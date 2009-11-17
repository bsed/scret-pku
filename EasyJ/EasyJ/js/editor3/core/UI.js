/**  Editor UI接口
*/
Ext.namespace("scret.Editor");
scret.Editor.UI = (function(){
	var defaultOptions = {
		west: {
			cls: "editor-west-panel",
			create: true,
			width: 100,
			min: 60,
			max: 200
		},
		east: {
			cls: "editor-east-panel",
			create: false,
			width: 80,
			min: 60,
			max: 150
		},
		north: {
			cls: "editor-north-panel",
			create: true,
			height: 18
		},
		south: {
			cls: "editor-south-panel",
			create: false,
			height: 40,
			min: 20,
			max: 100
		},
		center: {
			cls: "editor-center-panel"
		},
		cls: "editor-panel",
		width: 500,
		height: 300
	};
	
	var ui = function(options) {
		options = options || {};
		scret.util.copyObject(options, defaultOptions);
		
		options.center.id = options.center.id || '';
		// Create regions
		this.center = new Ext.Panel({
			region: "center",
			contentEl: options.center.id,
			cls: options.center.cls
		});
		this.west = options.west.create ? (new Ext.TabPanel({
			region: "west",
			cls: options.west.cls,
			width: options.west.width,
			collapsible: true,
			split: true,
			minSize: options.west.min,
			maxSize: options.west.max
		})) : null;
		this.east = options.east.create ? (new Ext.TabPanel({
			region: "east",
			cls: options.east.cls,
			width: options.east.width,
			collapsible: true,
			split: true,
			minSize: options.east.min,
			maxSize: options.east.max
		})) : null;
		this.south = options.south.create ? (new Ext.TabPanel({
			region: "south",
			cls: options.south.cls,
			height: options.south.height,
			collapsible: true,
			split: true,
			minSize: options.south.min,
			maxSize: options.south.max
		})) : null;
		this.north = options.north.create ? (new Ext.Toolbar({
			region: "north",
			cls: options.north.cls,
			height: options.north.height
		})) : null;
		var parts = [this.center];
		function pushPart(part) {
			if (part) parts.push(part);
		}
		pushPart(this.west);
		pushPart(this.east);
		pushPart(this.south);
		pushPart(this.north);
		this.parent = new Ext.Panel({
			renderTo: options.id,
			layout: "border",
			cls: options.cls,
			height: options.height,
			width: options.width,
			items: parts
		});
	};
	
	ui.prototype = {
		getPart: function(name) {
			return this[name];
		},
		
		//For toolbar (north)
		addIcon: function(options) {
			if (this.north) {
				Ext.QuickTips.init();
				var b =  this.north.addButton({
					icon: options.iconUrl,
					cls: 'x-btn-icon',
					tooltip: options.tip,
					handler: options.callback
				});
				if (options.cls) {
					b.addClass(options.cls);
				}
				return b;	
			}
			return null;
		},
		
		addSeparator: function() {
			if (this.north) {
				this.north.add('-');
			}
		},
		
		//For Tab Panel (east, west, south)
		addTab: function(part, extCmp, active) {
			if (this[part]) {
				this[part].add(extCmp);
				if (active) {
					this[part].setActiveTab(extCmp);
				}
			} 
			return null;
		}
	};
	return ui;
})();