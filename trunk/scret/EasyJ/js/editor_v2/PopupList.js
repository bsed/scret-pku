/**
 * PopupList.js
 * 
 * 对所有弹出列表式的界面元素提供的统一实现
 * 
 * 对外接口：
 *   fillList()：设置列表的内容
 *   setPosition()：设置列表的x，y坐标
 *   show()：显示列表
 *   hide()：隐藏列表
 * 
 * ver 0.1 -- Icons are not supported.
 */
var PopupList = (function() {
	
	//默认设置
	var PopupListConfig = {
		cssClass: "popuplist",
		itemClass: "popuplist_item",  //未选中项的css
		itemOverClass: "popuplist_item_over",  //当前选中项的css
		defaultSelect: -1
	};
	
	/**
	 * 
	 * @param {String} id
	 * @param {Object} items：对象(data, callback, param)的数组
	 * @param {Object} options
	 */
	// items: array of {data, callback, param}
	var PopupList = function(id, items, options) {
		this.x = this.y = -1;
		function setDefaults(target, defaults) {
			for (var option in defaults) {
				if (!target.hasOwnProperty(option)) {
					target[option] = defaults[option];
				}
			}
		}
		
		this.options = options = options || {};
		setDefaults(this.options, PopupListConfig);
		
		var list = document.createElement("div");
		list.id = this.id = id;
		list.className = options.cssClass;
		list.PopupList = this;
		
		document.body.appendChild(list);
		if (items && items.length) {
			this.fillList(items);
		}
		this.hide();
	};
	
	PopupList.prototype = {
		
		createItem: function(item, index) {
			var d = document.createElement("div");
			d.index = index;
			d.callback = item.callback;
			d.param = item.param;
			
			var self = this;
			$(d).mouseover(function() {
				self.moveToItem(d.index);
			}).mouseout(function() {
				$(this).removeClass().addClass(self.options.itemClass);
			}).click(function() {
				self.selectItem();
			});
			
			if (typeof item.data == "string") {
				d.appendChild(document.createTextNode(item.data));
			} else {
				d.appendChild(item.data);
			}
			
			return d;
		},
		
		fillList: function(items) {
			this.length = items.length;
			var list = document.getElementById(this.id);
			$(list).empty();
			
			for (var i = 0; i < items.length; ++i) {
				list.appendChild(this.createItem(items[i], i));
			}
			this.currentItemIndex = this.options.defaultSelect;
			if (this.currentItemIndex >= 0) {
				this.moveToItem(this.currentItemIndex);
			}
		},
		
		moveToItem: function(index) {
			var list = document.getElementById(this.id);
			index = (index < 0) ? 0 : index;
			index = (index >= this.length) ? this.length - 1 : index;
		
            if (this.currentItemIndex >= 0 && this.currentItemIndex < this.length) {
                $(list.childNodes[this.currentItemIndex]).removeClass().addClass(this.options.itemClass);
            }
            var item = list.childNodes[index];
            $(item).removeClass().addClass(this.options.itemOverClass);
            this.currentItemIndex = item.index; // For keyboard support
		},
		
		selectItem: function() {
			var list = document.getElementById(this.id);
			var item = list.childNodes[this.currentItemIndex];
			this.hide();
			item.callback(item.param);
		},
		
		moveToPreviousItem: function() {
			this.moveToItem(this.currentItemIndex - 1);
		},
		
		moveToNextItem: function() {
			this.moveToItem(this.currentItemIndex + 1);
		},
		
		setPosition: function(left, top) {
			this.x = left;
			this.y = top;
		},
		
		show: function(left, top) {
			var list = document.getElementById(this.id);
            list.style.display = "block";
			list.style.left = (left ? left : this.x) + "px";
			list.style.top = (top ? top : this.y) + "px";
		},
		
		hide: function() {
			var list = document.getElementById(this.id);
			list.style.display = "none";
		}
	};
	
	return PopupList;
})();
