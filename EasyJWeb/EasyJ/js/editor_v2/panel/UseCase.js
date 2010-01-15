var UseCasePanel = (function() {
    
    var UseCasePanel = function(panel) {
        this.panel = panel;
        this.id = panel.id;
        this.baseHeight = $(panel).height();
        this.lines = 0;
        this.$usecase = null;
    };
    
    UseCasePanel.prototype = {
        setLogger: function(logger) {
            this.logger = logger;
        },
        init: function(lines, usecaseInfo) {
            this.logger.info(16, "UseCasePanel.init()", "use case panel opened");
            // usecaseInfo is a set of {name, start, end, aux}
            lines = (lines < 1 ? 1 : lines);
            this.lines = lines;
            
            var $panel = $(this.panel);
            $panel.empty();
            // First, we create an empty panel
            for (var i = 1; i <= this.lines; ++i) {
                $panel.append("<div class='editorUseCaseEmptyLine'>&nbsp;+&nbsp;</div>");
            }
            // Then we fill the usecases
            if (usecaseInfo && usecaseInfo.length) { // Ensure typeof usecaseInfo == "array"
                for (var j in usecaseInfo) {
                    this.createUseCase($panel, usecaseInfo[j]);
                }
            }
            var self = this;
            // At last, create a "new usecase" icon in empty lines
            $("div.editorUseCaseEmptyLine", $panel).each(function() {
                self.createEmptyLine($(this));
            });
            this.$usecase = $("div.editorUseCaseStartLine", this.panel);
        },
        
        dump: function() {
            var result = [];
            this.$usecase.each(function() {
                result.push($(this).get(0).info);
            });
            return result;
        },
        
        scroll: function(scrollTop) {
            $(this.panel).css({
                "margin-top": (-scrollTop) + "px",
                "height": (this.baseHeight + scrollTop) + "px"
            });
        },
        
        change: function(selection, increment) {
            if (selection.start == selection.end) {
                switch (increment) {
                    case -1:
                        if (selection.atLineStart) {
                            this.deleteLines(selection.start, selection.end);
                        } else {  // it MUST be selection.atLineEnd
                            this.deleteLines(selection.start + 1, selection.end + 1);
                        }
                        break;
                    case 1: 
                        this.addLinesAfter(selection.start, increment);
                        break;
                }
            } else {
                switch (increment) {
                    case 1:
                        this.deleteLines(selection.start + 1, selection.end - 1);
                        break;
                    case 0:
                        this.deleteLines(selection.start + 1, selection.end);
                        break;                        
                }
            }
            /*var d = this.dump();
            var msg = "";
            for (var n in d) {
                msg += n + ":  " + objToString(d[n]) + "\n";
            }
            if (msg != "") {
            	alert(msg);
            }*/
        },
        
        // Use Case Range Change
        deleteLines: function(start, end) {
            if (start > end) {
                return;
            }
            // The lines from "start" to "end" are completely removed
            // 1) Remove all empty lines between start and end
            var self = this;
            self.logger.info(97, "deleteLines()", "start = " + start + ", end = " + end);
            $("div", this.panel).each(function(index) {
                var line = index + 1;
                if (line < start) {
                    return true;
                }
                if (line > end) {
                    return false;
                }
                var $me = $(this);
                if ($me.hasClass("editorUseCaseEmptyLine")) {
                    $me.addClass("__removed__");
                } 
            });
            // 2) Adjust all usecases between start and end
            this.$usecase.each(function() {
                var $start = $(this);
                var info = $start.get(0).info;
                self.logger.info(115, "deleteLines()", "Use Case Info: " + objToString(info));
                if (info.end < start) {
                    return true;
                } else if (info.start < start && start <= info.end) {
                    var $sibling = $start;
                    var infoEnd = info.end;
                    info.end -= (end - start + 1);
                    if (info.end < start - 1) {
                        info.end = start - 1;
                    }
                    if (infoEnd <= end) {
	                    for (var i = info.start + 1; i < info.end; ++i) {
	                        $sibling = $sibling.next();
	                    }
	                    
	                    if (info.end == info.start) {
	                        self.createStartLine($start, info);
	                    } else {
	                        $sibling = $sibling.next();
	                        self.createEndLine($sibling, info);
	                    }
	                    for (var i = start; i <= infoEnd; ++i) {
	                        $sibling = $sibling.next();
	                        $sibling.addClass("__removed__");
	                    }
                    } else {
                        for (var i = info.start; i < start; ++i) {
                            $sibling = $sibling.next();
                        }
                        for (var i = start; i <=end; ++i) {
                            $sibling.addClass("__removed__");
                            $sibling = $sibling.next();
                        }
                    }
                } else if (info.start >= start && info.end <= end) {
                    for (var i = info.start; i <= info.end; ++i) {
                        $start.addClass("__removed__");
                        $start = $start.next();
                    }
                } else if (info.start <= end && end < info.end) {
                    for (var i = info.start; i <= end; ++i) {
                        $start.addClass("__removed__");
                        $start = $start.next();
                    }
                    info.start = start;
                    info.end -= (end - start + 1);
                    self.createStartLine($start, info);
                } else if (info.start == info.end && info.start >= start) {
                    $start.addClass("__removed__");
                } else {
                    info.start -= (end - start + 1);
                    info.end -= (end - start + 1);
                }
            });
            
            // 3) Remove all marked lines
            $("div.__removed__", this.panel).remove();
            this.$usecase = $("div.editorUseCaseStartLine", this.panel);
        },
        
        addLinesAfter: function(start, lines) {
            if (lines <= 0) {
                return;
            }
            $me = $("div", this.panel).eq(start - 1);
            var sibling = $me.next().get(0);
            for (var i = 0; i < lines; ++i) {
                var $line = $(document.createElement("div"));
                if ($me.hasClass("editorUseCaseEmptyLine")) {
                	this.createEmptyLine($line);
                } else {
                    this.createMiddleLine($line);
                }
                if ($me.hasClass("editorUseCaseEndLine") && !$me.hasClass("editorUseCaseStartLine")) {
                    $me.before($line);
                } else {
                	$me.after($line);
                }
            }
            
            if ($me.hasClass("editorUseCaseStartLine")) {
                var info = $me.get(0).info;
                if (info.start == info.end) {
                    info.end += lines;
                    var $end = $(sibling).prev(); // find last new line
                    this.createStartLine($me, info);
                    this.createEndLine($end, info);
                } else {
                    info.end += lines;
                }
            } else if ($me.hasClass("editorUseCaseMiddleLine")) {
                while (!$me.hasClass("editorUseCaseStartLine")) {
                    $me = $me.prev();
                }
                var info = $me.get(0).info;
                info.end += lines;
            } else if ($me.hasClass("editorUseCaseEndLine")) {
                var info = $me.get(0).info;
                info.end += lines;
            }
            this.$usecase = $("div.editorUseCaseStartLine", this.panel);
            this.$usecase.each(function() {
                var $start = $(this);
                var info = $start.get(0).info;
                if (info.start > start) {
                    info.start += lines;
                    info.end += lines;
                }
            });
        },
        // Use Case Creation
        createUseCase: function(panel, info) {
            var $start = $("div:nth-child(" + info.start +")", panel);
            
            if (info.start < info.end) {
                this.createStartLine($start, info);
                for (var i = info.start + 1; i < info.end; ++i) {
                    $start = $start.next();
                    this.createMiddleLine($start);
                }
                this.createEndLine($start.next(), info);
            } else {
                this.createStartLine($start, info);
            }
        },
        
        createEmptyLine: function($line) {
            var self = this;
            //TODO: use image
            var icon = document.createElement("span");
            $(icon).html("&nbsp;+&nbsp;").css("cursor", "pointer").bind("click", function(event) {
                var line = $("div", self.panel).index($line[0]) + 1;  // index starts with 0
                var range = line, $sibling = $line.next();
                var n = null;
                while ($sibling.length > 0 && !$sibling.hasClass("editorUseCaseStartLine")) {
                    ++range;
                    $sibling = $sibling.next();
                }
                var onLoad = function(win) {
                    $("span.cur", win).text(line);
                    var $select = $("select.end", win);
                    for (var i = line; i <= range; ++i) {
                        $select.append("<option value='" + i + "'>" + i + "</option>");
                    }
                };
                var onConfirm = function(win) {
                    var $name = $("select.name", win);
                    var nameIndex = $name.val();
                    n = $("option:nth-child(" + nameIndex + ")", $name).text();
                    range = $("select.end", win).val();
                    self.createUseCase(self.panel, {start: line, end: range, name: n, aux: null});
                    self.$usecase = $("div.editorUseCaseStartLine", self.panel);
                    
                };
                PopUpWindow.show({
                    url: "static/Dialog/EditUseCaseLink.html",
                    callbacks: {
                        "load": onLoad,
                        "confirm": onConfirm
                    }
                });
            });
            
            $line.empty().append(icon).removeClass().addClass("editorUseCaseEmptyLine");
            $line.get(0).info = undefined;        
        },
        
        createStartLine: function($line, info) {
            //TODO: Add hyperlink for usecase.name, and range adjusting icons
            //TODO: If name is too long, then the text area will disappear
            var self = this;
            
            var $up = $(document.createElement("span"));
            $up.addClass("editorUseCaseButton").text("\u2191").click(function(event) {  // unicode: uparrow
                self.widen(event, "prev"); 
            });
            
            var $down = $(document.createElement("span"));
            $down.addClass("editorUseCaseButton").text("\u2193"); // unicode: downarrow
            if (info.start == info.end) {
                $down.click(function(event) {
                    self.widen(event, "next");
                });
            } else {
                $down.click(function(event) {
                    self.narrow(event, "next");
                });
            }
            
            var $collapse = $(document.createElement("span"));
            $collapse.text("-").click(function(event) {
                self.collapse(event);
            });
        	var $name = $(document.createElement("span"));
        	$name.addClass("editorUseCaseName").text(info.name);
         
            $line.empty();
            $line.append($up).append($down).append($name).append($collapse).removeClass().addClass("editorUseCaseStartLine");
            $line.get(0).info = info;
            if (info.start == info.end) {
                $line.addClass("editorUseCaseEndLine");
            }
        },
        
        createMiddleLine: function($line) {
            $line.removeClass().addClass("editorUseCaseMiddleLine").html("<span class='editorUseCaseMiddle'>&nbsp;&nbsp;</span>");
            $line.get(0).info = undefined;
        },
        
        createEndLine: function($line, info) {
            if (info.start == info.end) {
                // Should call createStartLine instead
                return;
            }
            var self = this;
            
            var $up = $(document.createElement("span"));
            $up.addClass("editorUseCaseButton").text("\u2191").click(function(event) {  // unicode: uparrow
                self.narrow(event, "prev"); 
            });
            
            var $down = $(document.createElement("span"));
            $down.addClass("editorUseCaseButton").text("\u2193").click(function(event) { // unicode: downarrow
                    self.widen(event, "next");
            });
            $line.empty().append($up).append($down).append("<span class='editorUseCaseEnd'>&nbsp;&nbsp;</span>").removeClass().addClass("editorUseCaseEndLine");
            $line.get(0).info = info;
        },
        
        // Use Case Manipulation
        widen: function(event, dir) {
            var $me = $(event.target).parent();
            var $sibling = $me[dir]();
            if ($sibling.hasClass("editorUseCaseEmptyLine")) {
                var info = $me.get(0).info;
                if (dir == "next") {
                    var end = info.end;
                    ++info.end;
                    this.createEndLine($sibling, info);
                    if (info.start == end) {
                        this.createStartLine($me, info);
                    } else {
                        this.createMiddleLine($me);
                    }
                } else if (dir == "prev") {
                    var start = info.start;
                    --info.start;
                    this.createStartLine($sibling, info);
                    if (start == info.end) {
                        this.createEndLine($me, info);
                    } else {
                        this.createMiddleLine($me);
                    }
                }
            }
        },
        
        narrow: function(event, dir) {
            var $me = $(event.target).parent();
            var $sibling = $me[dir]();
            var info = $me.get(0).info;
            
            if (info.start < info.end) {
                if (dir == "next") {
                    ++info.start;
                } else if (dir == "prev"){
                    --info.end;
                }
                if (info.start == info.end) {
                    this.createStartLine($sibling, info);
                } else {
                    if (dir == "next") {
                        this.createStartLine($sibling, info);
                    } else if (dir == "prev") {
                        this.createEndLine($sibling, info);
                    }
                }
                this.createEmptyLine($me);
            }
        },
        
        expand: function(event) {
        },
        
        collapse: function(event) {
        }
    };
    
    return UseCasePanel;
})();