/* AppControl: 所有控件的父类
 */
Module.require("JsControl.ControlFactory");
Module.require("JsControl.ObserverMap");
Module.require("JsControl.EventMap");

if (!Module.isDefined("JsControl.AppControl")) {
 
Module.define("JsControl.AppControl");

JsControl.AppControl = (function() {
    
    var AppCtrl = function(options) {
        this.options = options;

        //TODO: 下列操作在具体控件类的构造函数中实现
        // 1) 对options的特殊处理（如：取得某个特定的回调函数）
        // 2) Build EventMap
        this.preprocessOptions();
        this.buildEventMap();
                
        if (options.children) {
            this.subControls = [];
            for (var i = 0; i < options.children.length; ++i) {
                try {
                    var c = JsControl.ControlFactory.create(options[i]);
                    this.subControls.push(c);
                } catch (e) {
                   
                }
            }
        }
        
        var me = this;
        
        // Create HTML 
        this.element = null;
        if (options.style == JsControl.STYLE.INSERT) {
	        this.element = options.target;
	    }
        this.generateHtml(options);
        
        // Load extra js and css files
        JsControl.ControlFactory.loadDependency(options.url.js);
        JsControl.ControlFactory.loadDependency(options.url.css);

        // Request data from server
        this.data = null;
        
        //TODO: 下列操作在具体控件类的构造函数中实现
        // 1) Bind data with HTML
        if (options.dataBinding) {
            this.bindData(options.dataBinding);
        } else {
            this.requestData(options.dataRequest);
            
        } 
        
        //建立发布者/订阅者关系
        if (options.publish) {
            var id = options.publish.id;
            if (!id) {
                throw new Error("Missing publisher-id.");
            }
            var publications = options.publish.publications;
            for (var i = 0; i < publications.length; ++i) {
                var pub = publications[i];
                if (!pub.event) {
                    continue;
                }
                var r = JsControl.ObserverMap.addPublication({
                    "id": id, "event": pub.event, "data": pub.data
                });
                if (this.eventMap) {
                    this.eventMap.addEvent(pub.event, r);
                }
            }
        }
        
        if (options.subscribe) {
            for (var i = 0; i < options.subscribe.length; ++i) {
                var sub = options.subscribe[i];
                sub.subscriber = this;
                JsControl.ObserverMap.addSubscription(sub);
            }
        }
        
        this.initEventHandlers();
        //TODO: 下列操作在具体控件类的构造函数中实现
        // 1) Assemble sub-controls 
        if (options.children) {
            this.assembleSubControls();
        }
    };
    
    AppCtrl.prototype = {
        // -----  Template Methods  ------
        
        preprocessOptions: function() {
            // 下面包含一系列的check，保证options的一致性和完整性
            if (!this.options.style) {
                this.options.style = (this.options.target ? JsControl.STYLE.REPLACE : JsControl.STYLE.NEW);
            } else if (!this.options.target && (this.options.style == JsControl.STYLE.REPLACE || this.options.style == JsControl.STYLE.INSERT)) { 
                throw new Error("Control init failed.\n\tReason: target not found.");
            }
            
            if (!this.options.callback) {
                this.options.callback = {};
            }
            if (this.options.target) { // 如果控件是用HTML定义的，则忽略place回调函数
                this.options.callback.place = null;
            }
            
            if (!JsControl.ControlFactory.controlInfo[this.options.type].canHaveChildren) {
                this.options.children = null;
            }	
        },
        
        buildEventMap: function() {
            throw new Error("Method 'buildEventMap' is abstract");
        },
        
        importHtml: function(html) {
            if (this.options.style == JsControl.STYLE.INSERT) {
                $(this.element).prepend(html);
            } else {
                this.element = $(html).get(0);
                if (this.options.style == JsControl.STYLE.REPLACE) {
                	$(this.element).addClass(this.options.target.className).attr("id", this.options.target.id);
                	$(this.options.target).replaceWith(this.element);
                }
            }
            if (this.options.id) {
                this.element.id = this.options.id;
            }
            if (this.options.className) {
                $(this.element).addClass(this.options.className);
            }
        },
        
        generateHtml: function(options) {
            var me = this;
	        if (options.url.html && options.style != JsControl.STYLE.ABSTRACT) {
		        $.ajax({
		            async: false,  // 这里必须要同步请求数据
		            url: options.url.html,
		            dataType: "html",
		            beforeSend: (options.callback.htmlStart ? options.callback.htmlStart : null),
		            success: function(html) {
		                me.importHtml(html);
		                if (options.callback.htmlComplete) {
		                    options.callback.htmlComplete(html);
		                }  
		            }	            
		        });
	        }
        },
        
        requestData: function(req) {
            var me = this;
            if (req && typeof req == "object" && req.url) {
	            $.ajax({
	                async: false,  // MUST be false
	                url: req.url,
	                type: "POST",
	                data: (req.postData ? req.postData : null),
	                beforeSend: (me.options.callback.dataStart ? me.options.callback.dataStart : null),
	                success: function(data) {
	                    me.data = data;
	                    if (me.options.callback.dataComplete) {
	                        me.options.callback.dataComplete(data);
	                    }
	                }  
	            });
            }
            this.bindData();
        },
        
        bindData: function(data, noJSON) {
            if (data) {
                this.data = data;
            }
            if (!noJSON && this.data && typeof this.data == "string") {
	            try {
	                var d = JSON.parse(this.data);
	                this.data = d;
	            } catch (ignored) {
	                
	            }
	        }
        },
        
        initEventHandlers: function() {
            throw new Error("Method 'initEventHandlers' is abstract");
        },
        
        assembleSubControls: function() {
            throw new Error("Method 'assembleSubControls' is abstract");
        },
        
        // ---------------------------------
        
        show: function() {
            if (this.element) {
                $(this.element).show();
            }
        },
        
        hide: function() {
            if (this.element) {
                $(this.element).hide();
            }
        },
        
        appendTo: function(elem) {
            if (this.element) {
                $(elem).append(this.element);
            }
        },
        
        get: function() {
            return this.element;
        }
    };
    return AppCtrl;
})();

}