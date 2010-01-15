if (!Module.isDefined("JsControl.ControlFactory")) {

Module.define("JsControl.ControlFactory");

JsControl.ControlFactory = (function() {
    var loadedDependencies = {};
    
    var basePath = {
        html: "../static/control/",
        js: "../static/control/js/",
        css: "../static/control/css/"
    };
    
    function mergeOptions(elem, options) {
        var optionTag = $("." + JsControl.CLASS.OPTIONS + ":first", elem);
        if (optionTag.get(0).parentNode == elem) {
	        var optionString = optionTag.html();
	        var opts = JSON.parse(optionString);
	        for (var name in opts) {
	            if (options[name] == null) {
	                options[name] = opts[name];
	            }
	        }
        }
    }
    
    function mergeUrl(options) {
        var map = Factory.controlInfo[options.type];
        if (!map) {
            return;
        }
        function merge(name) {
            if (!options.url[name] && map[name]) {
                options.url[name] = map[name]; 
            }
        }
        merge("html");
        merge("js");
        merge("css");
    }
    
    var Factory = {
        controlInfo: {},
        
        create: function(options) {
            if (options.target) {
                mergeOptions(options.target, options);
            } else {
                options.style = JsControl.STYLE.NEW;
            }
            if (!options.type) {
                throw new Error("Control init failed.\n\tReason: type is missing.");
            }
            if (!Factory.controlInfo[options.type]) {
                throw new Error("Control init failed.\n\tReason: type is unknown.");
            }
            
            //Merge options.url with controlUrlMap
            if (!options.url) {
                options.url = {};
            }
            mergeUrl(options);
            
            return new JsControl[options.type](options);
        },
        
        extend: function(control) {
            Factory.controlInfo[control.type] = {
                html: (control.html ? (basePath.html + control.html) : null),
                js: (control.js ? (basePath.js + control.js) : null),
                css: (control.css ? (basePath.css + control.css) : null),
                canHaveChildren: (control.canHaveChildren ? true : false)
            };
        },
        
        loadDependency: function(name) {
            if (name && !loadedDependencies[name]) {
                var extStart = name.lastIndexOf(".");
                var ext = name.substring(extStart + 1);
                if (ext == "js") {
                    $("head").append('<script type="text/javascript" src="' + name + '"></scri' + 'pt>');
                } else if (ext == "css") {
                    $("head").append('<link rel="stylesheet" type="text/css" href="' + name + '" />');
                }
                loadedDependencies[name] = true;
            }
        }
    };
    return Factory;
})();

}