var Version = (function(){

    var Version = {
		
        // 将那些不属于任何Version标签的结点标记为“add”
        addChangeTag: function(container){
        
            function needAddTag(node){
                if (!node) {
                    return false;
                }
                if (node.nodeType == 3) {
                    return true;
                }
                if (node.needAddTag == undefined || node.needAddTag == null) {
                    node.needAddTag = false;
                }
                else {
                    return node.needAddTag;
                }
                if (node.className == "version") {
                    return false;
                }
                for (var c = node.firstChild; c; c = c.nextSibling) {
                    if (!needAddTag(c)) {
                        return false;
                    }
                }
                node.needAddTag = true;
                return true;
            }
            
            function addTagForNode(node){
                var doc = container.ownerDocument;
                var tag = doc.createElement("span");
                tag.className = "add";
                tag.appendChild(node.cloneNode(true));
                node.parentNode.replaceChild(tag, node);
                return tag.nextSibling;
            }
            
            function add(root){
                if (needAddTag(root)) {
                    return addTagForNode(root);
                }
                for (var c = root.firstChild; c;) {
                    if (c.className == "version") {
                        c = c.nextSibling;
                        continue;
                    }
                    var next = add(c);
                    if (next == -1) {
                        c = c.nextSibling;
                    }
                    else {
                        c = next;
                    }
                }
                return -1;
            }
            
            function mergeTag(tag){
                var next = tag.nextSibling;
                if (next && next.className == "add") {
                    var last = tag.lastChild;
                    insertArrayAfter(next.childNodes, last);
                    removeElement(next);
                    return tag;
                }
                return null;
            }
            
            function merge(root){
                if (root.className == "add") {
                    return mergeTag(root);
                }
                for (var c = root.firstChild; c != null;) {
                    var next = merge(c);
                    if (next) {
                        c = next;
                    }
                    else {
                        c = c.nextSibling;
                    }
                }
                return null;
            }
            
            add(container);
            merge(container);
        },
        
        compare: function(parent, tagCount, oldVal, newVal){
            var compareHolder = document.createElement("div");
            compareHolder.id = "compareHolder";
            compareHolder.style.display = "none";
            var oldContent = document.createElement("iframe");
            var newContent = document.createElement("iframe");
            compareHolder.appendChild(oldContent);
            compareHolder.appendChild(newContent);
            parent.appendChild(compareHolder);
            
            with (oldContent.contentWindow.document) {
                open();
                write("<html><head></head><body>" + oldVal + "</body></html>");
                close();
            }
            with (newContent.contentWindow.document) {
                open();
                write("<html><head></head><body>" + newVal + "</body></html>");
                close();
            }
            var oldDoc = oldContent.contentWindow.document;
            var newDoc = newContent.contentWindow.document;
            
            Version.addChangeTag(newDoc.body);
			
            var results = "";
            for (var i = 0; i < tagCount; i++) {
                var a = "" + i;
                
                var cc = a;
                
                
                if (!newDoc.getElementById(cc)) {
                    var enter = i;
                    while (!newDoc.getElementById(cc)) {
                        i++;
                        cc = "" + i;
                    }
                    var b = cc;
                    
                    if (enter == "0") {
                        var middle0 = "";
                        
                        for (var k = enter; k < i; k++) {
                            middle0 = middle0 + '<span id ="' + k + '"><span class="delete">' + innerText(oldDoc.getElementById("" + k)) + '</span></span>';
                        }
						//newDoc.getElementById(b).outerHTML = middle0 + newDoc.getElementById(b).outerHTML;
						setOuterHtml(newDoc.getElementById(b), middle0 + outerHtml(newDoc.getElementById(b)));
                    }
                    else 
                        if (newDoc.getElementById(String(enter - 1)).innerHTML.length == 1) {
                            var middle = "";
                            
                            for (var k = enter; k < i; k++) {
                            
                                middle = middle + '<span id ="' + k + '"><span class="delete">' + innerText(oldDoc.getElementById("" + k)) + '</span></span>';
                                
                            }
							//newDoc.getElementById(b).outerHTML = middle + newDoc.getElementById(b).outerHTML;
                            setOuterHtml(newDoc.getElementById(b), middle + outerHtml(newDoc.getElementById(b)));
                        }
                        else {
                            var middle2 = "";
                            for (var k2 = enter; k2 < i; k2++) {
                                middle2 = middle2 + '<span id ="' + k2 + '"><span class="delete">' + innerText(oldDoc.getElementById("" + k2)) + '</span></span>';
                            }
                            //newDoc.getElementById(b).outerHTML = middle2 + newDoc.getElementById(b).outerHTML;
							setOuterHtml(newDoc.getElementById(b), middle2 + outerHtml(newDoc.getElementById(b)));
                        }
                    
                }
                //�ж�������
                if (innerText(newDoc.getElementById(a)).length >= 2) {
                    var add = new Array();
                    add = newDoc.getElementById(a).innerHTML.split("");
                    middle1 = "";
                    middle3 = "";
                    if (a == "0") {
                        for (var ii = 0; ii < add.length - 1; ii++) {
                            middle1 = middle1 + add[ii];
                        }
                        
                        newDoc.getElementById(a).innerHTML = '<span class="add">' + middle1 + '</span>' + add[add.length - 1];
                    }
                    else {
                        for (var ii = 1; ii < add.length; ii++) {
                            middle3 = middle3 + add[ii];
                        }
                        newDoc.getElementById(a).innerHTML = add[0] + '<span class="add">' + middle3 + '</span>';
                    }
                }
				
                
            }
            
            for (var res = 0; res < tagCount; res++) {
                var res_a = "" + res;
                //newDoc.getElementById(res_a).outerHTML = newDoc.getElementById(res_a).innerHTML
				setOuterHtml(newDoc.getElementById(res_a), newDoc.getElementById(res_a).innerHTML);
            }
            
            var comparedContent = newDoc.body.innerHTML;
            removeElement(document.getElementById("compareHolder"));
            return comparedContent;
        },
        
        clean: function(parent, content){
            var filter = function(node){
                return node && (node.className == "version" || node.className == "add" || node.className == "delete");
            };
            
            var cleanHolder = document.createElement("div");
            cleanHolder.id = "cleanHolder";
            cleanHolder.style.display = "none";
            var cleanFrame = document.createElement("iframe");
            cleanHolder.appendChild(cleanFrame);
            parent.appendChild(cleanHolder);
            
            with (cleanFrame.contentWindow.document) {
                open();
                write("<html><head></head><body>" + content + "</body></html>");
                close();
                replaceAllWithChildren(body, filter);
            }
            
            var cleanContent = cleanFrame.contentWindow.document.body.innerHTML;
            removeElement(document.getElementById("cleanHolder"));
            return cleanContent;
        }
    };
    return Version;
})();
