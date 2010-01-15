/**
 * EditArea.js
 *     version: 1.2 -- new interface
 * 
 */
var EditArea = (function(){
    
    /* Create EditArea
     * EditAreaçç¤ºä¾HTMLå¦ä¸ï¼
     *  <div id = parent> 
     *    <div id = toolbar>  å·¥å·æ 
     *    </div>
     *    <div id = editorBorder>  åæ ç¼è¾å¨çå¤è¾¹æ¡
     *        <div id = lineNo>
     *        </div>        
     *        <div id = leftColumn>
     *        </div>
     *        <div id = resizeControl>  é¼ æ æ¾å¨ä¸é¢ï¼å¯ä»¥å·¦å³æå¨ä»¥æ¹åä¸¤æ å®½åº¦æ¯ä¾
     *        </div>
     *        <iframe id = content>
     *        </iframe>
     *     </div>
     *  </div>
     *  å¶ä¸­lineNoå®½åº¦åºå®ä¸ºpxï¼leftColumnä¸ºç¾åæ¯ï¼èiframeå®½åº¦å¨æè®¡ç®
     */
    var EditArea = function(id, placeholder, manager, options) {
        var self = this;
        var parent = placeholder.parentNode;
        if (options.height) {
            $(parent).height(options.height);
        }
        var maxHeight = $(parent).height();
        $(placeholder).hide();

        this.parent = parent;
        this.manager = manager;
        this.options = options;
        this.valid = true;
        var ui = manager.ui;
        this.termList = ui.termList;
        this.fakeTerm = ui.fakeTerm;

        if (options.console == "yes") {
            if (!options.menu) {
                options.menu = {};
            }
            options.menu.displayConsole = "yes";
            options.menu.refreshConsole = "yes";
        }

        if (options.menu) {
            if (options.usecaseView) {
                options.menu.switchToEditor = "yes";
                options.menu.switchToUseCase = "yes";
            }

            if (options.actionView) {
                options.menu.switchToEditor = "yes";
                options.menu.switchToAction = "yes";
            }

            var menu = document.createElement("div");
            menu.className = "editorToolbar";
           
            function createButton(name, action){
                if (options.menu[action]) {
                    var img = document.createElement("img");
                    img.src = "./image/" + action + ".gif";
                    img.className = "toolbar_icon_img";
                    img.title = name;
                    $(img).mouseover(function() {
                        $(this).toggleClass("toolbar_icon_img_over");
                    }).mouseout(function() {
                        $(this).toggleClass("toolbar_icon_img_over");
                    }).click(function() {
                        self[action].call(self);
                    });
                    menu.appendChild(img);
                }
            }
            createButton("保存", "save");
            //createButton("æäº¤ä¸ºçæ¬", "commit");
            //createButton("æ¾ç¤ºçæ¬åå²", "showHistory");
            createButton("自动调整缩进", "format");
            //TODO: use Tab instead of Button
            //createButton("åæ¢è³ç¼è¾è§å¾", "switchToEditor");
            createButton("打开用例视图", "switchToUseCase");
            //createButton("åæ¢è³Actionè§å¾", "switchToAction");
            //createButton("æ¾ç¤ºæ§å¶å°", "displayConsole");
            //createButton("å·æ°æ§å¶å°", "refreshConsole");
            if (options.editOnce) {
                options.menu.exit = true;
                createButton("取消", "exit");
            }
            if (options.menu.anonymous) {
                this.anonymous = false;
                var cb = document.createElement("input");
                cb.type = "checkbox";
                cb.defaultChecked = false;
                cb.onclick = function() {
                    self.anonymous = this.checked;
                };
                $(cb).css({
                    marginLeft: "15px",
                    marginTop: "0",
                    marginRight: "3px"
                });
                menu.appendChild(cb);
                menu.appendChild(document.createTextNode("å¿å"));
            }
            parent.appendChild(menu);
            maxHeight -= $("div.editorToolbar").height();
        }

        if (options.usecaseView) {
            this.isUseCaseView = false;
            this.useCaseInfo = null;
        /*    var useCaseViewBorder = document.createElement("div");
            $(useCaseViewBorder).css({
                width: options.width,
                height: options.height
            });
            useCaseViewBorder.className = "ucview_border";
            parent.appendChild(useCaseViewBorder);
            this.useCaseViewBorder = useCaseViewBorder;
            $(useCaseViewBorder).hide();
            this.isUseCaseView = false;
            this.useCaseMap = {
                index: 0
            };
            this.useCaseView = new UseCaseView(useCaseViewBorder, this);
         */
        }

        // create action view
        if (options.actionView) {
            var actionViewBorder = document.createElement("div");
            $(actionViewBorder).css({
                width: options.width,
                height: options.height
            });
            actionViewBorder.className = "actionview_border";
            parent.appendChild(actionViewBorder);
            this.actionViewBorder = actionViewBorder;
            $(actionViewBorder).hide();
            this.isActionView = false;
            this.actionMap = {
                index: 0
            };
            this.actionView = new ActionView(actionViewBorder, this);
            
        }

        // create Console
        if (options.console == "yes") {
            var consoleHolder = document.createElement("div");
            consoleHolder.id = "console";
            parent.appendChild(consoleHolder);
            this.console = new Console(consoleHolder, true);
        }
        
        // åå»ºåæ ç¼è¾å¨çå¤è¾¹æ¡
        var editorBorder = document.createElement("div");
        $(editorBorder).addClass("editorBorder").css({
            "height": maxHeight + "px"
        });
        
        var lineNo = null;
        if (options.lineNo) {
            lineNo = document.createElement("div");
        $(lineNo).addClass("editorLineNoPanel").css({
            "height": maxHeight + "px"
        });
        }
        
        var frame = document.createElement("iframe");
        frame.className = "editorContent";
        frame.id = id;
        frame.frameBorder = "no";
        frame.border = "0";
        frame.marginWidth = "0";
        frame.marginHeight = "0";   // ä»¥ä¸åå¥å¿éï¼å¦åå¨firefoxä¸iframeå°ä¸å¯ç¼è¾
        
        // æ¸é¤æeditorBorderä¹ååç´ çæµ®å¨
        var clearBr = document.createElement("br");
        clearBr.className = "clear";
        
        if (options.lineNo) {
        lineNo.id = frame.id + "_lineNoPanel";
        $(editorBorder).append(lineNo);
        }
        $(editorBorder).append(frame).append(clearBr);
        $(parent).append(editorBorder);
        
        if (options.lineNo) {
        this.lineNumberPanel = new LineNumberPanel(lineNo);
        }
        this.iframeBorder = editorBorder;
        this.iframe = frame;
        this.win = frame.contentWindow;
        this.isEditView = true;
        
        frame.EditArea = this;

        // Initialize Iframeï¼å¿é¡»å¨è¿ä¸ªå½æ°çæå
        var html = ['<html><head><link rel="stylesheet" href="' + options.cssPath + '/' + options.cssFile + '" />'];
        for (var i = 0; i < options.jsFile.length; ++i) {
            html.push('<script type="text/javascript" src="' + options.jsPath + '/' + options.jsFile[i] + '"></scri' + 'pt>');
        }
        html.push('</head><body class="scenario_editarea"></body></html>');
        var doc = this.win.document;
        doc.open();
        doc.write(html.join(""));
        doc.close();
        // è®¡ç®frameçå®½åº¦
        //var widthAll = $("div.editorBorder", parent).width();
        //var widthLeft = $("div.editorLineNoPanel", parent).width();
        //$("iframe", parent).width(widthAll - (options.lineNo ? widthLeft : 0) - 8);
        $("iframe", parent).height(maxHeight);
        $("iframe", parent).css({width: "92%"}); 
    };

    EditArea.prototype = {
        
        switchToEditor: function() {
            if (!this.isEditView) {
                if (this.isUseCaseView) {
                    this.editor.updateUseCaseInfo(this.useCaseView.container);
                }
                if (this.isActionView) {
                    this.editor.updateActionInfo(this.actionView.container);
                }
                $(this.iframeBorder).show();
                $(this.useCaseViewBorder).hide();
                $(this.actionViewBorder).hide();
                this.isUseCaseView = false;
                this.isActionView = false;
                this.isEditView = true;
            }
        },

        switchToUseCase: function() {
            if (!this.isUseCaseView) {
                this.isUseCaseView = true;
                // Create the use case panel
                if (!this.useCasePanel) {
                    var panel = document.createElement("div");
                    var $content = $(".editorContent", this.parent);
                    $(panel).addClass("editorUseCasePanel").css({
                        "height": $content.height()
                    });
                    $("div.editorLineNoPanel", this.parent).after(panel);
                    
                    var resizer = document.createElement("div");
                    $(resizer).addClass("editorResizeControl").css({
                        "height": $content.height()
                    });
                    $(panel).after(resizer);
                                        
                    var parent = this.parent;
                    var w = $("div.editorBorder", parent).width()
                        - $("div.editorLineNoPanel", parent).width()
                        - $("div.editorUseCasePanel", parent).width()
                        - $(resizer).width()
                        - 10;
                    $(".editorContent").width(w + "px");
                    this.useCasePanel = new UseCasePanel(panel);
                    this.editor.attachPanel("useCase", this.useCasePanel);
                    this.useCasePanel.init(this.lineNumberPanel.lines, this.useCaseInfo);
                    
                    this.resizeControl = new ResizeControl(resizer, $("div.editorBorder", parent));
                    this.resizeControl.attachSidePanel(panel);
                }
            }
            this.editor.win.focus();
        },

        switchToAction: function() {
            if (!this.isActionView) {
                if (this.isUseCaseView) {
                    this.editor.updateUseCaseInfo(this.useCaseView.container);
                }
                this.actionView.importContent(this.editor.container, this.editor.firstLineActionID);
                $(this.iframeBorder).hide();
                $(this.actionViewBorder).show();
                $(this.useCaseViewBorder).hide();
                this.isUseCaseView = false;
                this.isActionView = true;
                this.isEditView = false;
            }
        },

        displayConsole: function() {
            this.console.display();
        },

        refreshConsole: function() {
            this.console.refresh();
        },

        findTerm: function(pattern) {
            return this.manager.findTerm(pattern);
        },

        getIframe: function() {
            return this.iframe;
        },

        getEditorPosition: function() {
            return getPosition(this.iframe);
        },

        getAuxInfo: function() {
            var graphInfo = HtmlParser.getGraphSourceString(this);
            var mapInfo = HtmlParser.getMapString("usecase", this.useCasePanel);
            return graphInfo + mapInfo;
        },

        getContent: function() {
            if (this.valid) {
                return this.editor.getContent();
            } else {
                return "";
            }
        },

        getComparedContent: function() {
            if (this.options.version) {
                return Version.compare(this.iframe.parentNode, this.editor.context.tagCount, this.editor.oldContent, this.getContent());
            } else {
                return this.getContent();
            }
        },

        getCleanContent: function() {
            if (this.options.version) {
                return Version.clean(this.iframe.parentNode, this.getContent());
            } else {
                return this.getContent();
            }
        },

        showHistory: function() {
            //this.editor.setEditable(false);
        },
        
        switchToReadOnly: function(returnValue) {
            //å°ç¼è¾å¨çåå®¹ä»¥åªè¯»å½¢å¼æ¾ç¤ºå¨editor_borderä¸­
            var editorBorder = this.iframeBorder.parentNode;
            var html = this.getContent();
            removeElement(editorBorder);
            if (!returnValue) {
                returnValue = {};
            }
            returnValue.html = html;
            if (this.options.editOnceCallback) {
                this.options.editOnceCallback(returnValue);
            }
        },
        
        exit: function() {
            $(this.parent).remove();
            this.termList.hide();
            this.fakeTerm.style.display = "none";
            if (this.options.editOnceCallback) {
                this.options.editOnceCallback({});
            }
        },
        
        save: function() {
            if (this.options.menu && this.options.menu.save) {
                var url = this.options.url;
                var form = this.options.form;
                var contentName;
                for (var name in this.options.data) {
                    // data.xxxContentå¨ä¼ å¥æ¶å¿é¡»ä¸ºnull
                    if (this.options.data[name] == null) {
                        this.options.data[name] = this.getAuxInfo() + this.getContent();
                        contentName = name;
                        break;
                    }
                }
                var returnValue = Ajax.submit(url, form, this.options.data);
                if (returnValue) {
                    for (var name in returnValue) {

                        this.options.data[name] = returnValue[name];
                    }
                }
                //alert(objToString(this.options.data));
                this.options.data[contentName] = null;
            }
            if (this.options.editOnce) {
                this.switchToReadOnly(returnValue);
            }
        },

        commit: function() {
            if (this.options.menu && this.options.menu.commit) {
                var url = this.options.commitUrl;
                var form = this.options.form;
                var content = this.getComparedContent();

                var contentName;
                for (var name in this.options.data) {
                    // data.xxxContentå¨ä¼ å¥æ¶å¿é¡»ä¸ºnull
                    if (this.options.data[name] == null) {
                        this.options.data[name] = content;
                        contentName = name;
                        break;
                    }
                }
                this.options.data.cleanContent = this.getAuxInfo() + this.getCleanContent();
                var returnValue = Ajax.submit(url, form, this.options.data);
                if (returnValue) {
                    for (var name in returnValue) {
                        this.options.data[name] = returnValue[name];
                    }
                }
                this.options.data[contentName] = null;
                this.editor.importContent(this.options.data.cleanContent);
            }
            if (this.options.editOnce) {
                this.switchToReadOnly(returnValue);
            }
        },
        
        format: function() {
            Formatter.format(this.editor.container);
        }
    };

    return EditArea;
})();
