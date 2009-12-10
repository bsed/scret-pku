/**
 * EditorManagerFactory.js
 *    依赖于：ScenarioEditorManager.js, PlainTextEditorManager.js
 * 
 * 在一个使用场景编辑器的页面中，有且仅有唯一的EditorManager。
 * EditorManager分为两类：Scenario（功能齐全）和PlainText（功能较少）
 *  - Scenario型的功能：术语匹配、版本比较、多视图、添加评论
 *  - PlainText型的功能：仅有术语匹配
 */
var EditorManagerFactory = (function() {
	
	var _creator = {
		
		scenario: function() {
			return new ScenarioEditorManager();
		},
		
		plaintext: function(options) {
			return new PlainTextEditorManager();
		}
	};
	
	var EditorManagerFactory = {
		
		//根据name创建EditorManager
		// name == "scenario" or "plaintext"
		createEditorManager: function(name) {
		    
			return _creator[name.toLowerCase()]();
		}
		
	};
	
	return EditorManagerFactory;
})();
