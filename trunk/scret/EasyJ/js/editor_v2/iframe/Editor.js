/**
 * The in-frame Editor v1.2
 */

var Editor = (function() {

    //-----------------------------------
	//   Class Editor
	//-----------------------------------
	var Editor = function(options) {
		this.options = options;
		this.win = window;
		this.doc = document;
		this.container = document.body;
		this.iframe = window.frameElement;
		this.EditArea = window.frameElement.EditArea;
		this.oldContent = null;
		this.console = this.EditArea.console;
		this.firstLineUCID = "";  // Use Case ID for first line
		this.firstLineActionID = "";
        this.context = {
            termCount: 0,
			tagCount: 0
        };
        
        this.panel = {
            lineNumber: this.EditArea.lineNumberPanel
        };
		if (options.content && options.content != "") {
			this.importContent(options.content);
		} else if (options.version) {
    		this.container.innerHTML = "<span id='0' class='version'></span>";
    		this.context.tagCount = 1;
    	}
        
        if (options.lineNo) {
        this.initLineNumber();
        }

        $(this.container).css({
            overflowX: options.scrollX ? "auto" : "hidden",
            overflowY: options.scrollY ? "auto" : "hidden"
        });

		this.controller = new Controller(this);
		this.setEditable(true);
		this.bindEvents();
		
	}

	Editor.prototype = {

		setEditable: function(editable) {
            if (document.body.contentEditable != undefined && window.ActiveXObject) { // IE
                document.body.contentEditable = (editable ? "true" : "false");
            } else {
                document.designMode = (editable ? "on" : "off");
            }
		},

		bindEvents: function() {
			var self = this;
			$(document).bind("mousedown", function(event) {
				self.controller.handleMouseDown(event);
			}).bind("mouseup", function(event) {
				self.controller.handleMouseUp(event);
			}).bind("keydown", function(event) {
				self.controller.handleKeyDown(event);
			}).bind("keyup", function(event) {
				self.controller.handleKeyUp(event);
			}).bind("contextmenu", function(event) {
				self.controller.handleRightClick(event);
			});
			$(document.body).bind("paste", function(event) {
				self.controller.handlePaste(event);
			});
            $(window).bind("scroll", function(event) {
                self.controller.handleScroll(event);
            });
		},

		importContent: function(content) {
			    this.container.innerHTML = HtmlParser.parseContent(content, this.EditArea);
                        if (this.options.version) {
                this.context.tagCount = Version.addTag(this.container);
                this.oldContent = this.container.innerHTML;
            } else {
                var filter = function(node){
                    return node && (node.className == "version" || node.className == "add" || node.className == "delete");
                };
                replaceAllWithChildren(this.container, filter);
            }

			// Replace <p></p> with <br>
			$("p").each(function() {
			    $(this).replaceWith($(this).html() + "<br />");
			});

			this.context.termCount = HtmlManip.Term.rearrange(this.container);
		},
        
        initLineNumber: function() {
            // Firefox will adds a "<br>" when content is empty
            if ($.browser.mozilla && $(document.body).text() == "") {
                $("br").remove();
                this.panel.lineNumber.init(1);
            } else {
                this.panel.lineNumber.init($("br").length + 1);
            }
        },
        
        attachPanel: function(name, panel) {
            this.panel[name] = panel;
            this.panel[name].setLogger(this.controller);
        },
        
		updateUseCaseInfo: function(container) {
		    if (!container) {
		        return;
		    }
			var row = null;
			var table = null;
			for (var c = container.firstChild; c != null; c = c.nextSibling) {
				if (c.nodeType == 1 && c.nodeName.toLowerCase() == "div" && c.className == "use_case_left") {
					table = c;
					break;
				}
			}
			if (!table) {
				return;
			}

			function nextRow() {
				var begin = row ? row.nextSibling : table.firstChild;
				for (; begin != null; begin = begin.nextSibling) {
					if (isHeader(begin)) {
						row = begin;
						return true;
					}
				}
				return false;
			}

			this.firstLineUCID = null;
			var ucId = null;
			if (nextRow()) {
				if ($(row).hasClass("use_case_begin")) {
					this.firstLineUCID = row.usecase;
					ucId = row.usecase;
				}
				if ($(row).hasClass("use_case_end")) {
					ucId = null;
				}
			}


			function updateInfo(root) {
				if (!root) {
					return;
				}
				if (root.nodeType == 1 && root.nodeName.toLowerCase() == "br") {
					if (nextRow()) {
						var n = getClassMatch(root, /uc_/i);
						while (n != "") {
							$(root).removeClass(n);
							n = getClassMatch(root, /uc_/i);
						}
						if ($(row).hasClass("use_case_begin")) {
							ucId = row.usecase;
						}
						if ($(row).hasClass("use_case_end")) {
							$(root).addClass(ucId);
							ucId = null;
						}
						if (ucId) {
							$(root).addClass(ucId);
						}
					} else {
						return;
					}
				}
				for (var c = root.firstChild; c != null; c = c.nextSibling) {
					updateInfo(c);
				}
			}

			updateInfo(this.container);
		},

		updateActionInfo: function(container) {
		    if (!container) {
		        return;
		    }
			var row = null;
			this.firstLineActionID = null;

			function isLine(node) {
				return node && node.nodeType == 1 && $(node).hasClass("action_line");
			}

			function nextRow() {
				var begin = row ? row.nextSibling : container.firstChild;
				for (; begin != null; begin = begin.nextSibling) {
					if (isLine(begin)) {
						row = begin;
						return true;
					}
				}
				return false;
			}

			if (nextRow()) {
				if (row.id && row.id != "") {
					this.firstLineActionID = row.id;
				}
			}

			function update(root) {
				if (!root) {
					return;
				}
				if (root.nodeType == 1 && root.nodeName.toLowerCase() == "br") {
					if (nextRow()) {
						var n = getClassMatch(root, /action_/i);
						while (n != "") {
							$(root).removeClass(n);
							n = getClassMatch(root, /action_/i);
						}
						if (row.id && row.id != "") {
							$(root).addClass(row.id);
						}
					} else {
						return;
					}
				}
				for (var c = root.firstChild; c != null; c = c.nextSibling) {
					update(c);
				}
			}

			update(this.container);
		},

		getContent: function() {
			return this.container.innerHTML;
		}
	}

	return Editor;
})();

$(document).ready(function (){
	var EditArea = window.frameElement.EditArea;
	EditArea.editor = new Editor(EditArea.options);
});
