HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象
	
	// 获取表格
	var querygrid = Ext.getCmp("querygrid");
	//HBSConvertHelper.getATagString("ffff", "abc.action", "open");
	
	
	
	// -------------------------------------- 应用逻辑处理
	
	querygrid.getView().on("refresh", function(view) {
		//alert(this.ds.getCount())
		for(var i = 0 ; i < view.ds.getCount() ; i++) {
			// 获取客户简称所在的列
			var shortName_cell = view.getCell(i, view.grid.getColumnIndexById("shortName"));
			// 获取操作列
			var operator_cell  = view.getCell(i, view.grid.getColumnIndexById("operator"));
			// 将需要的链接渲染到此列
			HBSConvertHelper.renderATag2Cell(shortName_cell.innerText, "abc.action", "open", shortName_cell);
			
			// 操作列显示逻辑
			switch(view.ds.getAt(i).get("state")) {
				case "1":
				case "0":
				  // 创建按钮到操作列
					HBSConvertHelper.renderButton2Cell(["删除", "恢复", "修改"], operator_cell, view.ds.getAt(i));
					break;
			}
			
		}
	})
});