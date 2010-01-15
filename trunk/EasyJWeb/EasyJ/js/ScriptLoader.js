var ScenarioScriptLoader = (function() {
	
	var _libFiles = [
	    "jquery.dimensions.js"
	];
	var _editorFiles = [
	    "json2.js",
		"Util.js",
		"Console.js",
		"PopupList.js",
		"TermFinder.js",
		"Version.js",
		"HtmlParser.js",
        "Formatter.js",
        "panel/LineNumber.js",
        "panel/UseCase.js",
        "panel/Resize.js",
		"usecase/UseCaseView.js",
		"action/ActionView.js",
		"EditArea.js",
		"ReadOnlyArea.js",
		"ScenarioEditorManager.js",
		"PlainTextEditorManager.js",
		"EditorManagerFactory.js"
	];
	
	var _basePath = "./js";
	var _editorPath = "editor_v2";
	
	var ScenarioScriptLoader = {}; 
	
	ScenarioScriptLoader.loadScripts = function() {
		for (var i = 0; i < _libFiles.length; ++i) {
			document.write('<script type="text/javascript" src="' + _basePath + '/' + _libFiles[i] + '"> </script>');
		}
		for (var i = 0; i < _editorFiles.length; ++i) {
			document.write('<script type="text/javascript" src="' + _basePath + '/' + _editorPath + '/' + _editorFiles[i] + '"> </script>');
		}
	};
	
	return ScenarioScriptLoader;
})();
ScenarioScriptLoader.loadScripts();
