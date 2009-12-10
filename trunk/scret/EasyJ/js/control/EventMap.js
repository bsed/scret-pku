if (!Module.isDefined("JsControl.EventMap")) {

Module.define("JsControl.EventMap");

JsControl.EventMap = (function() {
    
    function Map(events) {
        this.map = {};
        if (typeof events == "string") {
            this.map[events] = {};
        } else if (typeof events == "object" && events.length) {
            for (var i = 0; i < events.length; ++i) {
                this.map[ events[i] ] = {};
            }
        }
    }
    
    Map.prototype = {
        addEvent: function(name, relation) {
            this.map[name] = {"relation": relation};
        },
        
        getData: function(eventName) {
            var e = this.map[eventName];
            if (e && e.relation) {
                return e.relation.data;
            }
            return null;
        },
        
        notify: function(eventName, data) {
            var e = this.map[eventName];
            if (e && e.relation) {
                e.relation.notifyAll(data);
            }
        }
    };
    return Map;
})();

}