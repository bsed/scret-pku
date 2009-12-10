var HtmlParser = (function() {

	//--------------------------------
	//         Contants
	//--------------------------------
	// 1) Keyword Map
	var KeywordMap = {
		keyword_if: "__#IF#__",
		keyword_else: "__#ELSE#__"
	};

	// 2) Seperators
	var PRE_ACTION = "__@@";
	var POST_ACTION = "@@__";
	var LINE_BREAK = "__#Line_Break#__";

	//----------------------------------
	//        Class   HtmlParser
	//----------------------------------
	var HtmlParser = {};

	HtmlParser.getGraphSourceString = function(editArea) {
		if (!editArea.actionView) {
			return "";
		}

		//1) Call ActionView API to link action info with user-input content
		editArea.actionView.importContent(editArea.editor.container, editArea.editor.firstLineActionID);

		//2) Parse the "action-linked" content (in ActionView.container)
		var container = editArea.actionView.container;

		function parseLine(line) {
			// Line structure: (At most 5 parts)
			//    <DIV class="action_line">
			//      pre-action nodes...
			//      <SPAN class="action_op">Action Operations(button)</SPAN>
			//      <SPAN class="action_name">Action Name</SPAN>
			//      <SPAN class="action_content">Action Content</SPAN>
			//      post-action nodes...
			//    </DIV>
			// Parse algorithm:
			//  (1) Replace all Keyword-SPAN with Keyword-Text
			//  (2) Skip all action_op SPANs
			//  (3) Replace all Term-SPAN with their innerTexts
			//  Output all results in a string
			var result = "";
			var hasActionName = true;

			function parseNode(node) {
				if (!node) {
					return;
				}
				if (isTerm(node)) {
					result += innerText(node);
					return;
				}
				if (isKeyword(node)) {
					result += KeywordMap[node.className];
					return;
				}
				if (isOp(node)) {
					return;
				}
				if (isName(node)) {
					var t = innerText(node);
					if (t != "") {
						result += t;
						hasActionName = true;
					} else {
						hasActionName = false;
					}
					return;
				}
				if (isContent(node)) {
					var text = innerText(node);
					if (!hasActionName) {
						result += (text.length > 3 ? (text.substring(0, 3) + "..") : text);
					}
					result += (PRE_ACTION + text + POST_ACTION);
					return;
				}
				if (node.nodeType == 3 && node.nodeValue != "") {
					result += node.nodeValue;
					return;
				}
				for (var c = node.firstChild; c != null; c = c.nextSibling) {
					parseNode(c);
				}
			}

			parseNode(line);

			return result;
		}// function parseLine()

		var graphString = "";
		for (var c = container.firstChild; c != null; c = c.nextSibling) {
			if (c.nodeType == 1 && $(c).hasClass("action_line")) {
				graphString += parseLine(c);
				graphString += LINE_BREAK;
			}
		}
		return '<span id="__Graph_String__">' + graphString + '</span>';
	};

	HtmlParser.getMapString = function(mapname, map) {
		if (!map) {
			return "";
		}
		var mapString = '<span id="__Map_' + mapname + '__">';
		mapString += JSON.stringify(map.dump());
		mapString += '</span>';
		return mapString;
	};

    // editArea is optional
    HtmlParser.parseContent = function(html, editArea) {
		var holder = document.createElement("div");
		holder.innerHTML = html;

var firstLineUCID = "", firstLineActionID = "";
		function stripeMapString(node, mapname) {
			if (editArea) {
				editArea[mapname] = JSON.parse(node.innerHTML);
			}
			removeElement(node);
		}

        for (var c = holder.firstChild; c != null;) {
			var next = c.nextSibling;
            if (c.nodeType == 1) {
                if (c.id == "__Graph_String__") {
                    removeElement(c);
                } else if (c.id == "__Map_usecase__") {
					stripeMapString(c, "useCaseInfo");
				} else if (c.id == "__Map_action__") {
					stripeMapString(c, "actionMap");
				} else if (c.id == "__First_ID_usecase__") {

                                      firstLineUCID = c.innerHTML;

                                  removeElement(c);
				} else if (c.id == "__First_ID_action__") {

                                      firstLineActionID = c.innerHTML;

                                  removeElement(c);
				} else {
                                  break;
				}

            } else {
              break;
            }
			c = next;
        }
		return holder.innerHTML;
	};
	return HtmlParser;
})();
