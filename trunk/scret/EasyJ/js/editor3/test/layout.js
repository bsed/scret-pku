Ext.namespace("test");

test.Layout = (function() {
	var layout = {};
	var parent, content, north, west, east, south;
	
	layout.init = function(id) {
		content = new Ext.Panel({
		    region: "center",
		    contentEl: id + '-c'
		});
		north = new Ext.Toolbar({
			region: 'north',
			contentEl: id + '-n',
			height: 20
		});
		west = new Ext.Panel({
		    region: 'west',
		    contentEl: id + '-w',
		    width: 60,
		    minSize: 30,
		    maxSize: 100,
		    split: true,
		    collapsible: true,
            title: 'Side'
		});
		parent = new Ext.Panel({
			renderTo: id,
			layout: 'border',
			height: 200,
			width: 400,
			items: [content, north, west]
		});
	};
	
	return layout;
})();
