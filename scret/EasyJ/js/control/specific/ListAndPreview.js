/* List-And-Preview 控件
 *   由两部分组成：一个列表（DIV），一个预览区域；默认CSS将二者左右排布
 *   当用户点击列表中某一项时，在预览区域显示该表项的内容
 *
 *   该控件不能包含其它子控件
 *
 *   使用此控件时必须指定一个showPreview回调函数，该函数定义了如何显示这些预览，例如：
 *     - Use Case Preview：用表格形式显示Use Case的Input，System Op，Output；不显示前置和后置条件；
 *     - Scenario Preview：仅列出Scenario的角色、数据和问题，不显示具体的描述和评论
 */

Module.require("JsControl.ControlFactory");
Module.require("JsControl.AppControl");
Module.require("JS");

if (!Module.isDefined("JsControl.ListAndPreview")) {

Module.define("JsControl.ListAndPreview");

JsControl.ListAndPreview = (function() {

    var LAP = function(options) {
        LAP.superClass.constructor.call(this, options);
    };
    
    JS.extend(LAP, JsControl.AppControl);
    
    LAP.LIST_HOLDER_CLASS = "list-area";
    LAP.PREVIEW_HOLDER_CLASS = "preview-area";
    LAP.LIST_ITEM_CLASS = "list-item";
    
    LAP.PRIMARY_KEY_NAME = "__Primary_Key";
    
    // -------- Overrides --------
    LAP.prototype.preprocessOptions = function() {
        LAP.superClass.preprocessOptions.call(this);
        
        this.preview = this.options.callback.preview;
    };
    
    LAP.prototype.buildEventMap = function() {
        this.eventMap = new JsControl.EventMap(["bind-data", "select-item", "filter-data"]);
    };
    
    LAP.prototype.bindData = function(data, noJSON) {
        // Data format: 
        //   { "primary-key1": { "key1.1" : "val1.1", "key1.2" : "val1.2", ...},
        //     "primary-key2": { "key2.1" : "val2.1", "key2.2" : "val2.2", ...},
        //     ... }
        
        LAP.superClass.bindData.call(this, data, noJSON);
        
        if (!this.data || typeof this.data != "object") {
            return;
        }
        var $target = $("." + LAP.LIST_HOLDER_CLASS, this.element);
        if ($target.length <= 0) {
            return;
        }
        var $preview = $("." + LAP.PREVIEW_HOLDER_CLASS, this.element);
        $target.empty().append("<ul></ul>");
        var $list = $("ul", $target);
        var me = this;
        for (var name in this.data) {
            (this.data[name])[LAP.PRIMARY_KEY_NAME] = name;
            var $item = $("<li>" + name + "</li>");
            $item.click(function() {
                me.selectItem(this);
            });
            $list.append($item);
        }
        
        this.eventMap.notify("bind-data", this.data);
    };
    
    LAP.prototype.initEventHandlers = function() {
        // no extra event handlers
    };
    // -----------------------------
    
    LAP.prototype.filterBy = function(filterString) {
        //TODO: 没有必要发布此事件
        var fs = filterString.toLowerCase();
        var $list = $("." + LAP.LIST_HOLDER_CLASS, this.element);
        var filtered = [];
        $("li", $list).each(function() {
            var $this = $(this);
            var val = $this.text();
            if (val.toLowerCase().indexOf(fs) >= 0) {
                $this.show();
            } else {
                $this.hide();
                filtered.push(val);
            }
        });
        
        this.eventMap.notify("filter-data", filtered);
    };
    
    LAP.prototype.selectItem = function(item) {
        var $preview = $("." + LAP.PREVIEW_HOLDER_CLASS, this.element);
        var itemData = this.data[$(item).text()];
        if ($preview && this.preview) {
            this.preview($preview.get(0), itemData);
        }
        
        this.eventMap.notify("select-item", itemData);
    };
    
    return LAP;
})();

JsControl.ControlFactory.extend({
    type: "ListAndPreview",
    html: "ListAndPreview.html",
    css: "ListAndPreview.css",
    canHaveChildren: false
});
}