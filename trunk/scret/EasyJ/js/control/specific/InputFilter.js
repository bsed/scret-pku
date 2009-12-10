/* 过滤器（输入框）控件
 */
 
Module.require("JsControl.ControlFactory");
Module.require("JsControl.AppControl");
Module.require("JS");


if (!Module.isDefined("JsControl.InputFilter")) {

Module.define("JsControl.InputFilter");

JsControl.InputFilter = (function() {
    
    var Filter = function(options) {
        Filter.superClass.constructor.call(this, options);
    };
    
    JS.extend(Filter, JsControl.AppControl);
    
    Filter.INPUT_CLASS = "input-filter";
    
    // -------- Overrides --------
    Filter.prototype.preprocessOptions = function() {
        // 预处理options
        Filter.superClass.preprocessOptions.call(this); 
        
        //TODO: 对Options的进一步检查 和/或 从Options中取得必要的数据
        this.size = this.options.size || "50";
        this.maxlength = this.options.maxlength || "50";          
    };
    
    Filter.prototype.buildEventMap = function() {
        // 创建事件表
        this.eventMap = new JsControl.EventMap(["change-filter-string"]);
    };
    
    Filter.prototype.generateHtml = function(options) {
        // 生成Html，如果已经在HTML文件中单独定义了控件外观，则不需覆盖此方法
        Filter.superClass.importHtml.call(this, '<input type="text" size="' + this.size + '" maxlength="' + this.maxlength + '" class="' + Filter.INPUT_CLASS + '" />');
    };
    
    Filter.prototype.bindData = function(data) {
        // 绑定数据到Html上，一般需要覆盖此方法，否则数据不会显示到页面上
        // NOTE: 覆盖时必须先调用父类的方法，在父类的方法中会尝试将type为string的data用JSON.parse转换为对象, 可以指定noJSON参数来避免此转换
        Filter.superClass.bindData.call(this, data, true);
        
        //TODO: bind this.data with this.element
        $(this.element).val(this.data);
    };
    
    Filter.prototype.initEventHandlers = function() {
        // 定义控件内部行为（例如，Console控件中包含一个按钮，当点击该按钮时清空控件的内容）
        var me = this;
        $(this.element).bind("change", function() {
            me.eventMap.notify("change-filter-string", $(me.element).val());
        }).bind("focus", function() {
            this.select();
        });
    };
    
    // ---------------------------
    
    return Filter;
})();

//TODO: 在此添加控件信息，其中type必填，中间三项没有可不填，最后一项如果不填则默认为false
JsControl.ControlFactory.extend({
    type: "InputFilter",
    canHaveChildren: false
});

}