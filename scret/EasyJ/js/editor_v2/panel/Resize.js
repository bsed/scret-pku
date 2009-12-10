var ResizeControl = (function() {
    
    var ResizeControl = function(panel, $context) {
        this.panel = panel;
        this.$lineNumber = $("div.editorLineNoPanel", $context);
        this.$sidePanel = null;
        this.$content = $(".editorContent", $context);
        this.$parent = $context;
        
        // Fixed widths
        this.totalWidth = this.$parent.width();
        this.lineNumberWidth = this.$lineNumber.width();
        this.myWidth = $(this.panel).width();
        this.minWidth = 25;  // 25px
        this.maxWidth = this.totalWidth - this.lineNumberWidth - this.myWidth - this.minWidth;
        
        this.startX = 0;
        var self = this;
        
        function onMove(event) {
        	event.stopPropagation();
            event.preventDefault();
        	var endX = parseInt(event.pageX);
		    var sideWidth = self.$sidePanel.width() + endX - self.startX;
		    if (sideWidth < self.minWidth) {
		        sideWidth = self.minWidth;
		    } else if (sideWidth > self.maxWidth) {
		        sideWidth = self.maxWidth;
		    }
		    self.$sidePanel.width(sideWidth); 
		    self.$content.width(self.totalWidth - self.lineNumberWidth - self.myWidth - sideWidth - 10);
		    self.startX = endX;
        }
        
        function onUp(event) {
        	event.stopPropagation();
            event.preventDefault();
            $(document).unbind("mousemove", onMove).unbind("mouseup", onUp);
        }
        
        $(this.panel).mousedown(function(event) {
            event.stopPropagation();
            event.preventDefault();
            self.startX = parseInt(event.pageX);
            $(document).bind("mousemove", onMove).bind("mouseup", onUp);            
	    });
    };
    
    ResizeControl.prototype = {
        attachSidePanel: function(panel) {
            this.$sidePanel = $(panel);
        }
    };
    return ResizeControl;
})();