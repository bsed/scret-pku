/**
 * 生成一个版本树，看看这个效果是不是比 表格形式好。
 * baidu baike采用表格形式. --- web
 * svn,cvs等采用树结构表示.  --- 桌面客户端
 */
 Ext.onReady(function(){
 	var aTree=  Ext.get('version_tree');
 	var rootId = Ext.get('root_id').dom.value;
 	aTree.addListener('click',function(){
 		TWin.show();
 	})
 	var root = new Ext.tree.AsyncTreeNode({ // 这个地方应该得到 根场景的ID 号.
 		text:rootId,
 		expanded:true,
 		id:'root'
 	})
 	var tree = new Ext.tree.TreePanel({
 		//title:'example for the tree panel',
 		width:200,
 		height:100,
 		region:'center',
 		root:root,
 		loader:new Ext.tree.TreeLoader({
 			baseAttrs:{cust:'client'},
 			requestMethod:'Get',
 			dataUrl: 'versionTree.so?scenarioId='+rootId // 应该有一个 跟版本的参数.以该版本为根的树.
 		}),
 		autoLoad:false
 	});
 	var TWin = new Ext.Window({
						contentEI : 'window',
						title : '演化树',
						width : 500,
						height : 500,
						plain : true,
						closeAction : 'hide',
						maximizable : false,
						layout : 'border',
						items : [tree]
					});
 });
 
 
 
 
