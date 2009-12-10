if (!Module.isDefined("JsControl.ObserverMap")) {

Module.define("JsControl.ObserverMap");

JsControl.ObserverMap = (function() {
    
    // Class Publisher-Subscriber-Relation
    function PubSubRelation(pub) {
        this.id = pub.id;
        this.event = pub.event;
        this.data = pub.data;
        this.subscribers = [];
    }
    
    PubSubRelation.prototype = {
        checkSubscription: function(subCandidate) {
            var me = this;
            function check(attrName) {
                return !subCandidate[attrName] || me[attrName] == subCandidate[attrName]; 
            }
            if (check("id") && check("event") && check("data")) {
                this.subscribers.push(subCandidate);
                return true;
            }
            return false;
        },
        
        notifyAll: function(data) {
            var d = this.data || data;
            for (var i = 0; i < this.subscribers.length; ++i) {
                var s = this.subscribers[i];
                if (s.update && typeof s.update == "function" && s.subscriber && typeof s.subscriber == "object") {
                    s.update.call(s.subscriber, this.id, this.event, d);
                }
            }
        }
    };
    
    var relations = [];
    var subscriptions = [];
    
    var ObMap = {
        
        addPublication: function(pub) {
            if (!pub.id || !pub.event) {
                throw new Error("Illegal Publication: publisher-id and event-name required."); 
            }
            var relation = new PubSubRelation(pub);
            relations.push(relation);
            for (var i = 0; i < subscriptions.length; ++i) {
                relation.checkSubscription(subscriptions[i]);
            }
            return relation;
        },
        
        addSubscription: function(sub) {
            for (var i = 0; i < relations.length; ++i) {
                relations[i].checkSubscription(sub);
            }
            subscriptions.push(sub);
        }
    };
    return ObMap;
})();
}