Ext.onReady(function() {
			/*为项目自定义问题类别*/
			Ext.lib.Ajax.defaultPostHeader += '; charset=utf-8';
			var question_custom = Ext.get('question_kind_custom');
			if(question_custom == null) return;
			var scenarioId = Ext.get('scenario_id').dom.value;
			var projectId = Ext.get('project_id').dom.value;
			question_custom.addListener('click', function() {
						QuestionCustomWin.show();
					});
			var name = new Ext.form.TextField({
						fieldLabel : '名称',
						id : 'name',
						selectOnFocus : true,
						allowBlank : false
					});
					
			var description = new Ext.form.TextArea({
				fieldLabel:'描述',
				width:200,
				height:150,
				id:'description',
				selectOnFocus:true,
				allowBlank:false
				});
			
			var QuestionCustomForm = new Ext.form.FormPanel({
						labelWidth : 50,
						width : 200,
						frame : false,
						labelSeparator : ':',
						region : 'center',
						items : [name,description],
						buttons : [new Ext.Button({
											text : '提交',
											handler : submitQuestionKind
										}), new Ext.Button({
											text : '重置',
											handler : reset
										})]
					});
			var QuestionCustomWin = new Ext.Window({
						contentEI : 'window',
						title : '自定义问题类别',
						width : 300,
						height : 300,
						plain : true,
						closeAction : 'hide',
						maximizable : true,
						layout : 'border',
						items : [QuestionCustomForm]
					});
			function submitQuestionKind() {
				QuestionCustomForm.getForm().submit({
					clientValidation : true,
					waitMsg : '正在更新，请稍候',
					waitTitle : '提示',
					url : 'submitQuestionKind.so?projectId=' + projectId
							+ '&scenarioId=' + scenarioId,
					method : 'POST',
					success : function(form, action) {
						Ext.Msg.alert('成功', '提交成功,即将跳转');
						window.location.reload();
					},// success.
					failure : function(form, action) {
						Ext.Msg.alert('失败', '提交失败，请检查输入内容');
					}
				});// submit
			}
			function reset() {
				QuestionCustomForm.getForm().reset();
			}

		});