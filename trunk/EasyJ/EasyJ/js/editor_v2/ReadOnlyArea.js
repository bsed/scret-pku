var ReadOnlyArea = (function() {
    
    var ReadOnlyArea = function(holder, index) {
        this.firstLineUCID = "";
        this.firstLineActionID = "";
        this.useCaseMap = {};
        this.actionMap = {};
        
        // 1) Delete "graph info"
        // 2) Create multi-view hashmaps
        for (var c = holder.firstChild; c != null;) {
            var next = c.nextSibling;
            if (c.id == "__Graph_String__") {
                removeElement(c);
            } else if (c.id == "__Map_usecase__") {
                this.useCaseMap = JSON.parse(c.innerHTML);
                removeElement(c);
            } else if (c.id == "__Map_action__") {
                this.actionMap = JSON.parse(c.innerHTML);
                removeElement(c);
            } else if (c.id == "__First_ID_usecase__") {
                this.firstLineUCID = c.innerHTML;
                removeElement(c);
            } else if (c.id == "__First_ID_action__") {
                this.firstLineActionID = c.innerHTML;
                removeElement(c);
            } else if (c.nodeType == 1) {
                break;
            }
            c = next;
        }
        
        // 3) Refill holder, add usecase & action info
        if (!this.useCaseMap.index || this.useCaseMap.index == 0) {
            return;
        }
        
        var self = this;
        function createUseCaseBegin(thead, tdata, ucid) {
            $(thead).addClass("use_case_begin");
            $(tdata).addClass("use_case_begin");
            thead.appendChild(document.createTextNode("用况：" + self.useCaseMap[ucid].name));
        }
        
        function createUseCaseEnd(thead, tdata) {
            $(thead).addClass("use_case_end");
            $(tdata).addClass("use_case_end");
        }
        var line = 1;
        var leftCol = document.createElement("div");
        leftCol.className = "use_case_left";
        var rightCol = document.createElement("div");
        rightCol.className = "use_case_right";
        
        var th = document.createElement("div");
        th.id = "th_" + index + "_" + line;
        th.className = "table_header";
        var td = document.createElement("div");
        td.id = "td_" + index + "_" + line;
        td.className = "table_datagrid";
        leftCol.appendChild(th);
        rightCol.appendChild(td);

        var dumb = document.createElement("span");
        addSpaces(dumb);
        dumb.className = "empty_line";
        td.appendChild(dumb);
        var emptyLine = true; 
        
        var lastUCID = null;
        if (this.firstLineUCID != "" ) {
            lastUCID = this.firstLineUCID;
            createUseCaseBegin(th, td, this.firstLineUCID);
        }           
        
        function checkEmptyLine() {
            if (emptyLine) {
                emptyLine = false;
                for (var c = td.firstChild; c != null; c = c.nextSibling) {
                    if (c.nodeType == 1 && c.className == "empty_line") {
                        removeElement(c);
                        return;
                    }
                }
            }
        }
        
        function fillTable(root) {
            if (root.nodeType == 3) {  // TextNode
                if (root.nodeValue != "") {
                    checkEmptyLine();
                    td.appendChild(document.createTextNode(root.nodeValue));
                }
                return;
            } 
            if (root.nodeType == 1) {  // HTML element
                if (isTerm(root) || isKeyword(root)) {
                    checkEmptyLine();
                    var node = document.createElement("span");
                    node.className = root.className;
                    node.appendChild(document.createTextNode(innerText(root)));
                    td.appendChild(node);
                    return;
                }
                if (root.nodeName.toLowerCase() == "br") {
                    // Create next line
                    ++line;
                    var nextTh = document.createElement("div");
			        nextTh.id = "th_" + index + "_" + line;
			        nextTh.className = "table_header";
			        var nextTd = document.createElement("div");
			        nextTd.id = "td_" + index + "_" + line;
			        nextTd.className = "table_datagrid";
			        leftCol.appendChild(nextTh);
			        rightCol.appendChild(nextTd);
			
			        var dumb = document.createElement("span");
			        addSpaces(dumb);
			        dumb.className = "empty_line";
			        nextTd.appendChild(dumb);
			        emptyLine = true; 
			        
                    // Get usecase id for next line
                    var ucID = getClassMatch(root, /uc_/i);
                    if (ucID != "") {
                        if (lastUCID) {
                            if (lastUCID != ucID) {
                                lastUCID = ucID;
                                createUseCaseEnd(th, td);
                                createUseCaseBegin(nextTh, nextTd, ucID);
                            }
                        } else {
                            lastUCID = ucID;
                            createUseCaseBegin(nextTh, nextTd, ucID);
                        }
                    } else {
                        lastUCID = null;
                        createUseCaseEnd(th, td);
                    }
                    th = nextTh;
                    td = nextTd;
                    return;
                }
            }
            for (var ch = root.firstChild; ch != null && ch.className != "trtitle"; ch = ch.nextSibling) {
                fillTable(ch);
            }
        }
        
        fillTable(holder);
        if (lastUCID) {
            createUseCaseEnd(th, td);
        }
        //删除末尾的空行
        var foundEmpty = false;
        for (var lastTd = rightCol.lastChild; lastTd != null;) {
            var prev = lastTd.previousSibling;
            foundEmpty = false;
            for (var c = lastTd.firstChild; c != null; c = c.nextSibling) {
                if (c.className == "empty_line") {
                    removeElement(lastTd);
                    removeElement(leftCol.lastChild);
                    foundEmpty = true;
                    break;
                }
            }
            if (!foundEmpty) {
                break;
            }
            lastTd = prev;
        }
        
        for (var c = holder.firstChild; c != null && c.className != "trtitle";) {
            var next = c.nextSibling;
            removeElement(c);
            c = next;
        }
        insertAtStart(leftCol, holder);
        insertAtStart(rightCol, holder);
        // 4) TODO: Create a button to display/hide usecase info
    };
    return ReadOnlyArea;
})();