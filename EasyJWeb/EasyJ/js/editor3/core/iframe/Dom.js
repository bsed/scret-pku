/* Utilities for DOM
 */
var Dom = (function() {
	var dom = {
		// 以先根遍历序找到以root为根的树中，在node之前的符合filter条件的结点
		// node为空则不限制最后一个结点，filter为空则认为所有结点都符合条件
		findLastBefore: function(root, node, filter) {
			var result = null;
			var count = 0;

			function accept(n) {
				if (!filter || filter(n)) {
					return true;
				}
				return false;
			}

			function find(r) {
				if (accept(r)) {
					result = r;
					++count;
				}
				if (node && r == node) {
					return true; // End traverse
				}

				for (var c = r.firstChild; c != null; c = c.nextSibling) {
					if (find(c)) {
						return true;
					}
				}
				return false;
			}

			find(root);
			return {
				"result" : result,
				"count" : count
			};
		}
		
	};
	return dom;
})();