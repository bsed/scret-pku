var LineNumberPanel = (function() {

    var LineNumberPanel = function(panel) {
        this.id = panel.id;
        this.panel = panel;
        this.baseHeight = $(panel).height();
        this.lines = 0;
    };
    
    LineNumberPanel.prototype = {
        init: function(lines) {
            lines = (lines < 1 ? 1 : lines);
            this.lines = lines;
            var $panel = $(this.panel);
            $panel.empty();
            for (var i = 1; i <= lines; ++i) {
                $panel.append("<div class='editorLineText'>" + i + "</div>");
            }
        },
        
        scroll: function(scrollTop) {
            $(this.panel).css({
                "margin-top": (-scrollTop) + "px",
                "height": (this.baseHeight + scrollTop) + "px"
            });
        },
        
        // 应该始终调用change，而不要直接调用increase和decrease
        increase: function(lines) {
            if (lines < 0) {
                this.decrease(-lines);
            } else {
                var $panel = $(this.panel);
                for (var i = 1; i <= lines; ++i) {
                    $panel.append("<div class='editorLineText'>" + (i+this.lines) + "</div>");
                }
                this.lines += lines;
            }
        },
        
        decrease: function(lines) {
            if (lines < 0) {
                this.increase(-lines);
            } else {
                for (var i = 1; i <= lines; ++i) {
                    $("div.editorLineText:last", this.panel).remove(); 
                }
                // At least one line
                this.lines -= lines;
                if (this.lines <= 0) {
                    this.lines = 1;
                    $(this.panel).append("<div class='editorLineText'>1</div>");
                }
            }
        },
        
        change: function(selection, increment) {
            // 用户先删掉从start到end之间的回车，然后再增加increment行
            var netIncrement = selection.start - selection.end + increment;
            this.increase(netIncrement);
        }
    };
    
    return LineNumberPanel;
})();