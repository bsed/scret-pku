/* A few useful utility functions. */

// The non-breaking space character.
var nbsp = "\u00a0";
// Unfortunately, IE's regexp matcher thinks non-breaking spaces
// aren't whitespace.
var realWhiteSpace = /^[\s\u00a0]*$/;

function addSpaces(node){
    node.appendChild(document.createTextNode(nbsp + nbsp + nbsp + nbsp + nbsp));
}

function spaceToNbsp(str) {
    return str.replace(/[\s\u00a0]/g, "&nbsp;");
}

function hasClassMatch(node, regex) {
	if (node.className) {
		var names = node.className.split(/\s+/);
		for (var i = 0; i < names.length; ++i) {
			if (names[i].search(regex) == 0) {
				return true;
			}
		}
	}
	return false;
}

function getClassMatch(node, regex) {
	if (node.className) {
		var names = node.className.split(/\s+/);
		for (var i = 0; i < names.length; ++i) {
			if (names[i].search(regex) == 0) {
				return names[i];
			}
		}
	}
	return "";
}

function isOp(node){
    return node && node.nodeType == 1 && $(node).hasClass("action_op");
}

function isName(node){
    return node && node.nodeType == 1 && $(node).hasClass("action_name");
}

function isContent(node) {
	return node && node.nodeType == 1 && $(node).hasClass("action_content");
}

function isTerm(node) {
	return node && node.nodeType == 1 && (node.className == "data" || node.className == "role");
}

function isKeyword(node) {
	return node && node.nodeType == 1  && node.className.search(/keyword_/i) == 0;
}

function isHeader(node){
    return node.nodeType == 1 && node.nodeName.toLowerCase() == "div" && $(node).hasClass("table_header");
}

function isBegin(node){
    return isHeader(node) && $(node).hasClass("use_case_begin");
}

function isEnd(node){
    return isHeader(node) && $(node).hasClass("use_case_end");
}
function setDefaults(target, defaults){
    for (var option in defaults) {
        if (!target.hasOwnProperty(option)) {
            target[option] = defaults[option];
        }
    }
}
		
// Insert a DOM node after another node.
function insertAfter(newNode, oldNode) {
  var parent = oldNode.parentNode;
  var next = oldNode.nextSibling;
  if (next)
    parent.insertBefore(newNode, next);
  else
    parent.appendChild(newNode);
  return newNode;
}

// Insert a dom node at the start of a container.
function insertAtStart(node, container) {
  if (container.firstChild)
    container.insertBefore(node, container.firstChild);
  else
    container.appendChild(node);
  return node;
}


function insertArrayAfter(array, node) {
	if (array && array.length > 0) {
		insertAfter(array[0], node);
		for (var i = 1; i < array.length; ++i) {
			insertAfter(array[i], array[i - 1]);
		}
	}
}

function insertArrayAtStart(array, container) {
	if (array && array.length > 0) {
		insertAtStart(array[0], container);
		for (var i = 1; i < array.length; ++i) {
			insertAfter(array[i], array[i - 1]);
		}
	}
}

function replaceAllWithChildren(container, filter) {
	
	function replaceOne(root) {
		if (!root) {
			return false;
		}
		if (filter(root) && root.nodeType == 1) {
			setOuterHtml(root, root.innerHTML);
			return true;
		}
		for (var c = root.firstChild; c != null; c = c.nextSibling) {
			if (replaceOne(c)) {
				return true;
			}
		}
		return false;
	}
	
    while (replaceOne(container)) {
		// empty loop body
    }
}

function removeElement(node) {
  if (node && node.parentNode)
    node.parentNode.removeChild(node);
}

	
function removeElementRange(start, end){
    var next = start.nextSibling;
    while (start && start != end) {
        removeElement(start);
        start = next;
        next = start.nextSibling;
    }
    removeElement(end);
}
	
function clearElement(node) {
  while (node.firstChild)
    node.removeChild(node.firstChild);
}
    
// Check whether a node is contained in another one.
function isAncestor(node, child) {
  while (child = child.parentNode) {
    if (node == child)
      return true;
  }
  return false;
}

// 计数以先根遍历序排列的DOM树root中，在node之前的满足filter条件的结点个数
function countNodeBefore(root, node, filter) {
	var count = 0;
	
	function rootFirstTraverse(r) {
		if (r == node) {
			return true; // true means traverse completed
		} else if(filter(r)) {
			++count;
		}
		for (var c = r.firstChild; c != null; c = c.nextSibling) {
			if (rootFirstTraverse(c)) {
				return true;
			}
		}
		return false;
	}
	
	rootFirstTraverse(root);
	
	return count;
}

// 以先根遍历序找到以root为根的树中，在node之前的符合filter条件的结点
// node为空则不限制最后一个结点，filter为空则认为所有结点都符合条件
function findLastNodeOf(root, node, filter) {
	
	var result = null;
    var acceptCount = 0;
	
	function accept(n) {
	    if (!filter || filter(n)) {
	        return true;
	    }
	    return false;      
	}
	
	function rootFirstTraverse(r) {
        if (accept(r)) {
            result = r;
            ++acceptCount;
        }
	    if (node && r == node) {
	        return true;   // End traverse
	    }
	    
	    for (var c = r.firstChild; c != null; c = c.nextSibling) {
	        if (rootFirstTraverse(c)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	rootFirstTraverse(root);
	return { "result": result, "acceptCount": acceptCount };
}

// 以先根遍历序返回树root中node之后满足filter条件的第一个结点，node为空表示从root
// 第一个结点算起，filter为空则认为所有结点都满足条件
// nodesBetween是一个数组，放置node和返回值之间的所有结点
function findFirstNodeOf(root, node, filter, nodesBetween) {
	var startFilter = false;
	if (!node) {
		startFilter = true;
	}
	
	function accept(n) {
		return startFilter && (!filter || filter(n));
	}
	
	function findFirstNode(r) {
		if (!r) {
			return null;
		}
		if (accept(r)) {
			if (nodesBetween) {
				nodesBetween.push(r);
			}
			return r;
		}
		if (r == node) {
			startFilter = true;
		}
		if (startFilter && nodesBetween) {
			nodesBetween.push(r);
		}
		
		for (var c = r.firstChild; c != null; c = c.nextSibling) {
			if (findFirstNode(c)) {
				return c;
			}
		}
		return null;
	}
	return findFirstNode(root);
}


var isIE = document.selection && document.selection.createRangeCollection;

// Stripe \r\n
function fixNewline(html){
    html = html || "";
    var regex = /\r\n|\n/g;
    return html.replace(regex, "");
}

// Standardize a few unportable event properties.
function normalizeEvent(event) {
	event = event || window.event;
  if (!event.stopPropagation) {
    event.stopPropagation = function() {this.cancelBubble = true;};
    event.preventDefault = function() {this.returnValue = false;};
  }
  if (!event.stop) {
    event.stop = function() {
      this.stopPropagation();
      this.preventDefault();
    };
  }

  if (event.type == "keypress") {
    if (event.charCode === 0 || event.charCode == undefined)
      event.code = event.keyCode;
    else
      event.code = event.charCode;
    event.character = String.fromCharCode(event.code);
  }
  event.source = event.srcElement || event.target;
  event.key = event.keyCode || event.which;
  return event;
}

function objToString(obj) {
	var str = "{";
	for(var name in obj) {
		str += name + ": " + obj[name] +"; ";
	}
	str += "}";
	return str;
}

function arrayToString(array) {
	var str = "[ ";
	if (array.length > 0) {
		str += outerHtml(array[0]);
		for (var i = 1; i < array.length; ++i) {
			str += ", " + outerHtml(array[i]);
		}
	}
	str += " ]";
	return str;
}

function stripeAllHtmlTags(html) {
	html = html || "";
	var regex = /<("[^"]*"|'[^']*'|[^<>'"])*>/g;
	return html.replace(regex, "");
}

function outerHtml(tag) {
    try {
        if (!tag) {
            return "null";
        }
        if (tag.nodeType == 3) {
            return tag.nodeValue == "" ? "<empty>" : tag.nodeValue;
        }
        if (tag.outerHTML) {
            return tag.outerHTML;
        }
        var a = tag.attributes;
        var str = "<" + tag.tagName;
        for (var i = 0; i < a.length; ++i) {
            if (a[i].specified) 
                str += " " + a[i].name + '="' + a[i].value + '"';
        }
        if (!hasChildren(tag)) {
            return str + " />";
        } else {
            return str + ">" + tag.innerHTML + "</" + tag.tagName + ">";
        }
    } catch (e) {
        return "<no outer HTML>";
    }
}

function hasChildren(tag) {
	return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(tag.tagName.toLowerCase());
}

function removeEmptyNodes(root) {
    if (root.nodeType == 1) {
        if (hasChildren(root) && (!root.firstChild || root.innerHTML == "")) {
            var next = root.nextSibling;
            removeElement(root);
            return next;
        }
    }
    
    for (var c = root.firstChild; c != null; c = removeEmptyNodes(c)) {
        // empty loop body
    }
    
    return root.nextSibling;
}
function setOuterHtml(node, html) {
	try {
		if (!node) {
			return;
		}
		if (node.outerHTML) { // IE
			node.outerHTML = html;
			return;
		}
		if (node.nodeType == 1) { // TextNode has no outerHTML attr in IE
		    var r = node.ownerDocument.createRange();
			r.setStartBefore(node);
			var newNode = r.createContextualFragment(html);
			node.parentNode.replaceChild(newNode, node);
		}
	} catch (ignored) {
		
	}
}

function innerText(tag) {
	try {
		if (tag.nodeType == 3) {
			return tag.nodeValue;
		} 
		if (tag.innerText) {
			return tag.innerText;
		}
		return stripeAllHtmlTags(tag.innerHTML);
	} catch (e) {
		return "<no inner Text>";
	}
}

function getPosition(elem){
    var x = elem.offsetLeft;
    var y = elem.offsetTop;
    var e = elem;
    while (e.offsetParent) {
        e = e.offsetParent;
        x += e.offsetLeft;
        y += e.offsetTop;
    }
    return {
        x: x,
        y: y
    };
}

function multiAlert(msg, str, charsPerAlert) {
    var times = str.length / charsPerAlert;
    var last = 0;
    for (var i = 1; i <= times; ++i) {
        alert(msg + " part" + i + "\n" + str.substring(last, last + 3000));
        last += 3000;
    }
    if (last < str.length) {
        alert(msg + " final part\n" + str.substring(last));
    }
}

var Version = {

        addTag: function(container){
			
            function removeTag(container){
                var filter = function(node){
                    return node && node.className == "version";
                };
                replaceAllWithChildren(container, filter);
            }
            
            if ($(".version").length > 0) {
                removeTag(container);
            }
            var doc = container.ownerDocument;
            var tagCount = 0;
            
            function addTagForTextNode(node){
                var tags = [];
                var text = node.nodeValue;
                for (var i = 0; i < text.length; ++i) {
                    var s = doc.createElement("span");
                    s.className = "version";
                    s.id = "" + (tagCount++);
                    s.appendChild(doc.createTextNode(text.charAt(i)));
                    tags.push(s);
                }
                insertArrayAfter(tags, node);
                var last = node;
                for (var j = 0; j < tags.length; ++j) {
                    last = last.nextSibling;
                }
                removeElement(node);
                return last.nextSibling;
            }
            
            function addTagForTree(root){
                if (root.nodeType == 3 && root.nodeValue != "") {
                    return addTagForTextNode(root);
                }
                for (var c = root.firstChild; c != null;) {
                    var next = addTagForTree(c);
                    if (next == -1) {
                        c = c.nextSibling;
                    }
                    else {
                        c = next;
                    }
                }
                return -1;
            }
            
            addTagForTree(container);
            return tagCount;
        }
};

var keymap = [];
keymap[8] = "backspace"; keymap[9] = "tab"; 
keymap[13] = "enter"; keymap[16] = "shift";
keymap[17] = "ctrl"; keymap[18] = "alt";
keymap[32] = "space"; 
keymap[37] = "left"; keymap[38] = "up";
keymap[39] = "right"; keymap[40] = "down";
keymap[46] = "delete";
keymap[229] = "<Chinese Pinyin Input>"
for (i = 48; i <= 57; ++i) {
	keymap[i] = i - 48;
}
keymap[65] = "a"; keymap[66] = "b";
keymap[67] = "c"; keymap[68] = "d";
keymap[69] = "e"; keymap[70] = "f";
keymap[71] = "g"; keymap[72] = "h";
keymap[73] = "i"; keymap[74] = "j";
keymap[75] = "k"; keymap[76] = "L";
keymap[77] = "m"; keymap[78] = "n";
keymap[79] = "o"; keymap[80] = "p";
keymap[81] = "q"; keymap[82] = "r";
keymap[83] = "s"; keymap[84] = "t";
keymap[85] = "u"; keymap[86] = "v";
keymap[87] = "w"; keymap[88] = "x";
keymap[89] = "y"; keymap[90] = "z";
