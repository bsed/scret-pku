/**
 * <iframe> HTML manipulation for Editor v1.2
 */
var HtmlManip = (function() {
	
	var _debug = null;
	var _controller = null;
	var _fakeTerm = null;
    var _cursorInfo = null;
    var _cursorHistory = null;
    
    var _needCheck = true;
    
	var _mayClean = [];
	var _mustClean = [];
	
    var _isTermNode = function(node) {
        return node && node.className && (node.className == Constant.CLASS_DATA || node.className == Constant.CLASS_ROLE || node.className == "term_clean_candidate" || node.className.search(/keyword_/i) == 0);
    };

//当匹配一个英文词组时，由于空格可能是' '或&nbsp;，因此要把带匹配串转为一个正则表达式
    var _getWhitespaceRegExp = function(pattern) {
        //将pattern中的空格替换为正则表达式([\s\u00a0] | &nbsp;)
        return new RegExp((pattern.replace(/\\/g, "\\\\")).replace(/\s/g, "([\\s\\u00a0]|&nbsp;)"), "g"); // 必须是全局匹配("g"标志）
    };

    var _rangeTextToHtml = function(container, text) {
        var cursorPos = Selection.htmlOffset(container, true);
        var exp = _getWhitespaceRegExp(text);
        _debug.info(26, "rangeTextToHtml()", "exp = " + exp + ", cp = " + cursorPos);
        var htmlStart = -1, matchedHtml, htmlEnd;
        var result;
        // 找到container.innerHTML中cursorPos之前最后一个匹配exp的串
        var textBeforeCursor = container.innerHTML.substring(0, cursorPos);
        while ((result = exp.exec(textBeforeCursor)) != null) {
            matchedHtml = result[0];
            htmlStart = result.index;
        } 
        if (htmlStart != -1) {
            htmlEnd = htmlStart + matchedHtml.length;
        }
        return {
            start: htmlStart,
            end: htmlEnd
        };
    };
    

    // 在使用过_cursorInfo一次之后（checkXXX函数），
    // 直到下一次check之前，_cursorInfo信息都不再有效
    var _invalidateCursorInfo = function() {
        if (_cursorInfo) {
            _cursorInfo.inTermScope = false;
            _cursorInfo.atFirst = false;
            _cursorInfo.atLast = false;
            _cursorInfo.termId = null;
        }
    };

	var HtmlManip = {};
	
	HtmlManip.Cursor = {
		
		setRecheck: function() {
            _needCheck = true;
        },
		
		// 检查光标在术语前、后还是中间
        // 优化了算法，在光标不会任意改变时，只在第一次判断光标位置
        // （即在endCursorMovement后重新检查光标位置），
        // 另外，删除（backspace和delete）也会变相改变光标位置
        // 参数：bStart = null，没有选中文本区域，仅对光标作判断
        // bStart = true/false，选中了文本区域，分别对区域首/尾作判断，此时，offsetStart和offsetEnd即原选中区域的首尾
        // （由于Selection.insertNode会消除选中区域，所以要靠这两个属性来恢复或者调节选中区域）
		// backward = false，表示光标停在术语后；否则停在术语前（以支持左键）
        check: function(win, container, backward, bStart, offsetStart, offsetEnd) {
            if (_needCheck) {
                Selection.save(win);
				var doc = container.ownerDocument;
                var cursor = doc.createElement("span");
                cursor.id = "__checkCursor__";
                Selection.insertNode(container, cursor, bStart);
                var atFirst, atLast, needFix, inTerm = false;
                cursor = doc.getElementById("__checkCursor__");
                var term = null;
				function setFlags(pos){
                    if (pos == "first") {
                        atFirst = true;
                        atLast = false;
                        needFix = true;
                    } else if (pos == "last") {
                        atLast = true;
                        atFirst = false;
                        needFix = true;
                    } else if (pos == "middle") {
                        atFirst = atLast = false;
                        needFix = true;
                    } else {
                        needFix = false;
                    }
                }
				
				function findActualNode(direction, node) {
					while(node) {
                        if (node.nodeType == 3 && node.nodeValue == "") {
                            node = node[direction + "Sibling"];
                        } else {
                            break;
                        }
					}
					return node;
				}
				
				var next = cursor.nextSibling, prev = cursor.previousSibling;
				prev = findActualNode("previous", prev);
				next = findActualNode("next", next);
				if (_isTermNode(prev)) {
					term = prev;
					setFlags("last");
				} else if (_isTermNode(next)) {
					term = next;
					setFlags("first");
				} else {
					while (cursor.parentNode && cursor.parentNode != container) {
						if (_isTermNode(cursor.parentNode)) {
							term = cursor.parentNode;
							inTerm = true;
							break;
						}
						cursor = cursor.parentNode;
					}
				}
	
                var textLengthBefore = 0, textLengthAfter = 0;
                if (term && inTerm) {
                    var html = term.innerHTML;
                    var pos = html.search(/(<span id=(\w*|'|")__checkCursor__\2><\/span>)/i);
                    var cursorHolder = RegExp.$1;
                    if (pos == 0) {
                        setFlags("first");
                    } else if (pos + cursorHolder.length == html.length) {
                        setFlags("last");
                    } else {
                        var pos2 = pos + cursorHolder.length;
                        // 若pos之前有文本，或者pos2之后有文本，则光标在术语中间
                        // 只需要剥除所有HTML标签，剩下的既是文本
                        var textBeforeCursor = stripeAllHtmlTags(html.substring(0, pos));
                        var textAfterCursor = stripeAllHtmlTags(html.substring(pos2));
                        if (textBeforeCursor == "") {
                            setFlags("first");
                        } else if (textAfterCursor == "") {
                            setFlags("last");
                        } else {
                            setFlags("middle");
                        }
                        textLengthBefore = textBeforeCursor.length;
                        textLengthAfter = textAfterCursor.length;
                    }
                } else if (!term) {
                    setFlags("out_of_term");
                }
                
                removeElement(doc.getElementById("__checkCursor__"));
                win.focus();
				if (needFix && !atFirst && !atLast) {
					if (bStart == null) {
						if (backward) {
							Selection.focusBefore(container, term);
							atFirst = true;
							atLast = false;
						} else {
							Selection.focusAfter(container, term);
							atFirst = false;
   						    atLast = true;
						}
						
					} else if (bStart) {
						Selection.set(container, {node: offsetStart.node, offset: offsetStart.offset - textLengthBefore}, offsetEnd);
						atFirst = true;
						atLast = false;
					} else {
						Selection.set(container, offsetStart, {node: offsetEnd.node, offset: offsetEnd.offset + textLengthAfter});
						atFirst = false;
						atLast = true;
					}
				} else { // out of term
					 if (bStart == null) {
                        Selection.load();
                    } else {
                        Selection.set(container, offsetStart, offsetEnd);
                    }
				}
                
                _cursorInfo = {
                    inTermScope: needFix,
                    atFirst: atFirst,
                    atLast: atLast,
                    termId: (term ? term.id : null)
                };
                _debug.info(161, "Cursor.check()", "returns: " + objToString(_cursorInfo), "-i");
                // 如果在术语头部且需要修正，则以后的输入均需要修正，
                // 但是cursorInfo不变，因此记录在history中，以后直接读取即可
                if (atFirst && needFix) {
                    _cursorHistory = {
                        inTermScope: needFix,
                        atFirst: atFirst,
                        atLast: atLast,
                        termId: (term ? term.id : null)
                    };
                } else {
                    _cursorHistory = null;
                }

                _needCheck = false;
				HtmlManip.Term.prepareToClean();
                
            } else if (_cursorHistory) {
                _cursorInfo = {
                    inTermScope: _cursorHistory.inTermScope,
                    atFirst: _cursorHistory.atFirst,
                    atLast: _cursorHistory.atLast,
                    termId: _cursorHistory.termId
                };
            }
        }
	};
	
	HtmlManip.Input = {
        getText: function(win, container, start, end) {
            if (start && end) {
                Selection.save(win);
                Selection.set(container, start, end);
                var t = Selection.text(container);
                win.focus();
                Selection.load();
                return t;
            }
            return null;
        },
		
		getLineTextBeforeCursor: function(win, container, cursor) {
            if (cursor) {
                if (cursor.offset == 0) {
                    return "";
                } else {
                    return HtmlManip.Input.getText(win, container, {
                        node: cursor.node,
                        offset: 0
                    }, cursor);
                }
            }
            return null;
		},
		
		// 检查是否删除整个术语，backward = false表示在术语之前用DELETE键删除，true表示
        // 在术语之后用Backspace删除
        deletion: function(win, container, backward) {
			var doc = container.ownerDocument;
            var needDelete = _cursorInfo.inTermScope && (_cursorInfo.atFirst == !backward || _cursorInfo.atLast == backward);
            if (needDelete) {
				var cursor = Selection.lineOffset(container, true);
				var term = doc.getElementById(_cursorInfo.termId);
				if (term && backward) {  // 使用backspace删除后，光标要向前移动
					cursor.offset -= innerText(term).length;
				}
				//在IE下，如果被删掉的术语标签带有格式（比如加粗、红色），那么删掉术语后再输入字符的话，
				//该字符会被自动加上对应的格式标签（加粗、红色），因此在删除之前先把术语的CSS class去掉，
				//也就是去掉了术语的格式，这样IE就不会再自作聪明加上什么格式标签了
				if (term) {
					$(term).removeClass();
				}
                removeElement(doc.getElementById(_cursorInfo.termId));
				win.focus();
				Selection.set(container, cursor);
            }
            _invalidateCursorInfo();
        },
		
		newline: function(win, container) {
		    _debug.info(279, "newline()", "method begin");
			var doc = container.ownerDocument;
			HtmlManip.Cursor.setRecheck();
            var start = Selection.lineOffset(container, true);
			var ret = start.node == null && start.offset == 0;
			//检查start是否在第一行最前面
            var end = Selection.lineOffset(container, false);
			
			HtmlManip.Term.clean(container);
			
            if (start.node == end.node && start.offset == end.offset) {
				HtmlManip.Cursor.check(win, container);
				if (_cursorInfo && _cursorInfo.inTermScope) {
					var br = doc.createElement("br");
					var t = doc.getElementById(_cursorInfo.termId);
					if (_cursorInfo.atFirst) {
						t.parentNode.insertBefore(br, t);
					} else {
						insertAfter(br, t);
					}
					Selection.focusAfter(container, br);
				} else {
					Selection.insertNewline(container);
					Selection.scrollToCursor(container);
				}
                _controller.selection.start = start.line;
                _controller.selection.end = start.line;
                _controller.informLineChange(1);
            } else { // 选中了一段文本，用<br>替换之
				Selection.set(container, start, end);
				//TODO: Firefox的insertNewLine存在问题，不会替换掉被选中的文本
                if ($.browser.mozilla) {
                    Selection.replace(container, doc.createElement("br"));
                } else {
                    Selection.insertNewline(container);
                }
				Selection.scrollToCursor(container);
                //controller.selection的信息已经在controller.endCursorMovement中设定了
                _controller.informLineChange(1);
            }
            HtmlManip.Cursor.setRecheck();
            _invalidateCursorInfo();
			return ret;
        }
	};
	
	HtmlManip.Term = {
		
		textLength: function(term) {
			var id = term.id;
			var begin = id.search(/length_/i);
			return Number(id.substring(begin + "length_".length));
		},
		
		prepareToClean: function() {
			if (_cursorInfo && _cursorInfo.termId) {
				for (var i = 0; i < _mayClean.length; ++i) {
					if (_mayClean[i].termId == _cursorInfo.termId && _mayClean[i].atFirst == _cursorInfo.atFirst && _mayClean[i].atLast == _cursorInfo.atLast) {
						return;  // cursorInfo has already existed
					}
				}
				_mayClean.unshift({
					inTermScope: _cursorInfo.inTermScope,
					termId: _cursorInfo.termId,
					atFirst: _cursorInfo.atFirst,
					atLast: _cursorInfo.atLast
				})
			}
		},
		
		checkCleanCandidates: function(container) {
			var doc = container.ownerDocument;
			for (var i = 0; i < _mayClean.length; ++i) {	
				var term = doc.getElementById(_mayClean[i].termId);
				
				if (!term) { // Term has already deleted by user
					_mayClean.splice(i, 1);  // remove _mayClean[i]
				} else { 
				    var t = innerText(term);
					var len = HtmlManip.Term.textLength(term);
                    if (t.length > len) {
                        term.oldClassName = term.className;
                        term.className = "term_clean_candidate";
                        
                        if (_mayClean[i].atFirst) {
                            _fakeTerm.innerHTML = t.substring(t.length - len);
                        } else {
                            _fakeTerm.innerHTML = t.substring(0, len);
                        }
                        _fakeTerm.className = term.oldClassName;
                        var offset = getPosition(term);
                        var base = _controller.EditArea.getEditorPosition();
                        var x = base.x - container.scrollLeft + offset.x;
                        var y = base.y - container.scrollTop + offset.y + 2;
                        _fakeTerm.style.left = x + "px";
                        _fakeTerm.style.top = y + "px";
                        _fakeTerm.style.display = "inline";

						_mustClean.unshift(_mayClean[i]);
                        _mayClean.splice(i, 1);
                    }
				}
			}
		},
		
		clean: function(container) {
			var cursor = Selection.lineOffset(container, true);
			for (var i = 0; i < _mustClean.length; ++i) {
				HtmlManip.Term.cleanTerm(container, _mustClean[i]);
			}
			Selection.set(container, cursor);
			_mayClean = [];
			_mustClean = [];
		},
		
        // 检查是否在术语末尾（首部）添加了字符，如果有，则把多余字符移到术语之外
        cleanTerm: function(container, cleanInfo){
            if (cleanInfo && cleanInfo.inTermScope) {
				var doc = container.ownerDocument;
				var term = doc.getElementById(cleanInfo.termId);
				if (!term) {
					return;
				}
				_debug.info(257, "Term.clean()", "term.innerText = " + innerText(term) + ", pos = " + (cleanInfo.atFirst ? "before" : "after"));
				var extraLength = innerText(term).length - HtmlManip.Term.textLength(term);
				
				function removeFirstChar(root) {
					if (root.nodeType == 3) {
						if (root.nodeValue.length > 0) {
							var c = root.nodeValue.charAt(0);
							root.nodeValue = root.nodeValue.substring(1);
							var txt = doc.createTextNode(c);
							var t = doc.getElementById(cleanInfo.termId);
							t.parentNode.insertBefore(txt, t);	
							return true;
						}
					}
					for (var ch = root.firstChild; ch != null; ch = ch.nextSibling) {
						if (removeFirstChar(ch)) {
							return true;
						}
					}
					return false;
				}
				
				function removeLastChar(root) {
					for (var ch = root.lastChild; ch != null; ch = ch.previousSibling) {
						if (removeLastChar(ch)) {
							return true;
						}
					}
					if (root.nodeType == 3) {
						if (root.nodeValue.length > 0) {
							var c = root.nodeValue.charAt(root.length - 1);
							root.nodeValue = root.nodeValue.substring(0, root.nodeValue.length - 1);
							var txt = doc.createTextNode(c);
							var t = doc.getElementById(cleanInfo.termId);
							insertAfter(txt, t);
							return true;
						}
					}
					return false;
				}
				
				while (extraLength > 0) {
					if (cleanInfo.atFirst) {
						removeFirstChar(doc.getElementById(cleanInfo.termId));
					} else {
						removeLastChar(doc.getElementById(cleanInfo.termId));
					}
					--extraLength;
				}
				
				term = doc.getElementById(cleanInfo.termId);
				term.className = term.oldClassName;
                
				_fakeTerm.style.display = "none";
            }
        },
		
		unmark: function(container, id) {
			var cursor = Selection.lineOffset(container, true);
			
			$('#' + id, container).each(function() {
			    $(this).replaceWith($(this).html());
			});
			
			Selection.set(container, cursor);
		},
		
		rearrange: function(container) {
			var count = 0;
			
			function resetId(root) {
				if (_isTermNode(root)) {
					root.id = "term_" + (count++) + "_length_" + $(root).text().length;
				}
				for (var c = root.firstChild; c != null; c = c.nextSibling) {
					resetId(c);
				}
			}
			
			resetId(container);
			
			return count;
		}
		
	};
	
    HtmlManip.replaceText = function(win, container, text, node) {
		if (!text) {
            return;
        }
		
		var doc = container.ownerDocument;
        var content = container.innerHTML;
        var range = _rangeTextToHtml(container, text);
        if (range.start >= 0) {
	        container.innerHTML = (content.substring(0, range.start) + "<span id='__replaceText__'></span>" + content.substring(range.end))
	        _debug.info(487, "replaceText");
	        var oldNode = doc.getElementById("__replaceText__");
	        oldNode.parentNode.replaceChild(node, oldNode);
	        
	        win.focus();
	        Selection.focusAfter(container, node);
        } else {
            _debug.warning(508, "HtmlManip.replaceText()", "Term insertion failed. REASON: _rangeTextToHtml() returns -1, this should NOT happen.");
        }
    };
	
	HtmlManip.setDebugger = function(d) {
		_debug = d;
	};
	
	HtmlManip.setController = function(c) {
		_controller = c;
		_fakeTerm = _controller.fakeTerm;
	};
	
	return HtmlManip;
})();
