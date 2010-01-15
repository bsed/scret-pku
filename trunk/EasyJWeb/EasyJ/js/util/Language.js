// 扩展JS语言

// ---------------------
//      Module
// ---------------------
var Module;
if (Module && typeof Module != "object") {
    throw new Error("Conflict: symbol 'Module' has already existed.");
}
if (!Module) {
    Module = {
        NAME: "Module",
        VERSION: "1.0",
        
        global: this,
        
        modules: {"Module": Module},
        
        define: function(name, version) {
            if (!name) {
                throw new Error("Missing module name.");
            }
            if (name.charAt(0) == "." || name.charAt(name.length - 1) == "." || name.indexOf("..") >= 0) {
                throw new Error("Illegal module name: " + name);
            }
            var parts = name.split(".");
            var container = Module.global;
            var qualifiedName = "";
            for (var i = 0; i < parts.length; ++i) {
                var part = parts[i];
                if (/[^\$\da-zA-Z]/.test(part)) {  // 名字中含有非法字符
                    throw new Error("Illegal module name: " + name);
                }
                if (!container[part]) {
                    container[part] = {};
                } else if (typeof container[part] != "object") {
                    var n = parts.slice(0, i).join('.');
                    throw new Error("Conflict: symbol '" + n + "' has already existed.");  
                }
                qualifiedName += (i == 0 ? "" : ".") + part;
                if (qualifiedName != name && !Module.modules[qualifiedName]) {
                    Module.modules[qualifiedName] = container[part];
                }
                container = container[part];
            }
            
            // 支持先用其他手段定义模块，然后用Module.define把它添加到Module的管理中，
            // 但是不允许用Module.define定义同一个模块多次
            if (container.NAME) {
                throw new Error("Module '" + name + "' has already defined.");
            }
            container.NAME = name;
            if (version) {
                container.VERSION = version;
            }
            
            Module.modules[name] = container;
        },
        
        require: function(name, version) {
            if (!name in Module.modules) {
                throw new Error("Module '" + name +"' is not defined.");
            }
            if (!version) {
                return;
            }
            var m = Module.modules[name];
            
            // ver1 < ver2, returns negative; ver1 = ver2, returns 0; ver1 > ver2, returns positive
            function compareVersion(ver1, ver2) {
                var v1 = ver1.split("."), v2 = ver2.split(".");
                var minLength = (v1.length < v2.length ? v1.length : v2.length);
                for (var i = 0; i < minLength; ++i) {
                    var num1 = v1[i], num2 = v2[i];
                    if ((/[^\d]/.test(num1)) || (/[^\d]/.test(num2))) {
                        throw new Error("Illegal version string.");
                    }
                    num1 = parseInt(num1);
                    num2 = parseInt(num2);
                    if (num1 < num2) {
                        return -1;
                    }
                    if (num1 > num2) {
                        return 1;
                    }
                }
                return v1.length - v2.length;
            }
            
            if (!m.VERSION || compareVersion(m.VERSION, version) < 0) {
                throw new Error("Module '" + name + "' has version " + m.VERSION + ", but requires version " + version + " or higher");
            }
        },
        
        // from是一个名字或者是一个名字数组，如果不指定to则默认为全局空间
        // 可以使用"*"通配符，例如 org.xx.util.*
        importSymbols: function(from, to) {  // "import" is a reserved word in JS
            
            if (!to) {
                to = Module.global;
            }
            
            function importSymbol(source) {
                if (typeof source != "string") {
                    throw new Error("Cannot import: illegal symbol.");
                }
                var wildcard = false;
                var lastDotIndex = source.lastIndexOf(".");
                var alias = source.substring(lastDotIndex + 1);
                if (alias == "*") {
                    wildcard = true;
                    source = source.substring(0, lastDotIndex);
                }
                source = Module.modules[source];
                if (!source || typeof source != "object") {
                    throw new Error("Cannot import: illegal symbol.");
                }
                
                if (alias == "*") {
                    if (source == to) {
                        return;  // Should we throw an exception here?
                    }
                    for (var name in source) {
                        to[name] = source[name];
                    }
                } else {
                    to[alias] = source;
                }
            }
            
            if (typeof from == "string" ) {
                importSymbol(from);
            } else if (typeof from == "object" && from.length != null) {
                for (var i = 0; i < from.length; ++i) {
                    importSymbol(from[i]);
                }
            } else {
                throw new Error("Cannot import: illegal symbol.");
            }
        },
        
        isDefined: function(name) {
            return name in Module.modules;
        },
        
        dump: function() {
            var s = "Modules: 'Module'";
            for (var name in Module.modules) {
                if (name != "Module") {
                    s += ", '" + name + "'";
                }
            }
            return s;
        }      
    };
}

// --------------
//     OOP
// --------------
Module.define("JS");

JS.extend = function(subClass, superClass) {
    var F = function() {};
    F.prototype = superClass.prototype;
    subClass.prototype = new F();
    subClass.prototype.constructor = subClass;
    
    subClass.superClass = superClass.prototype;
    if (superClass.prototype.constructor == Object.prototype.constructor) {
        superClass.prototype.constructor = superClass;
    } 
};

JS.toString = function(data, delimiter, indent) {
    delimiter = (delimiter && typeof delimiter == "string" ? delimiter : "\n");
    indent = (indent && typeof indent == "string" ? indent : "\t");
    
    function toStr(d, level) {
        if (d == null) {
     	   return "null";
    	}
        var s = "";
        switch (typeof d) {
	        case "object":
	            var indentString = "", endIndent = "";
		        for (var i = level; i > 0; --i) {
		            indentString += indent;
		            if (i == level) {
		                continue;
		            }
		            endIndent += indent;
		        }
		        if (d.constructor == Array) {
		            s += "[" + delimiter;
		            for (var i = 0; i < d.length; ++i) {
		                s += indentString + toStr(d[i], level+1) + delimiter;
		            }
		            s += endIndent + "]" + delimiter;
		        } else {
		            s += "{" + delimiter;
		            for (var n in d) {
		                s += indentString + n + ": " + toStr(d[n], level+1) + delimiter;
		            }
		            s += endIndent + "}" + delimiter;
		        } 
	            break;    
	        case "function":
	            s += "Function";
	            break;
	        default:
	            s += d;
	    }
        return s;
    } 
    
    return toStr(data, 1);
}