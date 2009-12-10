/**
 * ScenarioEditorManager.js
 *   依赖于：EditArea.js, PopupList.js, TermFinder.js
 *   
 * 用于创建Scenario页面中的各Editor，该EditorManager将被它所创建的所有Editor共享，
 * 也就是说，EditorManager提供的方法和数据是各Editor共有的
 * 
 * 在页面中使用ScenarioEditorManager.createEditor()来创建Editor，Editor有两类：Description和Remark
 */
var ScenarioEditorManager = (function(){
    var editorVersion = "editor_v2";
    var iframeVersion = "iframe";
    
	//术语下拉列表的默认设置
	//注意：一个页面中仅有一个下拉列表实例，因为界面上不会同时出现多个术语下拉列表
    var TermListOptions = {
        cssClass: "popuplist",
        itemClass: "popuplist_item",
        itemOverClass: "popuplist_item_over",
        defaultSelect: 0
    };
    
	//Description的默认设置
	var DescriptionOptions = {
		cssPath: "./css",
		cssFile: "editor.css",
		jsPath: "./js",
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
		console: "yes",
		menu: {
			save: "yes",
			commit: "yes",
			showHistory: "yes",
            format: "yes",
			help: "yes"
		},
		height: 400,
		dev: false,
		version: true,
		lineNo: true,
		usecaseView: true,  //视图：Usecase
		actionView: true,   //视图：Action
		scrollX: true,
		scrollY: true
	};

    //Remark的默认设置
	var RemarkOptions = {
		cssPath: "./css",
		cssFile: "editor.css",
		jsPath: "./js",
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
		height: 200,
		console: "no",
		menu: {
			save: "yes",
			anonymous: "yes",
			help: "yes"
		},
		editOnce: "yes",
		dev: false,
		version: false,
		scrollX: true,
		scrollY: true
	};

    //句式关键字，通过把不同的value对应到相同的type，可以达到一定的灵活性，
	//例如：“如果”、“若” 都等于 “if”
	//TODO: 当增加了新的句式或新的句式关键字时，要在此处添加
	var Keywords = [
	    // IF
	    {value: "如果", type: "keyword_if"},
		{value: "若", type: "keyword_if"},
		{value: "假如", type: "keyword_if"},
		{value: "当", type: "keyword_if"},
		// ELSE
		{value: "否则", type: "keyword_else"}
	];
    
	//构造函数
	var ScenarioEditorManager = function() {
		this.finder = new TermFinder(Keywords);
		this.ui = {termList: null,fakeTerm: null};
		this.editors = [];
	}

    ScenarioEditorManager.prototype = {
		
		/**
		 * 术语匹配算法实现
		 * @param {String} pattern：用来匹配的字符串
		 * @return (Array) 匹配到的术语
		 */
		//TODO: 把该算法的实现移到TermFinder中以更好的封装
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
						// 匹配成功
                        break;
                    }
                }
                result.offset = i;
            }
            return result;
        },

        /**
         * 添加一个术语，点击术语边上的“添加”时调用
         * @param {String} type
         */
        add: function(type){
        	//alert(type);
            if (type == 'participant' || type == 'observer' || type == 'communicator'){
                //PopUpWindow.showTable("cn.edu.pku.dr.requirement.elicitation.data.Role", '', this.addRoles, {
				//	type: type,
				//	self: this
				//});
				var data={};
				data.scenarioId=document.getElementById("scenarioId").value;
				//需要确保scenarioId不能为空，否则会造成删除的错误。
				if(!data.scenarioId)return ;
				data.roleType=type;
				data.property="roleId";
				data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.Scenario";
				PopUpWindow.showMultiSelect("easyJ.http.servlet.SingleDataAction", 
				"cn.edu.pku.dr.requirement.elicitation.data.Role","cn.edu.pku.dr.requirement.elicitation.data.ScenarioRoleRelation", data,MultiSelect.commonConfirm);
			}
            if (type == 'data') {
                //PopUpWindow.showTable("cn.edu.pku.dr.requirement.elicitation.data.Data", '', this.addData, {
				//	type: type,
				//	self: this
				//});
				var data={};
				data.scenarioId=document.getElementById("scenarioId").value;
				//需要确保scenarioId不能为空，否则会造成删除的错误。
				if(!data.scenarioId)return ;
				data.property="dataId";
				data["easyJ.http.Globals.CLASS_NAME"]="cn.edu.pku.dr.requirement.elicitation.data.Scenario";
				PopUpWindow.showMultiSelect("easyJ.http.servlet.SingleDataAction", 
				"cn.edu.pku.dr.requirement.elicitation.data.Data","cn.edu.pku.dr.requirement.elicitation.data.ScenarioDataRelation", data,MultiSelect.commonConfirm);
			}
        },
        
        addRoles: function(button, param){
			//添加一个术语（Role或Data）包括两步：（1）在页面上显示出来（2）添加到EditorManager中
			var type = param.type, self = param.self;
            var properties = document.getElementById("properties").value.split(",");
            var id = "#" + button.id;
            //firefox不支持parentElement，所以使用jQuery对象。
            var row = $(id).parent().parent()[0];
            var roles = document.getElementById(type + "_list");
            var value = {};
            for (i = 0; i < row.cells.length - 1; i++) {
                var property = properties[i];
                if (property == "roleName")
                    value.ruleName = row.cells[i + 1].innerHTML;
                if (property == "roleId")
                    value.roleId = row.cells[i + 1].innerHTML;
            }
            //在这里还需要向服务器提交，将角色加入数据库之后才修改后面的，否则如果有异常发生，则提示异常。

            var scenarioId=document.getElementById("scenarioId");
            value.scenarioId=scenarioId.value;
            var actionPath = "cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=addRole&type="+type+"&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario";
			var message=Ajax.submitLoadWithoutHistory(actionPath,null, null, value,false);
            if(message=='添加成功') {
	            if(roles.innerHTML&&roles.innerHTML.trim()!='')
	            	roles.innerHTML = roles.innerHTML + "," + value.ruleName;
	            else
	                roles.innerHTML = value.ruleName;
	            self.addTerm(value.ruleName, 'role');
            }

        },

        addData: function(button, param){
			var type = param.type, self = param.self;
            var properties = document.getElementById("properties").value.split(",");
            var id = "#" + button.id;
            //firefox不支持parentElement，所以使用jQuery对象。
            var row = $(id).parent().parent()[0];
            var datas = document.getElementById(type + "_list");
            var value = {};
            for (i = 0; i < row.cells.length - 1; i++) {
                var property = properties[i];
                if (property == "dataName")
                    value.dataName = row.cells[i + 1].innerHTML;
                if (property == "dataId")
                    value.dataId = row.cells[i + 1].innerHTML;
            }
            //在这里还需要向服务器提交，将角色加入数据库之后才修改后面的，否则如果有异常发生，则提示异常。
            var scenarioId=document.getElementById("scenarioId");
            value.scenarioId=scenarioId.value;
            var actionPath = "cn.edu.pku.dr.requirement.elicitation.action.ScenarioAction.do?ACTION=addData&type="+type+"&easyJ.http.Globals.CLASS_NAME=cn.edu.pku.dr.requirement.elicitation.data.Scenario";
			var message=Ajax.submitLoadWithoutHistory(actionPath,null, null, value,false);
            if(message=='添加成功') {
            	self.addTerm(value.dataName, 'data');
            	if(datas.innerHTML&&datas.innerHTML.trim()!='')
            		datas.innerHTML = datas.innerHTML + "," + value.dataName;
            	else
            		datas.innerHTML = value.dataName;
            }
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

		/**
		 * 
		 * @param {Object} holder：Editor将创建在holder所在的位置上
		 * @param {String} name：description或者remark
		 * @param {Object} options
		 * @param {String} id：指定Editor的id
		 */
        createEditor: function(holder, name, options, id){
        if (!holder) {
        return;
        }
            if (!this.ui.termList) {
				//创建术语下拉列表
                this.ui.termList = new PopupList("scenarioTermList", null, TermListOptions);
            }
            if (!this.ui.fakeTerm) {
				//创建“伪术语元素”，在术语clean时用到，参见HtmlManip.Term
                this.ui.fakeTerm = document.createElement("span");
                this.ui.fakeTerm.id = "scenario_fake_term";
                document.body.appendChild(this.ui.fakeTerm);
            }
            $("#scenario_fake_term").hide();
            $(".popuplist").hide();
			options = options || {};
            return this["_create_" + name.toLowerCase()](holder, options, (id ? id : null));
        },

		_create_description: function(holder, options, id) {
			setDefaults(options, DescriptionOptions);
			var ea = new EditArea((id ? id : "des_" + this.editors.length), holder, this, options);
			this.editors.push(ea);
			return ea;
		},

		_create_remark: function(holder, options, id) {
			//Remark需要创建一个额外的editor边框
			var border = document.createElement("div");
            border.className = "remark_border";
            var edit = document.createElement("textarea");
            border.appendChild(edit);
            if (options.createAfter) {
                insertAfter(border, options.createAfter);
            } else {
                holder.appendChild(border);
            }

			setDefaults(options, RemarkOptions);
			var ea = new EditArea((id ? id : "remark_" + this.editors.length), edit, this, options);
			this.editors.push(ea);
			return ea;
		},
		
		createReadOnlyArea: function(holder, holderIndex) {
		    return new ReadOnlyArea(holder, holderIndex);
		}

    };

    return ScenarioEditorManager;
})();
