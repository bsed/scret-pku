/* 文本编辑器光标底层操作
 * 
 */
var Selection = (function() {
	
	var Selection = {};
	var container = document.body;
	if ($.browser.msie) {  // IE
		
		// 所选区域/光标相对于所在行的行首的偏移量（以可见字符计算）
		// bStart = true：所选区域的左端点为光标；false：右端点为光标
		// 返回：{node：上一行末尾换行符<br>，offset：相对于node的偏移量}
		// 对于第一行，node = null
		Selection.get = function(bStart) {
            var selection = document.selection;
            if (!selection) {
				return null;
			}
            
			try {                
                var range = selection.createRange(), range2 = range.duplicate();
                range.collapse(bStart);
				range.pasteHTML("<span id='__cursor__'></span>");
				var node = document.getElementById("__cursor__");
				var filter = function(n) {
					return n && n.nodeName && n.nodeName.toLowerCase() == "br";
				};
				var lastBr = Dom.findLastBefore(container, node, filter);
                if (lastBr.result) {
                    range2.moveToElementText(lastBr.result);
                    range2.collapse(false);
                } else {
                    range2.moveToElementText(container);
                    range2.collapse(true);
                }
                range.setEndPoint("StartToStart", range2);
                
                return {
                	base: lastBr.result,
                    line: lastBr.count + 1,
                    offset: range.text.length
                };
			} catch (e) {
				return null;
			} finally {
				$('#__cursor__').remove();
			}
		};
		
		// 选中从from到to的文本（from和to都是lineOffset的返回类型）
		// 注意：不含端点from，但是包含了端点to
		Selection.set = function(from, to) {
            function _rangeAt(pos){
                var range = container.createTextRange();
                if (!pos.base) {
                    range.moveToElementText(container);
                    range.collapse(true);
                } else {
                    range.moveToElementText(pos.base);
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
		
		// XY offset 
		Selection.getXY = function(bStart) {
			
		};
		
		Selection.text = function() {
			try {
				return document.selection.createRange().text;
			} catch (e) {
				return null;
			}
		};
		
		Selection.scrollToCursor = function() {
			try {
                document.selection.createRange().scrollIntoView();
			} catch (ignored) {
				
			}
		};

	} else {
		
	}
	return Selection;
})();