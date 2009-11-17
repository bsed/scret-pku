/* 包装了jqModal的对话框
 * 
 * 创建对话框时，传入以下参数：
 *   - title: String
 *   - id：String
 *   - className：String
 *   - modal：Bool
 *   - templateUrl：String，对话框内容HTML文件的url，如果此项已指定，则忽略content
 *   - content：对话框内容HTML，如果此项已指定，则忽略掉templateUrl
 *   - callbackShow：对话框onshow的callback
 *   - callbackHide：对话框关闭/隐藏时的callback
 * 注：以上两个回调函数将传入一个参数，表示该对话框的HTML元素对应的jQuery对象(即dialog window)
 *   - callbackLoad：当且仅当templateUrl指定时，由jqModal调用（参见jqModal文档）
 *
 * 新创建的对话框将自动带有class = dialogWindow
 */
var Dialog = (function() {
    
    var dlgWindowUrl = "http://localhost:8080/static/Dialog/Window.html";
    
    var Dialog = function(options) {
        this.$dialog = $(document.createElement("div"));
        if (options.className) {
            this.$dialog.addClass(options.className);
        }
        // 创建对话框窗口
        this.$dialog.attr("id", options.id ? options.id : "").addClass("dialogWindow").appendTo(document.body);
        var self = this;
        this.$dialog.ajaxStart(function() {
            alert("ajax started.");
        }).load(dlgWindowUrl, null, function() {
            alert("Dialog window loaded.");
	        // 标题    
	        $(".dialogTitle", self.$dialog).text(options.title);
	        // 内容
	        if (options.content) {
	            $(".dialogContent", self.$dialog).html(options.content);
	        }
	        // 利用jqModal创建对话框
	        self.$dialog.jqm({
	            modal: options.modal,
	            ajax: (options.templateUrl ? options.templateUrl : false),
	            target: $(".dialogContent", self.$dialog).get(0),
	            onShow: function(hash) {
	                if (options.callbackShow) {
	                    options.callbackShow(hash.w);
	                }
	                hash.w.show();
	            },
	            onHide: function(hash) {
	                if (options.callbackHide) {
	                    options.callbackHide(hash.w);
	                }
	                hash.w.fadeOut('2000',function(){ hash.o.remove(); });
	            },
	            onLoad: options.callbackLoad ? options.callbackLoad : false
	        });   
	        
	        // 由于IE不支持CSS的:hover和:focus，所以为关闭按钮(input.dialogClose)加上相应的类
			$('input.dialogClose', self.$dialog)
			  .hover(
			    function(){ $(this).addClass('dialogCloseHover'); }, 
			    function(){ $(this).removeClass('dialogCloseHover'); })
			  .focus( 
			    function(){ this.hideFocus=true; $(this).addClass('dialogCloseHover'); })
			  .blur( 
			    function(){ $(this).removeClass('dialogCloseHover'); });
		});
    };
    
    Dialog.prototype = {
        show: function() {
            this.$dialog.jqmShow();
        },
        
        hide: function() {
            this.$dialog.jqmHide();
        }
    };
    
    return Dialog;
})();