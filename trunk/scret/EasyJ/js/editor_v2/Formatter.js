var Formatter = (function() {
    
    var nonSpace = /[^\s\u00a0]/;
    var formatElements = {
        "if": {
            entry: ["{", "｛"],
            exit: ["}", "｝"]
        },
        "else": {
            entry: ["{", "｛"],
            exit: ["}", "｝"]
        } 
    };
    
    var doc = null;
    var contextStack = [];
    var currentContext = null;
    var lineBegin = true;
    
    // Find index_of_keychar(entry or exit) of context in target string
    // point == "entry" or "exit"
    function indexOfKeychar(target, context, point) {
        var chars = context[point];
        for (var i = 0; i < chars.length; ++i) {
            var index = target.indexOf(chars[i]);
            if (index >= 0 ) {
                return index;
            }
        }
        return -1;
    }
    
    function indent() {
        var spaces = "";
        for (var i = contextStack.length; i > 0; --i) {
            spaces += (nbsp + nbsp + nbsp + nbsp);
        }
        return spaces;
    }
    
    function changeContext(className) {
        var keyword = className.substring("keyword_".length);
        
        if (formatElements[keyword]) {
            currentContext = formatElements[keyword];
        }
    }
    
    function addContext() {
        if (currentContext) {
            contextStack.push(currentContext);
            currentContext = null;
        }
    }
    
    function _format(root) {
        if (isKeyword(root)) {
            // do not addContext here, but set currentContent only
            // when we meet the currentContent.entry, we actually addContext then.
            changeContext(root.className);
            if (lineBegin) {
                var preWhitespace = indent();
                if (preWhitespace != "") {
                    root.parentNode.insertBefore(doc.createTextNode(preWhitespace), root);
                }
                lineBegin = false;
            }            
            // We need to return the next node in the tree
            return root.nextSibling;
        }
        
        if (root.nodeType == 3) {
            var next = root.nextSibling;
            if (lineBegin) {
                var beginIndex = root.nodeValue.search(nonSpace);
                if (beginIndex >= 0) {
                    // if the first nonspace char is exitPoint, then we need to exit context first
                    var firstNonSpaceChar = root.nodeValue.charAt(beginIndex);
                    var foundExit = false;
                    if (contextStack.length > 0) {
                        var exitChars = contextStack[length - 1].exit;
                        for (var i = 0; i < exitChars.length; ++i) {
                            if (firstNonSpaceChar == exitChars[i]) {
                                foundExit = true;
                                contextStack.pop();
                                break;
                            } 
                        }
                    }
                    root.nodeValue = indent() + root.nodeValue.substring(beginIndex);
                    lineBegin = false;
                    if (foundExit) {
                        return next;
                    }
                } else {
                    removeElement(root);  // root is an indent whitespace node created before now
                    return next;
                }
            }
            if (currentContext) {
                // Need to find entry of currentContext
                var entryPoint = indexOfKeychar(root.nodeValue, currentContext, "entry");
                if (entryPoint >= 0) {
                    addContext();
                }
            } else {
                // Need to find exit of currentContext
                if (contextStack.length > 0) {
                    var exitPoint = indexOfKeychar(root.nodeValue, contextStack[contextStack.length - 1], "exit");
                    if (exitPoint >= 0) {
                        contextStack.pop();
                    }
                }
            }     
            return next;
        }
        if (root.nodeType == 1 && root.nodeName.toLowerCase() == "br") {
            lineBegin = true;
            return root.nextSibling;
        }
        for (var c = root.firstChild; c != null; c = _format(c)) {
            // empty loop body
        }
        return root.nextSibling;
    }
    
    var Formatter = {
        format: function(container) {
             doc = container.ownerDocument;
             contextStack = [];
             currentContext = null;
             lineBegin = true;
             _format(container);
             removeEmptyNodes(container);
        }
    };
    
    return Formatter;
})();