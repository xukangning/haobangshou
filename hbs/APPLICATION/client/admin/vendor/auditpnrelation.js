urlPs.pageType = "audit";
// 查询数据的地址
var querygridUrl = "/vendorInfo/vendorPartNoInfoMgr!list.action?vendorPartNoInfo.state=2";

HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象
	
	// 获取 批量通过按钮
//	var passBtn   = Ext.getCmp("passBtn");
	// 获取 批量通过按钮
//	var rejectBtn = Ext.getCmp("rejectBtn");
	// 获取表格
	var querygrid = Ext.getCmp("querygrid");
	
	// -------------------------------------- 应用逻辑处理
	
//	var submitFun = function(operator) {
//		// 提交的方法
//		var _submitFun = function(params) {
//			// 提交数据
//			ExtConvertHelper.submitForm(null, "/vendorInfo/vendorPartNoInfoMgr!auditList.action", params, function(form, action) {
//				// 获取成功后的提示信息
//				var msg = ExtConvertHelper.getMessageInfo(action, "操作成功！");
//				
//				// 弹出提示框给用户
//				Ext.Msg.alert("提示", msg, function() {
//					HBSConvertHelper.refreshGrid("querygrid");
//				});
//			});
//		};
//		
//		// 提交的参数
//		var params = {
//			"baseSeqId" : querygrid.getCheckFields(),
//			"audit" : operator
//		};
		
//		switch(operator) {
//			case "pass":
//				_submitFun(params);
//				break;
//			case "reject":
//				Ext.Msg.prompt("提示", "请输入拒绝原因：", function(btn, text) {
//					if(btn == "cancel") return;
//					if(!text.trim()) {
//						Ext.Msg.alert("提示", "必须输入拒绝原因。");
//						return;
//					}
//					params.jqyy = text;
//					_submitFun(params);
//				}, null, true);
//				break;
//		}
//	};
	
	// 点击批量通过按钮
//	passBtn.on("click", function() {
//		submitFun("pass");
//	});
	
	// 点击批拒绝过按钮
//	rejectBtn.on("click", function() {
//		submitFun("reject");
//	});
	
	querygrid.getView().on("refresh", function(view) {
		//alert(this.ds.getCount())
		for(var i = 0 ; i < view.ds.getCount() ; i++) {
			// 获取客户简称所在的列
			var commCode_cell = view.getCell(i, view.grid.getColumnIndexById("commCode"));
			// 将需要的链接渲染到此列
			HBSConvertHelper.renderATag2Cell(commCode_cell.innerText, "/vendor/detailpnrelation.jsp?pageType=query&seqId="+view.ds.getAt(i).get("seqId"), "open", commCode_cell);
			
			
			// 获取操作列
			var operator_cell  = view.getCell(i, view.grid.getColumnIndexById("operator"));	
		  // 创建按钮到操作列
			var auditBtn = HBSConvertHelper.renderButton2Cell(["审批"], operator_cell, view.ds.getAt(i));
			// 按钮的单击事件
			auditBtn.on("click", function() {
				// 要访问的 url 地址
				var url = ["/vendor/detailpnrelation.jsp?editorType=update&seqId=", this.config.get("seqId"), "&state=", this.config.get("state")].join("");
				// 打开指定页面
				HBSConvertHelper.openNewWin(url);
			});
	
		}
	});
	
});