//TODO: 仅为demo版，需要修改

Module.require("JsControl.ControlFactory");
Module.require("JsControl.AppControl");
Module.require("JS");

if (!Module.isDefined("JsControl.Console")) {

Module.define("JsControl.Console");

JsControl.Console = (function() {

    var Console = function(options) {
        Console.superClass.constructor.call(this, options);
    };
    
    JS.extend(Console, JsControl.AppControl);
    
    Console.BUTTON_CLEAR_CLASS = "console-clear";
    Console.MSG_CLASS = "console-msg";
    
    // -------- Overrides --------
    Console.prototype.buildEventMap = function() {
        // do nothing
    };
    
    Console.prototype.bindData = function(data) {
        Console.superClass.bindData.call(this, data, true); // always no JSON.parse
        
        this.appendMessage(this.data);
    };
    
    Console.prototype.initEventHandlers = function() {
        var me = this;
        $("." + Console.BUTTON_CLEAR_CLASS, this.element).click(function() {
            $("." + Console.MSG_CLASS, me.element).empty();
        });
    };
    // ---------------------------
    Console.prototype.appendMessage = function(msg) {
        var $msgArea = $("." + Console.MSG_CLASS, this.element);
        $(".newMsg", $msgArea).removeClass();
        if (typeof msg == "string") {
            $msgArea.append("<div class='newMsg'>" + msg + "</div>");
        }
    };
    
    return Console;
})();

JsControl.ControlFactory.extend({
    type: "Console",
    html: "Console.html",
    css: "Console.css",
    canHaveChildren: false
});
}