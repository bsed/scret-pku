/* A bridge between <iframe> inside/outside
 */
 
var Editor = (function(){
	var editor = {
		init: function(target, callback, options) {
			// Make <iframe> editable
			if (document.body.contentEditable != undefined && window.ActiveXObject) { // IE
                document.body.contentEditable = "true";
            } else {
                document.designMode = "on";
            }
            window.focus();
			target.Selection = Selection;
			callback();
		}
	};
	return editor;
})();

$(document).ready(function (){
	var frm = window.frameElement;
	Editor.init(frm.api, frm.onReady);
});
