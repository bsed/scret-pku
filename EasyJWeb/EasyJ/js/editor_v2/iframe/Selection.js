/**
 * 辅助类 —— 光标、选择
 * 仅对可编辑iframe有效
 * v1.2
 */
var Selection = {};

(function() {
	
	var _debug = null;
	
	var _lastSelection = null;
	
	// 寻找container之内的node的最上层祖先节点，
	// null若node不在container之内
	var _topAncestorOf = function(node, container) {
		while (node && node.parentNode != container) {
			node = node.parentNode;
		}
		return node;
	};
	
	// 寻找container之内的node最上层祖先节点的前一个结点
	var _topAncestorBefore = function(node, container) {
		if (node) {
			while (!node.previousSibling && node.parentNode != container) {
				node = node.parentNode;
			}
			return _topAncestorOf(node.previousSibling, container);	
		}
		return null;
	};
	
	Selection.setDebugger = function(d) {
		_debug = d;
	};
	
	if ($.browser.msie) {
	
		// 记录在appWindow这个窗口下的当前文档光标/选择区域位置信息，
		// appWindow需要显式设置margin和padding为0
		Selection.save = function(appWindow, saveAs){
			
			var b = appWindow.document.body;
			_lastSelection = {
				start: Selection.lineOffset(b, true),
				end: Selection.lineOffset(b, false),
				container: b
			};
			if (saveAs) {
                return _lastSelection;
			}
		};
		
		Selection.load = function(old) {
			if (!_lastSelection && !old) {
				return;
			}
			var sel;
			if (old) {
				sel = old;
			} else {
				sel = _lastSelection;
			}
			Selection.set(_lastSelection.container, _lastSelection.start, _lastSelection.end);
		};
		
		// 在container之内的包含被选中区域/光标的最上层结点。
		// bStart = true：被选中区域左端点为光标；false：右端点为光标
		// 光标不存在，返回false；
		// 光标在编辑框第一个字符之前，返回null；
		// 光标在最上层纯文本结点(TextNode)中，返回光标之前该文本的某一段值（IE对此的实现不确定！！）
		Selection.topNode = function(container, bStart) {
			var selection = container.ownerDocument.selection;
			try {
			    var range = selection.createRange();
                range.collapse(bStart);
                var parent = range.parentElement();
                if (parent && isAncestor(container, parent)) {
                    var range2 = range.duplicate();
                    range2.moveToElementText(parent);
                    if (range.compareEndPoints("StartToStart", range2) == -1) {
                        return _topAncestorOf(parent, container);
                    }
                }
				
                range.pasteHTML("<span id='__tempTopNode__'></span>");
                var temp = container.ownerDocument.getElementById("__tempTopNode__");
                var result = _topAncestorBefore(temp, container);
                removeElement(temp);
				return result;
			} catch (e) {
				return false;
			}
		};
		
		Selection.parent = function(container, bStart) {
			var selection = container.ownerDocument.selection;
			try {
			    var range = selection.createRange();
                range.collapse(bStart);
                return range.parentElement();
			} catch (e) {
				return null;
			}
		};
		
		// IE的pasteHTML等价于一次替换
		Selection.replace = function(container, node) {
			Selection.insertNode(container, node, null);
		};
		
		// 设置光标在node之后，如果node为null，则设置于container的头部
		Selection.focusAfter = function(container, node) {
			var range = container.ownerDocument.body.createTextRange();
			var target, next = null;
			if (!node) {
				target = container;
			} else if (node.outerHTML) {
				target = node;
			} else {  // TextNode
				var doc = container.ownerDocument;
				next = doc.createElement("span");
				insertAfter(next, node);
				target = next;
			}
			range.moveToElementText(target);
			range.collapse(!node);
			range.select();
			if (next) {
				removeElement(next);
			}
		};
		
		Selection.focusBefore = function(container, node) {
			var prev = node.previousSibling;
			if (!prev || prev.outerHTML) {
				Selection.focusAfter(container, prev);
			} else {  // prev is TextNode
			    var doc = container.ownerDocument;
				var prevElement = doc.createElement("span");
				insertAfter(prevElement, prev);
				Selection.focusAfter(container, prevElement);
				removeElement(prevElement);
			}
		};
		
		// 插入html文本
		Selection.insertHtml = function(container, html, bStart) {
            var selection = container.ownerDocument.selection;
            try {
                var range = selection.createRange();
				if (bStart != null && bStart != undefined) {
					range.collapse(bStart);
				}
                range.pasteHTML(html);
                range.collapse(false);
                range.select();
            } catch (ignored) {
				
			}
		};
		
		Selection.insertNode = function(container, node, bStart) {
			Selection.insertHtml(container, (node.nodeType == 1 ? node.outerHTML : node.nodeValue), bStart);
		};
		
		// 针对回车键，各浏览器插入的标签不同(p或br)，统一为br
		Selection.insertNewline = function(container) {
			Selection.insertHtml(container, "<br />", null);
		};
			
		// 选中区域/光标相对于编辑区域左上角的坐标
		Selection.coordinate = function(container, bStart) {
            try {
                var range = container.ownerDocument.selection.createRange();
				range.collapse(bStart);
                return {
                    x: range.offsetLeft,
                    y: range.offsetTop,
                    h: range.boundingHeight
                };
            } 
            catch (e) {
                return null;
            }
		};
		
		// 选中区域/光标在body的HTML表示字符串中的位置
		Selection.htmlOffset = function(container, bStart) {
			try {
				var range = container.ownerDocument.selection.createRange();
				range.collapse(bStart);
				
				range.pasteHTML('<span id="__tempHtmlOffset__"></span>');
				var pattern = /<span id=(\w*|'|")__tempHtmlOffset__\1><\/span>/i;
				
				// 在IE中，也许会去掉id的引号（regex）
				var offset = container.ownerDocument.body.innerHTML.search(pattern);
				removeElement(container.ownerDocument.getElementById("__tempHtmlOffset__"));
				
				return offset;
			} catch (e) {
				return null;
			}
		};
				
		// 所选区域/光标相对于所在行的行首的偏移量（以可见字符计算）
		// bStart = true：所选区域的左端点为光标；false：右端点为光标
		// 返回：{node：上一行末尾换行符<br>，offset：相对于node的偏移量}
		// 对于第一行，node = null
		Selection.lineOffset = function(container, bStart) {
            var selection = container.ownerDocument.selection;
            if (!selection) {
				return null;
			}
            
			try {                
			    var root = Selection.topNode(container, bStart);
                var range = selection.createRange(), range2 = range.duplicate();
                range.collapse(bStart);
				range.pasteHTML("<span id='__cursor__'></span>");
				var node = container.ownerDocument.getElementById("__cursor__");
				var filter = function(n) {
					return n && n.nodeName && n.nodeName.toLowerCase() == "br";
				};
				var br = null;
                var count = 0;
				if (root) {
					var last = findLastNodeOf(root, node, filter);
                    br = last.result;
                    count += last.acceptCount;
					while (!br) {
						root = root.previousSibling;
						if (root) {
							last = findLastNodeOf(root, null, filter);
                            br = last.result;
                            count += last.acceptCount;
						} else {
							break;
						}
					}
				}
                if (br) {
                    range2.moveToElementText(br);
                    range2.collapse(false);
                } else {
                    range2.moveToElementText(container);
                    range2.collapse(true);
                }
                range.setEndPoint("StartToStart", range2);
                
                return {
                    node: br,
                    line: count + 1,
                    offset: range.text.length
                };
			} catch (e) {
				return null;
			} finally {
				removeElement(container.ownerDocument.getElementById("__cursor__"));
			}
		};
		
		// 选中从from到to的文本（from和to都是lineOffset的返回类型）
		// 注意：不含端点from，但是包含了端点to
		Selection.set = function(container, from, to) {
            function _rangeAt(pos){
                var range = container.ownerDocument.body.createTextRange();
                if (!pos.node) {
                    range.moveToElementText(container);
                    range.collapse(true);
                } else {
                    range.moveToElementText(pos.node);
                    range.collapse(false);
                }
                range.move("character", pos.offset);
                return range;
            }
            
            var range = _rangeAt(from);
            if (to && to != from) {
				range.setEndPoint("EndToEnd", _rangeAt(to));
			}
            range.select();
		};
				
		// 保证光标可见性
		Selection.scrollToCursor = function(container) {
            var selection = container.ownerDocument.selection;
            if (!selection) {
				return null;
			}
			
			try {
                selection.createRange().scrollIntoView();
			} catch (ignored) {
				
			}
		};
		

		// HTML of selection
		Selection.html = function(container) {
			try {
				return container.ownerDocument.selection.createRange().htmlText;
			} catch (e) {
				return null;
			}
		};

		
		// Text(without any HTML markup) of selection
		Selection.text = function(container) {
			try {
				return container.ownerDocument.selection.createRange().text;
			} catch (e) {
				return null;
			}
		};
		
	} else {  // Firefox/Netscape/Opera/Safari
        // This is used to fix an issue with getting the scroll position
        // in Opera.
        var opera_scroll = !window.scrollX && !window.scrollY;	
		
        // Helper for selecting a range object.
        var _addRange = function(range, appWindow) {
            var selection = appWindow.getSelection();
            selection.removeAllRanges();
            selection.addRange(range);
        };
		
        var _getRange = function(appWindow) {
            var selection = appWindow.getSelection();
            if (!selection || selection.rangeCount == 0) 
                return false;
            else 
                return selection.getRangeAt(0);
        };		    
		
		Selection.save = function(appWindow, saveAs) {
            var range = _getRange(appWindow);
			if (!range) {
				return null;
			}
            
            _lastSelection = {
                start: {
                    node: range.startContainer,
                    offset: range.startOffset
                },
                end: {
                    node: range.endContainer,
                    offset: range.endOffset
                },
                window: appWindow,
                scrollX: opera_scroll && appWindow.document.body.scrollLeft,
                scrollY: opera_scroll && appWindow.document.body.scrollTop
            };

            // 为了获得光标处的准确结点而非其某个父节点，要沿着DOM树向下找到叶（即取得offset=0的那个结点）
            function _normalize(point){
                while (point.node.nodeType != 3 && point.node.nodeName != "BR") {
                    var newNode = point.node.childNodes[point.offset] || point.node.nextSibling;
                    point.offset = 0;
                    while (!newNode && point.node.parentNode) {
                        point.node = point.node.parentNode;
                        newNode = point.node.nextSibling;
                    }
                    point.node = newNode;
                    if (!newNode) {
						break;
					}
                }
            }
            
            _normalize(_lastSelection.start);
            _normalize(_lastSelection.end);

            if (_lastSelection.start.node) {
				_lastSelection.start.node.selectStart = _lastSelection.start;
			}
            if (_lastSelection.end.node) {
				_lastSelection.end.node.selectEnd = _lastSelection.end;
			}	
			if (saveAs) {
				return _lastSelection;
			}
		};	
		
		Selection.load = function(old) {
            if (!_lastSelection && !old) {
				return;
			}
			var sel;
			if (old) {
				sel = old;
			} else {
				sel = _lastSelection;
			}
            var win = sel.window;
            var range = win.document.createRange();
            
            function setPoint(point, which){
                if (point.node) {
                    // Remove the link back to the selection.
                    delete point.node["select" + which];
                    // Some magic to generalize the setting of the start and end
                    // of a range.
                    if (point.offset == 0) 
                        range["set" + which + "Before"](point.node);
                    else 
                        range["set" + which](point.node, point.offset);
                }
                else {
                    range.setStartAfter(win.document.body.lastChild || win.document.body);
                }
            }
            
            // Have to restore the scroll position of the frame in Opera.
            if (opera_scroll) {
                sel.window.document.body.scrollLeft = sel.scrollX;
                sel.window.document.body.scrollTop = sel.scrollY;
            }
            setPoint(sel.end, "End");
            setPoint(sel.start, "Start");
            _addRange(range, win);
		};
		
        Selection.topNode = function(container, bStart){
            var range = _getRange(container.ownerDocument.defaultView);
            if (!range) 
                return false;
            
            var node = bStart ? range.startContainer : range.endContainer;
            var offset = bStart ? range.startOffset : range.endOffset;
            
            if (node.nodeType == 3) { // Text Node
				if (offset > 0) {
					return _topAncestorOf(node, container);
				} else {
					return _topAncestorBefore(node, container);
				}
			} else if (node.nodeName == "HTML") {
				return (offset == 1 ? null : container.lastChild);
			} else if (node == container) {
				return (offset == 0) ? null : node.childNodes[offset - 1];
			} else {
				if (offset == node.childNodes.length) {
					return _topAncestorOf(node, container);
				} else if (offset == 0) {
					return _topAncestorBefore(node, container);
				} else {
					return _topAncestorOf(node.childNodes[offset - 1], container);
				}
			}
        };
		
		Selection.parent = function(container, bStart) {
			var range = _getRange(container.ownerDocument.defaultView);
            if (!range) {
				return null;
			}
			try {
				range.collapse(bStart);
				var s = document.createElement("span");
				range.surroundContents(s);
				var p = s.parentNode;
				removeElement(s);
				return p;
			} catch (e) {
				return null;
			}
		};
	/*
	Selection.replace = function(oldNode, newNode, container) {
			var win = container.ownerDocument.defaultView;
            var range = win.document.createRange();
            range.selectNode(oldNode);
            
            //删除并插入新值，注意将输入光标置于新值之后 
            range.deleteContents();
            range.insertNode(newNode);
            range.setEndAfter(newNode);
			range.collapse(false);
			
            _addRange(range, win);
		};
*/
		
		// 用node替换选中区域，注意：如果替换导致DOM树结构不对则会抛出异常
		Selection.replace = function(container, node) {
			var win = container.ownerDocument.defaultView;
            var range = _getRange(win);
            if (!range) {
				return;
			}
			
			try {
				range.deleteContents();
				range.insertNode(node);
				range.setEndAfter(node);
				range.collapse(false);
				_addRange(range, win);
			} catch (ignored) {
				
			}
				
		};
		
		Selection.focusAfter = function(container, node) {
            var win = container.ownerDocument.defaultView;
			var range = win.document.createRange();
            range.setStartBefore(container.firstChild || container);
            // In Opera, setting the end of a range at the end of a line
            // (before a BR) will cause the cursor to appear on the next
            // line, so we set the end inside of the start node when
            // possible.
            if (node && !node.firstChild) {
				range.setEndAfter(node);
			} else if (node) {
				range.setEnd(node, node.childNodes.length);
			} else {
				range.setEndBefore(container.firstChild || container);
			}
            range.collapse(false);
            _addRange(range, win);
		};
		
		Selection.focusBefore = function(container, node) {
			Selection.focusAfter(container, node.previousSibling);
		};
		
		Selection.insertNode = function(container, node, bStart) {
			var win = container.ownerDocument.defaultView;
            var range = _getRange(win);
            if (!range) 
                return;
			if (bStart != null && bStart != undefined) {
				range.collapse(bStart);
			}		        
            // On Opera, insertNode is completely broken when the range is
            // in the middle of a text node.
            if (win.opera && range.startContainer.nodeType == 3 && range.startOffset != 0) {
                var start = range.startContainer, text = start.nodeValue;
                start.parentNode.insertBefore(win.document.createTextNode(text.substr(0, range.startOffset)), start);
                start.nodeValue = text.substr(range.startOffset);
                start.parentNode.insertBefore(node, start);
            } else {
                range.insertNode(node);
            }
            
            range.setEndAfter(node);
            range.collapse(false);
            _addRange(range, win);
		};
		
		Selection.insertNewline = function(container) {
			Selection.insertNode(container, container.ownerDocument.createElement("br"), null);
		};
		
		Selection.coordinate = function(container, bStart) {
			var offset = {x: 0, y: 0, h: 0};
			
			var win = container.ownerDocument.defaultView;
			var range = _getRange(win);
			range.collapse(bStart);
            var e = range.endOffset;
			
            var temp = container.ownerDocument.createElement('span');
            var temp2 = temp;
            temp.style.borderLeft = '1px solid red';
            range.insertNode(temp);
            offset.h = temp2.offsetHeight;
            while (temp2.offsetParent) {
                offset.x += temp2.offsetLeft;
                offset.y += temp2.offsetTop;
                temp2 = temp2.offsetParent;
            }
            removeElement(temp);
            if (e - range.endOffset != 0) {
                range.setEnd(range.endContainer.nextSibling, e - range.endOffset);
                _addRange(range, win);
            }
			
			return offset;
		};
		
		Selection.htmlOffset = function(container, bStart) {
            // 当ifrmae无内容时，无法再oldCursor处插入s，注意，只有在第一次输入字符时才有此bug，如果在输入后将
            // iframe的内容全部删除，不会有此bug
			if (container.innerHTML == "") {
                return 0;
            }
			
			var win = container.ownerDocument.defaultView;
			      
            var range = _getRange(win);
			range.collapse(bStart);
            var oldPos = range.endOffset;
            
            var s = document.createElement("span");
            range.insertNode(s);
            
            var index = container.innerHTML.search(/<span><\/span>/i);
            
            removeElement(s);
            if (oldPos - range.endOffset != 0) {
                range.setEnd(range.endContainer.nextSibling, oldPos - range.endOffset);
                _addRange(range, win);
            }
			return index;
		};
		
		Selection.lineOffset = function(container, bStart) {
			var win = container.ownerDocument.defaultView;
            var range = _getRange(win);
            if (!range) {
				return;
			}
            try {
				var root = Selection.topNode(container, bStart);
                range = range.cloneRange();
                range.collapse(bStart);
				var s = container.ownerDocument.createElement("span");
				s.id = "__cursor__";
				range.insertNode(s);
				var node = container.ownerDocument.getElementById("__cursor__");
				var filter = function(n) {
					return n && n.nodeName && n.nodeName.toLowerCase() == "br";
				};
				var br = null;
                var count = 0;
				if (root) {
					var last = findLastNodeOf(root, node, filter);
                    br = last.result;
                    count += last.acceptCount;
					while (!br) {
						root = root.previousSibling;
						if (root) {
							last = findLastNodeOf(root, null, filter);
                            br = last.result;
                            count += last.acceptCount;
						} else {
							break;
						}
					}
				}
                
				if (br) {
					range.setStartAfter(br);
				} else {
					range.setStartBefore(container);
				}
				
                return {
                    node: br,
                    line: count + 1,
                    offset: range.toString().length
                };
            } catch (e) {
                return null;
            } finally {
				removeElement(container.ownerDocument.getElementById("__cursor__"));
			}
		};
		
		Selection.set = function(container, from, to) {
            var win = container.ownerDocument.defaultView;
			var range = win.document.createRange();
            
			function nodesAfter(root, node, filter) {
				// 返回按照先根遍历序的node之后的结点集合
				var result = [];
				
				function rootFirstTraverse(r) {
					if (!r) {
						return;
					}
					if (r == node || !filter || filter(r)) {
						result.push(r);
					}
					for (var c = r.firstChild; c != null; c = c.nextSibling) {
						rootFirstTraverse(c);
					}
				}
				
				rootFirstTraverse(root);
				if (result.length > 0 && node) {
					var i = 0, found = false;
					for (; i < result.length; ++i) {
						if (node == result[i]) {
							found = true;
							break;
						}
					}
					if (found) {
						++i;
						if (i >= result.length) {
							result = [];
						} else {
							result = result.splice(i, result.length - i);
						}
					}
				}
				return result;
			}
			
            function setPoint(node, offset, side){
				var root = null;
                if (!node) {
					root = container.firstChild;
				} else {
					root = node.nextSibling;
				}
                
                if (offset == 0) {
					if (!node && !root) {
						return false;
					}
					if (root) {
						range["set" + side + "Before"](root);
					} else {
						range["set" + side + "After"](node.nextSibling);
					}
                    return true;
                }
                
				while (root && root.parentNode != container) {
					root = root.parentNode;
				}
				var filter = function(n) {
					return n && n.nodeType == 3;  // TextNode
				};
				var after = nodesAfter(root, node, filter);

				function moveEndPoint() {
					while (after.length > 0) {
						var cur = after.shift();
						var length = cur.nodeValue.length;
						if (length >= offset) {
							range["set" + side](cur, offset);
							return true;
						}
						offset -= length;
					}
					return false;
				}
				
				while (!moveEndPoint()) {
					if (!root || !root.nextSibling) {
						return false;
					}
					root = root.nextSibling;
					after = nodesAfter(root, null, filter);
				}
				return true;
            }
            
            to = to || from;
            if (setPoint(to.node, to.offset, "End") && setPoint(from.node, from.offset, "Start")) {
				_addRange(range, win);
			}
		};
				
		Selection.scrollToCursor = function(container) {
            var body = container.ownerDocument.body, win = container.ownerDocument.defaultView;
            var element = Selection.topNode(container, true) || container.firstChild;
            
            // In Opera, BR elements *always* have a scrollTop property of zero. Go Opera.
            while (element && !element.offsetTop) {
				element = element.previousSibling;
			}
            
            var y = 0, pos = element;
            while (pos && pos.offsetParent) {
                y += pos.offsetTop;
                pos = pos.offsetParent;
            }
            
            var screen_y = y - body.scrollTop;
            if (screen_y < 0 || screen_y > win.innerHeight - 10) 
                win.scrollTo(0, y);
		};
		
/*
		Selection.html = function(container) {
			try {
                var win = container.ownerDocument.defaultView;
                var range = _getRange(win);
                
				var content = range.cloneContents();
				var s = document.createElement("span");
				s.appendChild(content);
				
				var result = s.innerHTML;
				s = null;
				
				return result;
			} catch (e) {
				return null;
			}
		};
*/
		
		Selection.text = function(container) {
			try {
				var win = container.ownerDocument.defaultView;
                var range = _getRange(win); 
				return range.toString();
			} catch (e) {
				return null;
			}
		};
	} // if Firefox/Netscape/Opera/Safari
	
})();
