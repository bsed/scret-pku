/* version 1.2
 */
var ActionView = (function () {
	var skipChars = /[^\s\u00a0,，\.。\(\)（）{}\[\]{}【】]/;
	var closeChars = /[\)）}}\]】]/;
	function getOpSpan(id) {
		return document.getElementById("ao_" + id.substring(3));
	}

	function getNameSpan(id) {
		return document.getElementById("an_" + id.substring(3));
	}

	function getContentSpan(id) {
		return document.getElementById("ac_" + id.substring(3));
	}

    function getOpButtons(op){
        var expand = null, collapse = null, newname = null;
        for (var c = op.firstChild; c != null; c = c.nextSibling) {
            if ($(c).hasClass("button_expand")) {
                expand = c;
            }
            if ($(c).hasClass("button_collapse")) {
                collapse = c;
            }
            if ($(c).hasClass("button_newname")) {
                newname = c;
            }
        }
        return {
            expand: expand,
            collapse: collapse,
            newname: newname
        };
    }

	//--------------------
	//  Class ActionView
	//--------------------
	function ActionView(border, editArea) {
		this.container = border;
		this.EditArea = editArea;
	}

	ActionView.prototype = {

		importContent: function(container, firstLineActionID) {
			var self = this;			
			$(this.container).empty();

			var lineNo = 1;

			var line = document.createElement("div");
			line.className = "action_line";

            var dumb = document.createElement("span");
			addSpaces(dumb);
			dumb.className = "empty_line";
			line.appendChild(dumb);

			var emptyLine = true;
			var preWhiteSpace = true;
			var actionClosed = false;

			this.container.appendChild(line);

			var content = document.createElement("span");
			content.className = "action_content";
			content.id = "ac_" + lineNo;
			var name = document.createElement("span");
			name.className = "action_name";
			name.id = "an_" + lineNo;
			var op = document.createElement("span");
			op.className = "action_op";
			op.id = "ao_" + lineNo;
			++lineNo;

			if (firstLineActionID && firstLineActionID != "") {
				line.id = firstLineActionID;
				this.createAction(op, name, content, line.id);
			} else {
				this.createPlain(op, name, content);
			}

			function checkEmptyLine() {
				if (emptyLine) {
					emptyLine = false;
					for (var c = line.firstChild; c != null; c = c.nextSibling) {
						if (c.nodeType == 1 && c.className == "empty_line") {
							removeElement(c);
							break;
						}
					}
				}
			}

			function createLines(root) {
				if (!root) {
					return;
				}
				if (isTerm(root)) {
					checkEmptyLine();
					var term = document.createElement("span");
					term.className = root.className;
					term.appendChild(document.createTextNode(innerText(root)));
					if (preWhiteSpace) {
						preWhiteSpace = false;
						line.appendChild(op);
						line.appendChild(name);
						line.appendChild(content);
					}
					if (actionClosed) {
						line.appendChild(term);
					} else {
						content.appendChild(term);
					}
					return;
				}

				if (isKeyword(root)) {
					checkEmptyLine();
					var term = document.createElement("span");
					term.className = root.className;
					term.appendChild(document.createTextNode(innerText(root)));
					if (preWhiteSpace || actionClosed) {
						line.appendChild(term);
					} else {
						content.appendChild(term);
					}
					return;
				}

                if (root.nodeType == 3 && root.nodeValue != "") { // Text Node
                    checkEmptyLine();
                    var preContent = null, inContent = null, postContent = null;
					var firstContent = false;
                    if (actionClosed) {
                        postContent = root.nodeValue;
                    } else {
                        if (preWhiteSpace) {
                            var contentBegin = root.nodeValue.search(skipChars);
                            if (contentBegin >= 0) {
                                preWhiteSpace = false;
								firstContent = true;
                                if (contentBegin > 0) {
                                    preContent = root.nodeValue.substring(0, contentBegin);
                                }
                                inContent = root.nodeValue.substring(contentBegin);
                            } else {
								preContent = root.nodeValue;
							}
                        } else {
                            inContent = root.nodeValue;
                        }
						if (inContent) {
							var contentEnd = inContent.search(closeChars);
							if (contentEnd >= 0) {
								actionClosed = true;
								postContent = inContent.substring(contentEnd);
								if (contentEnd > 0) {
									inContent = inContent.substring(0, contentEnd);
								} else {
									inContent = null;
								}
							}
						}
                    }
                    if (preContent) {
                        line.appendChild(document.createTextNode(preContent));
                    }
                    if (inContent) {
						if (firstContent) {
							line.appendChild(op);
                            line.appendChild(name);
                            line.appendChild(content);
						}
                        content.appendChild(document.createTextNode(inContent));
                    }
                    if (postContent) {
                        line.appendChild(document.createTextNode(postContent));
                    }
                    return;
                }

				if (root.nodeType == 1 && root.nodeName.toLowerCase() == "br") {
					// Create a new line in ActionView
					preWhiteSpace = true;
					emptyLine = true;
					actionClosed = false;

                    var thisLine = document.createElement("div");
                    thisLine.className = "action_line";
                    var dumb = document.createElement("span");
                    addSpaces(dumb);
                    dumb.className = "empty_line";
                    thisLine.appendChild(dumb);

					self.container.appendChild(thisLine);

                    var thisContent = document.createElement("span");
                    thisContent.className = "action_content";
                    thisContent.id = "ac_" + lineNo;
                    var thisName = document.createElement("span");
                    thisName.className = "action_name";
                    thisName.id = "an_" + lineNo;
                    var thisOp = document.createElement("span");
                    thisOp.className = "action_op";
                    thisOp.id = "ao_" + lineNo;
                    ++lineNo;

					line = thisLine;
					op = thisOp;
					name = thisName;
					content = thisContent;

					var actionId = getClassMatch(root, /action_/i);
					if (actionId != "") {
                        thisLine.id = actionId;
                        self.createAction(thisOp, thisName, thisContent, actionId);
					} else {
                        self.createPlain(thisOp, thisName, thisContent);
					}
				}

				for (var c = root.firstChild; c != null; c = c.nextSibling) {
					createLines(c);
				}
			} // function createLines()

			createLines(container);
		},

		createAction: function(op, name, content, id) {
			var self = this;

			var expand = document.createElement("img");
			expand.src = "./image/expand.gif";
			expand.title = "显示内容";
			expand.className = "button_expand";
			$(expand).bind("click", function(e) {
				self.expandAction(e, true);
			});

			var collapse = document.createElement("img");
			collapse.src = "./image/collapse.gif";
			collapse.title = "隐藏内容";
			collapse.className = "button_collapse";
			$(collapse).bind("click", function(e) {
				self.expandAction(e, false);
			});

			$(op).empty().show();
			op.appendChild(expand);
			op.appendChild(collapse);

			$(name).empty().show().unbind().bind("click", function(e) {
				self.renameAction(e);
			});
			name.appendChild(document.createTextNode(this.EditArea.actionMap[id].name));
			$(content).hide();
			$(collapse).hide();
		},

		createPlain: function(op, name, content) {
			var self = this;

			var newName = document.createElement("img");
			newName.src = "./image/new_action.gif";
			newName.title = "添加action";
			newName.className = "button_newname";
			$(newName).bind("click", function(e) {
				self.renameAction(e);
			});
			$(op).empty().show();
			op.appendChild(newName);

			$(name).empty().hide();
			$(content).show();
		},

		expandAction: function(e, hideName) {
			e = normalizeEvent(e);
			var op = e.source;
			while (op && !isOp(op)) {
				op = op.parentNode;
			}

			var buttons = getOpButtons(op);
			if (hideName) {
				$(buttons.expand).hide();
				$(buttons.collapse).show();
				$(getNameSpan(op.id)).hide();
			    $(getContentSpan(op.id)).show().css({
                    display: "inline"
                });;
			} else {
				$(buttons.expand).show();
				$(buttons.collapse).hide();
                $(getNameSpan(op.id)).show().css({
                    display: "inline"
                });
			    $(getContentSpan(op.id)).hide();
			}

		},

		renameAction: function(e) {
			var self = this;
			e = normalizeEvent(e);
			var top = e.source;
			while (top && !isOp(top) && !isName(top)) {
				top = top.parentNode;
			}

			var op = getOpSpan(top.id);
			var name = getNameSpan(top.id);
			var content = getContentSpan(top.id);

			var line = op;
			while (line && line.className != "action_line") {
				line = line.parentNode;
			}

			// Show a dialog box in Name SPAN
			var oldName = $(name).text();
			var inputBox = document.createElement("input");
			inputBox.type = "text";
			inputBox.size = 5;
			inputBox.maxLength = 10;
			inputBox.value = oldName;
			$(op).hide();
			$(name).empty().show().append(inputBox);
			$(".action_name").css({
				display: "inline"
			});
			$(inputBox).focus();
			$(inputBox).bind("blur", function() {
				if (inputBox.value.replace(/\s/g, "") == "") {
					if (line.id && line.id != "") {
						delete self.EditArea.actionMap[line.id];
						line.id == "";
					}
					self.createPlain(op, name, content);
				} else {
					var actionId = line.id;
					if (!line.id || line.id == "") {
						actionId = "action_" + self.EditArea.actionMap.index;
						++self.EditArea.actionMap.index;
						line.id = actionId;
					}
                    if (!self.EditArea.actionMap[actionId]) {
                        self.EditArea.actionMap[actionId] = {};
                    }
                    self.EditArea.actionMap[actionId].name = inputBox.value;
					self.createAction(op, name, content, actionId);
				}
				removeElement(inputBox);
			});
		}
	};

	return ActionView;
})();
