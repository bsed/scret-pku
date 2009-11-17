Ext.namespace("scret.Editor.test");
scret.Editor.test.Iframe = (function() {
	
	var innerScripts = [
	    "jquery-1.2.3.js",
	    "Dom.js",
	    "Selection.js",
	    "Editor.js"
	];
	
	var tester = {
		run: function() {
			// Create an iframe in parent element
			var parent = Ext.get('edt-parent');
			if (!parent) {
				parent = Ext.DomHelper.append(document.body, {
					tag: 'div',
					id: 'edt-parent'
				}, true);
			}
			var frm = Ext.DomHelper.append(parent, {
				tag: 'iframe',
				style: 'width:500px;height:300px;'
			});
			frm.frameBorder = "no";
        	frm.border = "0";
        	frm.marginWidth = "0";
        	frm.marginHeight = "0";
        	frm.api = {};
        	frm.onReady = function() {
        		//Ext.Msg.alert("Info", frm.api.Selection.set ? "has set" : "not set");
	       		frm.api.Selection.set({base: null, offset: 6});
        	};
        	var win = frm.contentWindow;
        	var doc = win.document;
        	var html = ['<html><head>'];
        	for (var i = 0; i < innerScripts.length; ++i) {
        		html.push('<script type="text/javascript" src="../js/editor3/core/iframe/' + innerScripts[i] + '"></scr' + 'ipt>');
        	}
        	html.push('</head><body>123456789abcdefg</body></html>');
        	doc.open();
        	doc.write(html.join(""));
        	doc.close();
		}
	};
	return tester;
})();