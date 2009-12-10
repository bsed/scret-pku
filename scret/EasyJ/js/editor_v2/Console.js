/**
 * Console for debug
 */
var Console = (function(){
	
	var Console = function(holder, refresh) {
		this.holder = holder;
		this.id = holder.id;
		this.history = [];
	};
	
	Console.prototype = {
		
		info: function(oInfo){
			var holder = document.createElement("div");
			
			var h = document.createElement("h3");
            h.style.color = "brown";
            h.style.fontWeight = "bold";
            h.appendChild(document.createTextNode(oInfo.lineno + ": " + oInfo.method));
			holder.appendChild(h);
			
			if (oInfo.msg) {
				var p1 = document.createElement("p");
				p1.style.color = "purple";
				p1.appendChild(document.createTextNode(oInfo.msg));
				holder.appendChild(p1);
			}
			
			if (oInfo.variables && oInfo.variables.length > 0) {
				var p2 = document.createElement("p");
				p2.appendChild(document.createTextNode("Variables: "));
				p2.appendChild(document.createElement("br"));
				for (var i = 0; i < oInfo.variables.length; ++i) {
					var v = oInfo.variables[i];
					p2.appendChild(document.createTextNode("  " + v.name + ": " + v.value));
					p2.appendChild(document.createElement("br"));
				}
				holder.appendChild(p2);
			}
			
			if (oInfo.flag && oInfo.flag == "end") {
				holder.appendChild(document.createElement("hr"));
			}
			
			this.history.push(holder);
		},
		
		warning: function(lineno, method, msg, vars) {
		    var holder = document.createElement("div");
			
			var h = document.createElement("h2");
            h.style.color = "red";
            h.style.fontWeight = "bold";
            h.appendChild(document.createTextNode("--- WARNING ---"));
			holder.appendChild(h);
			
			var h2 = document.createElement("h3");
			h2.appendChild(document.createTextNode(lineno + ": " + method));
			holder.appendChild(h2);
			
			var p1 = document.createElement("p");
			p1.style.color = "purple";
			p1.appendChild(document.createTextNode(msg));
			holder.appendChild(p1);
			
			if (vars && vars.length > 0) {
				var p2 = document.createElement("p");
				p2.appendChild(document.createTextNode("Variables: "));
				p2.appendChild(document.createElement("br"));
				for (var i = 0; i < vars.length; ++i) {
					var v = vars[i];
					p2.appendChild(document.createTextNode("  " + v.name + ": " + v.value));
					p2.appendChild(document.createElement("br"));
				}
				holder.appendChild(p2);
			}
			
			holder.appendChild(document.createElement("hr"));
			
			this.history.push(holder);
		},
		
		display: function() {
			if (this.holder) {
				$(this.holder).empty();
				insertArrayAtStart(this.history, this.holder);
			}
		},
		
		refresh: function() {
			this.history = [];
			if (this.holder) {
				$(this.holder).empty();
			}
		}		
	};
	return Console;
})();
