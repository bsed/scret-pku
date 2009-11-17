/* version 1.2
 */
var Controller = (function() {

	var Key = {
		// 可打印字符
		isPrintable: function(code) {
			return code == 32 // 空格
			    || (65 <= code && code <= 90)   // letter
			    || (48 <= code && code <= 57)   // digit
			    || (96 <= code && code <= 107)  // digit(小键盘)
			    || (109 <= code && code <= 111) // operators(小键盘)
			    || (186 <= code && code <= 222); // operators 或 输入法
 		},

		// 中文输入法截获的字符
		isSpecial: function(code) {
			return code > 222;
		},

		// 可移动光标的字符
		isCursorBackwardable: function(code) {
			return code == 36 || code == 37;
		},

		isCursorChangeable: function(code) {
			return (33 <= code && code <= 40);
		}
	};

	var Controller = function(editor) {
		this.editor = editor;
		this.container = editor.container;
		this.doc = editor.doc;
		this.win = editor.win;
		this.iframe = editor.iframe;
		this.EditArea = editor.EditArea;
		this.console = editor.console;
		this.context = editor.context;

		this.termList = this.EditArea.termList;
		this.fakeTerm = this.EditArea.fakeTerm;
        this.panel = editor.panel;

		this.state = {
			preText: null,   // 光标前的本行文字
			cursor: null,
			matching: false,
			pinyin: false
		};
        
        this.selection = {  // 记录最近一次光标在第几行（起止）
            start: null,     
            end: null,
            atLineStart: false
        };
		
		this.scheduler = null; // 保存需要延迟执行的方法（只支持延迟一个方法）
		
		HtmlManip.setDebugger(this);
		HtmlManip.setController(this);
		Selection.setDebugger(this);
	};

	Controller.prototype = {

		info: function(lineno, method, msg, flag) {
			if (!this.console) {
				return;
			}
			var noState = false, noInnerHtml = false;
			if (flag) {
				if (flag.indexOf("-s") >= 0) {
					noState = true;
				}
				if (flag.indexOf("-i") >= 0) {
					noInnerHtml = true;
				}
			}
			var variables = [];
			if (!noState) {
				variables = variables.concat([
				    {
						name: "matching",
						value: this.state.matching
					},
					{
						name: "text before cursor",
						value: this.state.preText != null ? this.state.preText : "null"
					},
					{
						name: "cursor",
						value: this.state.cursor ? (outerHtml(this.state.cursor.node) + ": " + this.state.cursor.offset) : "null"
					},
					{
						name: "pinyin",
						value: this.state.pinyin
					}
				]);
			}
			if (!noInnerHtml) {
				variables.push({
						name: "iframe_content",
						value: this.container.innerHTML
					});
			}
			this.console.info({
				lineno: lineno,
				method: method,
				msg: (msg ? msg : null),
				flag: (flag ? flag : null),
				variables: variables
			});
		},

		warning: function(lineno, method, msg) {
		    if (this.console) {
		        this.console.warning(lineno, method, msg, [{name: "iframe_content",	value: this.container.innerHTML}]);
		    }
		},

		changeState: function(toMatchingState) {
            if (toMatchingState) {
                this.termList.show();
            } else {
                this.termList.hide();
            }
			this.state.matching = toMatchingState;
		},

		updateCursor: function(recheck, cursor) {
		    if (cursor) {
                this.state.cursor = cursor;  
            } else {
                this.state.cursor = Selection.lineOffset(this.container, true);
            }
            this.state.preText = HtmlManip.Input.getLineTextBeforeCursor(this.win, this.container, this.state.cursor);
			if (recheck) {
				HtmlManip.Cursor.setRecheck();
				HtmlManip.Cursor.check(this.win, this.container);
			}
		},
        
        informLineChange: function(increment) {
            // 被选中区域（this.selection）将被删除，而增加的行数为increment
            // 如果increment == null，则表示调用方难以确定增加量（例如：现在
            // 无法判断光标是否在行尾，因此不知道是否会delete掉一行），这时，就
            // 重新对行数进行计算
            // 上面的思路就是说：不到万不得已就不要采取效率较低的“重新计数”方法
            
            // firefox会在内容为空时自动加入一个br
            if ($.browser.mozilla && $(document.body).text() == "") {
                $("br").remove();
            } 
            
            if (increment == null) {
                increment = $("br").length + 1 - this.panel.lineNumber.lines;
            } 
            // Inform observers(panels)
            for (var p in this.panel) {
                this.panel[p].change(this.selection, increment);
            }
        },
		//--------------------------------------------
		//  User Input
		//--------------------------------------------
        
        //TODO: 如果用户删除动作太快（比如按着backspace不放），则此方法的计算速度跟不上
        //触发事件的速度，会导致BUG
		beginDeletion: function() {
			if (!this.state.matching) {
				var start = Selection.lineOffset(this.container, true);
    			var end = Selection.lineOffset(this.container, false);
    			HtmlManip.Term.clean(this.container);
    			if (start.node == end.node && start.offset == end.offset) {
    				// 没有选择文本，只是普通click
                    // 在此情况下必须要主动的调用check方法，否则会导致光标的信息错误
                    // （例如：用户插入一个术语之后立即按backspace，如果此时不check，则
                    // 术语的删除会不正常）
    				HtmlManip.Cursor.setRecheck();
    				HtmlManip.Cursor.check(this.win, this.container, false);
                    start = Selection.lineOffset(this.container, true);
                    this.selection.start = start.line;
                    this.selection.end = start.line;
                    if (start.offset == 0 && start.node != null) {
                        this.selection.atLineStart = true;
                    } else {
                        this.selection.atLineStart = false;
                    }
    			} else {
                    // 选中了一段文本，不需要再check了
                    // 此时必须调用一下set函数来恢复这段选中，因为前面的lineOffset函数会在firefox下使得该选中区域失效
                    Selection.set(this.container, start, end);
                }
			}
		},
        
        // Deletion keys
        //   - backspace: backward = true
        //   - delete: backward = false
		endDeletion: function(backward) {
			this.info(167, "endDeletion", "pinyin = " + this.state.pinyin);
			// 如果在输入法开启的时候删除字符，则该删除只是在输入法中进行，不影响编辑器
            if (!this.state.pinyin) {
			  if (this.state.matching) {
                // 如果在术语匹配时删除字符，因为不可能跨行匹配，所以在此状态下也不可能影响到行的变化
			    this.state.cursor = Selection.lineOffset(this.container, true);
			    this.doTextMatching(); 
			  } else {
				HtmlManip.Input.deletion(this.win, this.container, backward);
				HtmlManip.Cursor.setRecheck();
                this.updateCursor();
                // 下面计算对行的影响
                if (backward) {
                    if (this.selection.atLineStart) {
                        this.informLineChange(-1);  // 此时会删掉一个回车
                        this.selection.atLineStart = false;
                    } else {
                        this.informLineChange(0);
                    }
                } else {
                    if (this.selection.start != this.selection.end) {
                        // 对于跨行的选择区域稍稍做一点优化
                        //TODO: 如果selection还记录列数（现在只记录了行数）
                        //那么还可以精确的判断在一行内是否存在选择区域
                        this.informLineChange(0);
                    } else {
                        // 因为此时不知道是否会delete掉一个行尾的回车，所以保险起见传入Null(参见
                        //   informLineChange方法的注释)
                        this.informLineChange(null);
                    }
                }
                if ($.browser.msie) { // IE在删除之后不会触发scroll事件，因此手动触发
                    this.handleScroll();
                }
              }
			}
		},

		beginEnter: function(event) {
		    event.stopPropagation();
			event.preventDefault();
			if (this.state.matching) {
				this.termList.selectItem();
				this.changeState(false);
			} else {
				//TODO: 在Firefox中，插入<BR>后光标不会自动到下一行，除非又插入了文本或元素
				var brAtStart = HtmlManip.Input.newline(this.win, this.container);
				// 如果在第一行前面回车，那么原来第一行用FirstLineXX记录的其它视图的信息就应该
				// 写到这个新插入的回车上
				if (brAtStart) {
					$("br:first").addClass(this.editor.firstLineUCID).addClass(this.editor.firstLineActionID);
					this.editor.firstLineActionID = "";
					this.editor.firstLineUCID = "";
				}
				// 自动缩进，计算在本行之前出现的所有'{'和'}'
				var lineBegin = Selection.lineOffset(this.container);
				var lineBase = lineBegin.node;
				var level = 0;
				function calculateLevel(root) {
				    if (root == lineBase) {
				        return true;
				    }
				    if (root.nodeType == 3 && root.nodeValue != "") {
				        var levelIn = root.nodeValue.match(/[{｛]/g);
				        var levelOut = root.nodeValue.match(/[}｝]/g);
				        if (levelIn) {
				            level += levelIn.length;
				        }
				        if (levelOut) {
				            level -= levelOut.length;
				        }
				    }
				    for (var ch = root.firstChild; ch != null; ch = ch.nextSibling) {
				        if (calculateLevel(ch)) {
				            return true;
				        }
				    }
				    return false;
				}
				
				calculateLevel(this.container);
				var pre = "";
				for (var j = 0; j < level; ++j) {
				    pre += (nbsp + nbsp + nbsp + nbsp);
				}
				if (pre != "") {
				    Selection.insertNode(this.container, this.doc.createTextNode(pre));
				}
				this.updateCursor();
			}
		},

		beginUpArrow: function(event) {
			if (this.state.matching) {
				this.termList.moveToPreviousItem();
				event.stopPropagation();
				event.preventDefault();
			} else {
				this.beginCursorMovement();
			}
		},

		beginDownArrow: function(event) {
			if (this.state.matching) {
				this.termList.moveToNextItem();
				event.stopPropagation();
				event.preventDefault();
			} else {
				this.beginCursorMovement();
			}
		},

		beginCursorMovement: function() {
			this.changeState(false);
		},

		//对于“前向移动建”——左、Home，光标应该停在术语前面
		endCursorMovement: function(event, backward) {
			if (this.state.pinyin) {
				return;
			}
			var start = Selection.lineOffset(this.container, true);
			var end = Selection.lineOffset(this.container, false);
			HtmlManip.Term.clean(this.container);
			if (start.node == end.node && start.offset == end.offset) {
				// 没有选择文本，只是普通click
				HtmlManip.Cursor.setRecheck();
				HtmlManip.Cursor.check(this.win, this.container, backward);
                this.selection.start = start.line;
                this.selection.end = start.line;
                if (start.offset == 0 && start.node != null) {
                    this.selection.atLineStart = true;
                } else {
                    this.selection.atLineStart = false;
                }
			} else {
                // 检查区域的头部
				HtmlManip.Cursor.setRecheck();
				HtmlManip.Cursor.check(this.win, this.container, backward, true, start, end);
				start = Selection.lineOffset(this.container, true);
                // 检查区域的尾部
				HtmlManip.Cursor.setRecheck();
				HtmlManip.Cursor.check(this.win, this.container, backward, false, start, end);
                end = Selection.lineOffset(this.container, false);
                this.selection.start = start.line;
                this.selection.end = end.line;
                this.selection.atLineStart = false;
				//this.changeState(false);
			}
			this.updateCursor();
		},
        
        doTextMatching: function() {
        	var textBeforeCursor = HtmlManip.Input.getLineTextBeforeCursor(this.win, this.container, this.state.cursor);
			var pattern = null;
			if (this.state.preText != null && textBeforeCursor != null) {
				pattern = textBeforeCursor.substring(this.state.preText.length);
			}
			this.info(318, "doTextMatching()", "pattern = " + (pattern ? (pattern == "" ? "<empty>" : pattern) : "<null>"), "-i");
			var match = this.EditArea.findTerm(pattern);
			if (match.terms && match.terms.length > 0) {
				this.handleTermFound(true, match);
			} else {
				this.handleTermFound(false);
			}
        },
        
		beginInput: function(event) {
			if (!this.state.matching) {
				if (this.state.preText == null) {
					this.updateCursor(true);
				} else {
					HtmlManip.Cursor.check(this.win, this.container);
				}
				this.info(226, "beginInput()");
			}
		},

		endInput: function(event) {
            if (this.selection.start != null) {
                if (this.selection.start != this.selection.end) {
                    this.informLineChange(0);
                    if ($.browser.msie) { // IE在删除之后不会触发scroll事件，因此手动触发
                        this.handleScroll();
                    }
                    this.selection.start = null; // 该信息使用一次后就要失效
                }
            }
			this.state.cursor = Selection.lineOffset(this.container, true);
			if (!this.state.matching) {
					HtmlManip.Term.checkCleanCandidates(this.container);
			}
            this.doTextMatching();
		},

		//----------------------------
		//       Event Handler
		//----------------------------
		handleKeyDown: function(event) {
			var key = -1;
			if ($.browser.msie) { //IE
				key = event.keyCode;
			} else { // Gecko
				key = event.which;
			}
            this.info(200, "handleKeyDown()", "Keyboard Event Start, key(code, char) = (" + key + ", " + keymap[key] + ")", "begin");
			this.state.pinyin = false;
			switch (key) {
				case 8: // backspace
				case 46: // delete
					this.beginDeletion();
					break;
				case 13:    // Enter
				case 108:   // Numpad Enter
					this.beginEnter(event);
					break;
				case 38:
					this.beginUpArrow(event);
					break;
				case 40:
					this.beginDownArrow(event);
					break;
				default:
					if (Key.isSpecial(key)) {
						this.state.pinyin = true;
						this.beginInput(event);
					} else {
						if (Key.isPrintable(key)) {
							this.beginInput(event);
						} else if (Key.isCursorChangeable(key)) {
							this.beginCursorMovement(event);
						}
					}
			}
		},

		handleKeyUp: function(event) {

			var key = -1;
			if ($.browser.msie) { //IE
				key = event.keyCode;
			} else { // Gecko
				key = event.which;
			}

			switch (key) {
				case 8:  // backspace
				    this.endDeletion(true);
					break;
				case 46:  // delete
				    this.endDeletion(false);
					break;
				case 38: // uparrow
				case 40: // downarrow
					if (!this.state.matching) {
						this.endCursorMovement(event);
					}
					break;
				default:
					if (Key.isPrintable(key)) {
						this.endInput(event);
					} else if (Key.isCursorChangeable(key)) {
						this.endCursorMovement(event, Key.isCursorBackwardable(key));
					}
			}
			this.info(260, "handleKeyUp()", "Keyboard Event End, key(code, char) = (" + key + ", " + keymap[key] + ")", "end");
		},

		handleMouseDown: function(event) {
			this.info(261, "handleMouseDown()", "Mouse Event Start", "begin");
			this.state.pinyin = false; // 单击鼠标会自动使输入法消失
            this.beginCursorMovement();
		},

		handleMouseUp: function(event) {
			this.endCursorMovement(event, false);
            this.info(291, "handleMouseUp()", "Mouse Event End", "end");
		},

		handleRightClick: function(event) {

		},

		handlePaste: function(event) {

			//需要去掉嵌套的Version标签，并且对Version标签重新标号
			var vid = 0;
			function removeInnerVersionTag(root) {
			    if (!root) {
			        return null;
			    }
			    if (root.nodeType == 1 && root.className == "version") {
			        var next = root.nextSibling;
			        replaceAllWithChildren(root, function(node) {
			          return node && node != root && node.nodeType == 1 && node.className == "version";
			        });
			        root.id = vid++;
                    return next;			         			        
			    }
			    for (var c = root.firstChild; c != null; c = removeInnerVersionTag(c)) {
			        ; // empty loop body
			    }
			    return root.nextSibling;
			}
			
			function clean() {
			    removeInnerVersionTag(this.container);
			    this.context.termCount = HtmlManip.Term.rearrange(this.container);
			}
			
			//此函数需要延迟100ms执行，因为此时内容还没有粘贴到编辑框中
			// TODO: 为何在firefox下不运行clean？
			if (this.scheduler) {
			    window.clearTimeout(this.scheduler);
			}
			this.scheduler = window.setTimeout(clean, 100); 
		},
        
        handleScroll: function(event) {
            var top = $(window).scrollTop();
            for (var p in this.panel) {
                this.panel[p].scroll(top);
            }
        },
		//----------------------------
		//     Term Matching
		//----------------------------
		handleTermFound: function(found, context) {
			if (found) {
				var terms = context.terms;
				var items = [];
				for (var i = 0; i < terms.length; ++i) {
					var displayContent;
					switch (terms[i].type) {
						case "data":
						case "role":
						    displayContent = terms[i].value;
							break;
						case "keyword_if":
						    displayContent = "插入句型：" + terms[i].value + "...，则...";
							break;
						case "keyword_else":
						    displayContent = "插入句型：" + terms[i].value + "...";

					}
					items.push({
						data: displayContent,
						callback: this.handleTermSelected,
						param: {
							self: this,
							value: terms[i].value,
							type: terms[i].type,
							pattern: context.pattern
						}
					});
				}
                this.state.cursor = Selection.lineOffset(this.container, true);
                this.termList.fillList(items);

                //定位在光标处并显示
                var baseOffset = Selection.coordinate(this.container, true);
                var iframeOffset = this.EditArea.getEditorPosition();
                var x = baseOffset.x + iframeOffset.x + (($.browser.msie) ? Constant.UI_LIST_OFFSET_X_IE : Constant.UI_LIST_OFFSET_X);
                var y = baseOffset.y + iframeOffset.y + (($.browser.msie) ? Constant.UI_LIST_OFFSET_Y_IE : Constant.UI_LIST_OFFSET_Y);
                this.termList.setPosition(x, y);
                this.changeState(true);
			} else {
				this.changeState(false);
			}
		},

        handleTermSelected: function(param){
			var self = param.self;
            Selection.set(self.container, self.state.cursor);

			HtmlManip.Term.clean(self.container);
			
			var id = 'term_' + (self.context.termCount++) + '_length_' + param.value.length; 
			var term = $('<span id="' + id + '" class="' + param.type + '">' + param.value + '</span>').get(0);
			HtmlManip.replaceText(self.win, self.container, param.pattern, term);
			Selection.focusAfter(self.container, term);
			var cursor = Selection.lineOffset(self.container, true);
			//如果插入的是句型，则还要生成句型文字
			switch (param.type) {
				case "keyword_if":
				    $('#' + id).after(' ( )<br>{<br>}'); 
				    
					// 把光标从“如果”之后移到“如果 （”之后，也就是说要向后再移动2个offset
					cursor.offset += 2;
					Selection.set(self.container, cursor);
                    self.informLineChange(2); // 增加了2行
					break;
				case "keyword_else":
				    var $term = $('#' + id);
				    $term.after('<br>{<br>}');
					// 把光标从“否则”之后移到下一行的“{”之后
					cursor.node = $term.next().get(0);
					cursor.offset = 1;
					Selection.set(self.container, cursor);
                    self.informLineChange(2);
					break;
			}

			self.updateCursor(false, cursor);
            HtmlManip.Cursor.setRecheck();
            self.changeState(false);
        }
	};

	return Controller;
})();
