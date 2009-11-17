var PlainTextEditorManager = (function(){
    var editorVersion = "editor_v2";
    var iframeVersion = "iframe";
    var TermListOptions = {
        cssClass: "popuplist",
        itemClass: "popuplist_item",
        itemOverClass: "popuplist_item_over",
        defaultSelect: 0
    };

	var PlainTextAreaOptions = {
		cssPath: "./css",
		cssFile: "editor.css",
		jsPath: "./js",
		// jsFile中各文件的顺序很重要
		jsFile: [
		    "jquery-1.2.3.js",
            "jquery.dimensions.js",
			editorVersion + "/json2.js",
		    editorVersion + "/" + iframeVersion + "/Constant.js",
  		    editorVersion + "/Util.js",
			editorVersion + "/HtmlParser.js",
		    editorVersion + "/" + iframeVersion + "/Selection.js",
		    editorVersion + "/" + iframeVersion + "/HtmlManip.js",
		    editorVersion + "/" + iframeVersion + "/Controller.js",
		    editorVersion + "/" + iframeVersion + "/Editor.js"
		],
		//TODO: jquery改为压缩版
		height: 100,
		console: "no",
		dev: false,
		version: false,
		scrollX: true,
		scrollY: true
	};

	var Keywords = [
	    {value: "如果", type: "keyword_if"},
		{value: "若", type: "keyword_if"},
		{value: "假如", type: "keyword_if"},
		{value: "当", type: "keyword_if"},
		{value: "否则", type: "keyword_else"}
	];

	var PlainTextEditorManager = function() {
		this.finder = new TermFinder(Keywords);
		this.ui = {termList: null,fakeTerm: null};
		this.editors = [];
	}

    PlainTextEditorManager.prototype = {
        findTerm: function(pattern){
            var result = {
                offset: 0,
                pattern: null,
                terms: null
            };
            if (pattern) {
                var i = 0;
                for (; i < pattern.length; ++i) {
                    result.pattern = pattern.substring(i);
                    result.terms = this.finder.find(result.pattern);
                    if (result.terms.length > 0) {
                        break;
                    }
                }
                result.offset = i;
            }
            return result;
        },

        addTerm: function(value, type){
            if (typeof value == "string" && typeof type == "string") {
                this.finder.addTerm({
                    value: value,
                    type: type
                });
            }
        },

        addTerms: function(values, type){
            var vec = values.split(/\s*,\s*/);
            for (i = 0; i < vec.length; ++i) {
                this.addTerm(vec[i], type);
            }
        },

		// Call createEditor, not createDescription/Remark
        createEditor: function(holder, options, id){
        if (!holder) {
        return;
        }
            if (!this.ui.termList) {
                this.ui.termList = new PopupList("plainTextTermList", null, TermListOptions);
            }
            if (!this.ui.fakeTerm) {
                this.ui.fakeTerm = document.createElement("span");
                this.ui.fakeTerm.id = "plainText_fake_term";
                document.body.appendChild(this.ui.fakeTerm);
            }
			options = options || {};
            setDefaults(options, PlainTextAreaOptions);
			var ea = new EditArea((id ? id : "editarea_" + this.editors.length), holder, this, options);
			this.editors.push(ea);
			return ea;
        }
    };

    return PlainTextEditorManager;
})();
