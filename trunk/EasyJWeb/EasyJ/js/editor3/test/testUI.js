Ext.namespace('scret.Editor.test');
scret.Editor.test.UI = (function() {
	var uiOptions = {
		//east: { create: true },
		south: { create: true },
		//center: { id: 'edt-c' },
		id: 'edt'
	};
	var t = {
		run: function() {
			var e = Ext.get(uiOptions.id);
			if (!e) {
				e = Ext.DomHelper.append(document.body, {
					tag: 'div',
					id: uiOptions.id
				}, true);
			}
			var ui = new scret.Editor.UI(uiOptions);
			ui.addIcon({
				iconUrl: "../image/Doc-Ok.png",
				tip: "Save",
				callback: function() {
					Ext.Msg.alert("Message", "Saved OK.");
				}
			});
			ui.addSeparator();
			ui.addIcon({
				iconUrl: "../image/Add.png",
				tip: "Commit",
				callback: function() {
					Ext.Msg.alert("Message", "Committed OK.");
				}
			});
		}
	};
	return t;
})();

