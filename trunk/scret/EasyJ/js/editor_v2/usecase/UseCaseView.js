/* version 1.2
 */
var UseCaseView = (function(){

    //----------------
    //    Util
    //----------------

	function getTd(th) {
		return document.getElementById("td_" + th.id.substring("th_".length));
	}

	function addClass(th, name) {
		$(th).addClass(name);
		$(getTd(th)).addClass(name);
	}

	function removeClass(th, name) {
		$(th).removeClass(name);
		$(getTd(th)).removeClass(name);
	}

    // node is before target?
    function isBefore(node, target){
        if (node == target) {
            return false;
        }
        for (var n = node; n != null; n = n.nextSibling) {
            if (n == target) {
                return true;
            }
        }
        return false;
    }

    function isAfter(node, target){
        return isBefore(target, node);
    }

    function setClassBefore(node, name){
        for (var n = node.previousSibling; n != null; n = n.previousSibling) {
            if (n.nodeType == 1) {
                $(n).removeClass("valid_range invalid_range");
				if (isHeader(n)) {
					$(getTd(n)).removeClass("valid_range invalid_range");
				}
                if (name) {
                    $(n).addClass(name);
					if (isHeader(n)) {
						$(getTd(n)).addClass(name);
					}
                }
            }
        }
    }

    function setClassAfter(node, name){
        for (var n = node.nextSibling; n != null; n = n.nextSibling) {
            if (n.nodeType == 1) {
                $(n).removeClass("valid_range invalid_range");
				if (isHeader(n)) {
					$(getTd(n)).removeClass("valid_range invalid_range");
				}
                if (name) {
                    $(n).addClass(name);
					if (isHeader(n)) {
						$(getTd(n)).addClass(name);
					}
                }
            }
        }
    }

    function setClassBetween(begin, end, name){
        for (var n = begin; n != null; n = n.nextSibling) {
            if (n.nodeType == 1) {
                $(n).removeClass("valid_range invalid_range");
				if (isHeader(n)) {
					$(getTd(n)).removeClass("valid_range invalid_range");
				}
                if (name) {
                    $(n).addClass(name);
					if (isHeader(n)) {
						$(getTd(n)).addClass(name);
					}
                }
            }
            if (n == end) {
                break;
            }
        }
    }

    //-----------------
    //  class Assoc
    //-----------------
    function Assoc(rows, id, name, view){
        var begin = rows[0];
        var end = rows[rows.length - 1];
        this.begin = begin, this.end = end;
        this.name = name;
		this.view = view;

        $(begin).addClass("use_case_begin");
		$(getTd(begin)).addClass("use_case_begin");
        $(end).addClass("use_case_end");
		$(getTd(end)).addClass("use_case_end");
        begin.assoc = this;
		begin.usecase = id;

        view.createAssocBegin(begin, name);

        if (begin != end) {
            end.assoc = this;
            view.createAssocEnd(end);
            for (var r = begin.nextSibling; r != end; r = r.nextSibling) {
                if (isHeader(r)) {
                    $(r).empty();
					addSpaces(r);
                }
            }
        }
    }

    //------------------
    //    Main Class
    //------------------
    function UseCaseView(border, editArea){
        this.container = border;
        this.EditArea = editArea;
		this.useCaseList = [];
		this.Range = {
            begin: null,
            end: null
        };
    }

    UseCaseView.prototype = {

        importContent: function(container, firstLineUCID){
			clearElement(this.container);

			var lineNo = 1;

			var leftCol = document.createElement("div");
			leftCol.className = "use_case_left";
			var rightCol = document.createElement("div");
			rightCol.className = "use_case_right";

			this.container.appendChild(rightCol);
			this.container.appendChild(leftCol);


			var th = document.createElement("div");
			th.id = "th_" + lineNo;
			th.className = "table_header";
			var td = document.createElement("div");
			td.id = "td_" + lineNo;
			td.className = "table_datagrid";
			leftCol.appendChild(th);
			rightCol.appendChild(td);

			var dumb = document.createElement("span");
			addSpaces(dumb);
			dumb.className = "empty_line";
			td.appendChild(dumb);
			var emptyLine = true;

			var lastTd = td;
			var lastUCID = firstLineUCID;
			var lastUcLines = {};
			var lastPlainLines = {};
			if (firstLineUCID && firstLineUCID != "") {
				th.usecase = firstLineUCID;
				lastUcLines[firstLineUCID] = [];
				lastUcLines[firstLineUCID].push(th);
				inUcRange = true;
			} else {
				this.createPlainTextHeader(th);
			}

			++lineNo;
			var self = this;

			function checkEmptyLine() {
				if (emptyLine) {
					emptyLine = false;
					for (var c = lastTd.firstChild; c != null; c = c.nextSibling) {
						if (c.nodeType == 1 && c.className == "empty_line") {
							removeElement(c);
							break;
						}
					}
				}
			}

			function fillTable(root) {
				if (!root) {
					return;
				}
				if (isTerm(root) || isKeyword(root)) {
					checkEmptyLine();
				    // root is a keyword or term
					var n = document.createElement("span");
					n.className = root.className;
					n.appendChild(document.createTextNode(innerText(root)));
					lastTd.appendChild(n);
					return;
				}
				if (root.nodeType == 3 && root.nodeValue != "") { //Text Node
				    checkEmptyLine();
					lastTd.appendChild(document.createTextNode(root.nodeValue));
				}
				if (root.nodeType == 1 && root.nodeName.toLowerCase() == "br") {
					// create a new row in table
					var curTh = document.createElement("div");
					curTh.id = "th_" + lineNo;
					curTh.className = "table_header";
					var curTd = document.createElement("div");
					curTd.id = "td_" + lineNo;
					curTd.className = "table_datagrid";

				    leftCol.appendChild(curTh);
                    rightCol.appendChild(curTd);

                    var dumb = document.createElement("span");
                    addSpaces(dumb);
                    dumb.className = "empty_line";
                    curTd.appendChild(dumb);

					emptyLine = true;
					lastTd = curTd;

					var ucClass = getClassMatch(root, /uc_/i);
					if (ucClass != "") {
						if (lastUCID == ucClass) {
							if (lastPlainLines[lastUCID] && lastPlainLines[lastUCID].length > 0) {
								lastUcLines[lastUCID] = lastUcLines[lastUCID].concat(lastPlainLines[lastUCID]);
								lastPlainLines[lastUCID] = [];
							}
						} else {
							if (lastUCID) {
								// a new use case
								if (lastUcLines[lastUCID] && lastUcLines[lastUCID].length > 0) {
									var id = lastUcLines[lastUCID][0].usecase;
									var name = self.EditArea.useCaseMap[id].name;
									self.useCaseList.push(new Assoc(lastUcLines[lastUCID], id, name, self));
								}
								delete lastUcLines[lastUCID];

								if (lastPlainLines[lastUCID]) {
									for (var i = 0; i < lastPlainLines[lastUCID].length; ++i) {
										self.createPlainTextHeader(lastPlainLines[lastUCID][i]);
									}
								}
								delete lastPlainLines[lastUCID];
							}
							lastUCID = ucClass;
							curTh.usecase = ucClass;
							lastUcLines[lastUCID] = [];
						}
						lastUcLines[lastUCID].push(curTh);
					} else {  // a plain text line
					    if (lastUCID) {
							if (!lastPlainLines[lastUCID]) {
								lastPlainLines[lastUCID] = [];
							}
							lastPlainLines[lastUCID].push(curTh);
						} else {
							self.createPlainTextHeader(curTh);
						}
					}
                    ++lineNo;
				}
				for (var c = root.firstChild; c != null; c = c.nextSibling) {
					fillTable(c);
				}
			}

			fillTable(container);

			for (var n in lastUcLines) {
				if (lastUcLines[n].length > 0) {
					var id = lastUcLines[n][0].usecase;
					var name = self.EditArea.useCaseMap[id].name;
					self.useCaseList.push(new Assoc(lastUcLines[n], id, name, self));
				}
			}

			for (var n in lastPlainLines) {
				for (var i = 0; i < lastPlainLines[n].length; ++i) {
					self.createPlainTextHeader(lastPlainLines[n][i]);
				}
			}

			function isEmptyLine(td) {
				if (innerText(td) == "") {
					return true;
				}
				for (var c = td.firstChild; c != null; c = c.nextSibling) {
					if (c.nodeType == 1 && c.className == "empty_line") {
						return true;
					}
				}
				return false;
			}

			// 删掉末尾多余的空行
			for (var r = leftCol.lastChild; r != null;) {
                if (isHeader(r)) {
                    if ($(r).hasClass("use_case_begin") || $(r).hasClass("use_case_end")) {
                        break;
                    } else {
                        var td = getTd(r);
                        if (td) {
                            if (isEmptyLine(td)) {
                                var temp = r.previousSibling;
                                removeElement(r);
                                removeElement(td);
                                r = temp;
                            } else {
                                break;
                            }
                        } else {
                            r = r.previousSibling;
                        }
                    }
                } else {
                    r = r.previousSibling;
                }
			}
        },

        createAssocBegin: function(th, name){
			var self = this;
            $(th).empty();

            var del = document.createElement("img");
            del.src = "./image/assoc_delete.gif";
            del.title = "取消关联";
            del.className = "button";
            $(del).bind("click", function(event) {
				self.deleteAssoc(event);
			});

            var up = document.createElement("img");
            up.src = "./image/assoc_up.gif";
            up.className = "button";
			$(up).bind("click", function(event) {
				self.expandBegin(event);
			});

            var down = document.createElement("img");
            down.src = "./image/assoc_down.gif";
            down.className = "button";
			$(down).bind("click", function(event) {
				self.collapseBegin(event);
			});

            th.appendChild(up);
            th.appendChild(down);
			th.appendChild(del);
			var s = document.createElement("span");
            s.className = "assoc_name";
            s.appendChild(document.createTextNode(name));
            th.appendChild(s);
        },

        createAssocEnd: function(th){
			var self = this;
            $(th).empty();

            var up = document.createElement("img");
            up.src = "./image/assoc_up.gif";
            up.className = "button";
			$(up).bind("click", function(event) {
				self.collapseEnd(event);
			});

            var down = document.createElement("img");
            down.src = "./image/assoc_down.gif";
            down.className = "button";
            $(down).bind("click", function(event) {
				self.expandEnd(event);
			});

            th.appendChild(up);
            th.appendChild(down);
			addSpaces(th);
        },

        createPlainTextHeader: function(th){
            $(th).empty();

            var btn = document.createElement("img");
            btn.src = "./image/assoc_add.gif";
            btn.title = "关联用况";
            btn.className = "button";
			var self = this;
            $(btn).bind("click", function(event) {
				self.beginCreateAssoc(event);
			});
            th.appendChild(btn);
			addSpaces(th);
        },

        //--------------------
        //  Event Handlers
        //--------------------
        beginCreateAssoc: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            event.stop();
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            $(".button").hide();
            this.Range.begin = th;
            this.Range.end = th;
            $(th).addClass("valid_range");
			$(getTd(th)).addClass("valid_range");
            var self = this;
            $("div.table_header").unbind().mouseover(function(event){
                self.checkRange(event);
            }).click(function(event){
                self.confirmRange(event);
            });

            $(document).keyup(function(event){
                event = normalizeEvent(event);
                if (event.key == 27) { //ESC
                    self.endCreateAssoc();
                }
            });
        },

        endCreateAssoc: function(){
            $("div.table_header").unbind().removeClass("valid_range invalid_range");
			$("div.table_datagrid").removeClass("valid_range invalid_range");
            $(".button").show();
        },

        checkRange: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            if (th == this.Range.begin) {
                this.Range.end = this.Range.begin;
                setClassBefore(th);
                setClassAfter(th);
            } else if (isBefore(th, this.Range.begin)) {
                this.Range.end = this.Range.begin;
                setClassBefore(th);
                setClassBetween(th, this.Range.begin, "invalid_range");
                $(this.Range.begin).removeClass("invalid_range").addClass("valid_range");
				$(getTd(this.Range.begin)).removeClass("invalid_range").addClass("valid_range");
                setClassAfter(this.Range.begin);
            } else {
                // search for another use_case_begin between this.Range.begin and th
                var end = null, lastTr = null;
                for (var r = this.Range.begin.nextSibling; r != null; r = r.nextSibling) {
                    if (isBegin(r)) {
                        end = r;
                        break;
                    }
                    if (isHeader(r)) {
                        lastTr = r;
                    }
                    if (r == th) {
                        break;
                    }
                }

                if (end) {
                    this.Range.end = lastTr ? lastTr : this.Range.begin;
                } else {
                    this.Range.end = th;
                }

                setClassBefore(this.Range.begin);
                setClassBetween(this.Range.begin, this.Range.end, "valid_range");
                if (end) {
                    setClassBetween(end, th, "invalid_range");
                }
                setClassAfter(th);
            }
        },

        confirmRange: function(event){
            event = normalizeEvent(event);
            event.stop(); // avoid clicking button in <tr>

			var id = "uc_" + this.EditArea.useCaseMap.index;
            var name = prompt("请输入用况名", "");
			if (!name) {
				return;
			}
            var lines = [];
            var r = this.Range.begin;
            while (r) {
                if (isHeader(r)) {
                    lines.push(r);
                }
                if (r == this.Range.end) {
                    break;
                }
                r = r.nextSibling;
            }
            this.useCaseList.push(new Assoc(lines, id, name, this));
			this.EditArea.useCaseMap[id] = {
				name: name
			};
			++this.EditArea.useCaseMap.index;
            this.endCreateAssoc();
        },

        expandBegin: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            var prevRow = null;
            for (var r = th.previousSibling; r != null; r = r.previousSibling) {
                if (isHeader(r)) {
                    prevRow = r;
                    break;
                }
            }

            if (prevRow && !isEnd(prevRow)) {
                if (th.assoc.begin == th.assoc.end) {
                    th.assoc.end = th;
                    this.createAssocEnd(th);
                } else {
                    $(th).empty();
					addSpaces(th);
                }
				prevRow.usecase = th.usecase;
				th.usecase = null;
                th.assoc.begin = prevRow;
                this.createAssocBegin(prevRow, th.assoc.name);
                $(th).removeClass("use_case_begin");
				$(getTd(th)).removeClass("use_case_begin");
                $(prevRow).addClass("use_case_begin");
				$(getTd(prevRow)).addClass("use_case_begin");
                prevRow.assoc = th.assoc;
            }
        },

        expandEnd: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            var nextRow = null;
            for (var r = th.nextSibling; r != null; r = r.nextSibling) {
                if (isHeader(r)) {
                    nextRow = r;
                    break;
                }
            }

            if (nextRow && !isBegin(nextRow)) {
                th.assoc.end = nextRow;
                $(th).empty();
				addSpaces(th);
                this.createAssocEnd(nextRow);
                $(th).removeClass("use_case_end");
				$(getTd(th)).removeClass("use_case_end");
                $(nextRow).addClass("use_case_end");
				$(getTd(nextRow)).addClass("use_case_end");
                nextRow.assoc = th.assoc;
            }
        },

        collapseBegin: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }
            var nextRow = null;
            for (var r = th.nextSibling; r != null; r = r.nextSibling) {
                if (isHeader(r)) {
                    nextRow = r;
                    break;
                }
            }
            if (th.assoc.begin != th.assoc.end) {
                if (nextRow) {
                    $(th).empty();
					addSpaces(th);
                    $(th).removeClass("use_case_begin");
					$(getTd(th)).removeClass("use_case_begin");
                    this.createAssocBegin(nextRow, th.assoc.name);
                    $(nextRow).addClass("use_case_begin");
					$(getTd(nextRow)).addClass("use_case_begin");
                    nextRow.assoc = th.assoc;
                    nextRow.assoc.begin = nextRow;
					nextRow.usecase = th.usecase;
					th.usecase = null;
                    this.createPlainTextHeader(th);
                }
            } else {
                if (nextRow && !isBegin(nextRow)) {
                    th.assoc.end = nextRow;
                    this.createAssocEnd(nextRow);
					removeClass(th, "use_case_end");
					addClass(nextRow, "use_case_end");
                    nextRow.assoc = th.assoc;
                }
            }
        },

        collapseEnd: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            if (th.assoc.begin != th.assoc.end) {
                var prevRow = null;
                for (var r = th.previousSibling; r != null; r = r.previousSibling) {
                    if (isHeader(r)) {
                        prevRow = r;
                        break;
                    }
                }

                if (prevRow) {
                    $(th).empty();
					addSpaces(th);
                    removeClass(th, "use_case_end");
                    if (prevRow != th.assoc.begin) {
                        this.createAssocEnd(prevRow);
                    }
                    addClass(prevRow, "use_case_end");
                    prevRow.assoc = th.assoc;
                    prevRow.assoc.end = prevRow;
                    this.createPlainTextHeader(th);
                }
            }
        },

        deleteAssoc: function(event){
            event = normalizeEvent(event);
            var th = event.source;
            while (th && !isHeader(th)) {
                th = th.parentNode;
            }

            var begin = th.assoc.begin;
            var end = th.assoc.end;
            delete this.EditArea.useCaseMap[begin.usecase];
			delete begin.usecase;
            removeClass(begin, "use_case_begin");
            removeClass(end, "use_case_end");
            $(begin).empty();
			addSpaces(begin);
            $(end).empty();
			addSpaces(end);
            for (var r = begin; r != null; r = r.nextSibling) {
                if (isHeader(r)) {
                    this.createPlainTextHeader(r);
                }
                if (r == end) {
                    break;
                }
            }
        }
    };

    return UseCaseView;
})();
