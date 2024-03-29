
HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象
	
	// 获取提交按钮
	var submitBtn 	 = Ext.getCmp("submitBtn");
	// 获取保存按钮
	//var saveBtn 	   = Ext.getCmp("saveBtn");
	// 获取返回按钮
	var backBtn 		 = Ext.getCmp("backBtn");
	
	
	// -------------------------------------- 应用逻辑处理
	
	// 提交操作成功后要做的事情
	var submitSuccessPro;
	
	/**
	 * 提交数据
	 * @param url  (String) 提交的url
	 */
	function submitData(url) {
		// 验证 form 内容是符满足要求
		if(!ExtConvertHelper.isFormValid("form")) return;
		
		// 提交数据
		ExtConvertHelper.submitForm("form", url, null, function(form, action) {
			// 获取成功后的提示信息
			var msg = ExtConvertHelper.getMessageInfo(action, "操作成功！");
			
			// 弹出提示框给用户
			Ext.Msg.alert("提示", msg, submitSuccessPro);
		});
	}
	
	(function() {
		// 当提交按钮被单击时
		submitBtn.on("click", function() {
			submitData("/invoice/SendInvoice!save.action");
		});
		
		// 当保存按钮被单击时
		//saveBtn.on("click", function() {
		//	submitData("/customerOrder/customerOrder!saveTemp.action");
		//});
		
		// 当单机取消按钮时，调用默认的关闭窗口方法
		backBtn.on("click", HBSConvertHelper.defaultCloseTab);

	}())
	
	// -------------------------------------- 页面操作逻辑处理
	
	// 提交操作成功后要做的事情
	var submitSuccessPro;
	
	// 新增页面的处理逻辑
	function addInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("客户发票新增");
		
		// 提交完成后的操作
		submitSuccessPro = function() {
			// 用户单击后重载此页面
			location.reload();
		}
	}
	
	// 修改页面的处理逻辑
	function updateInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("客户发票修改");
		// 隐藏不需要的控件
		//if(urlPs.state != "01") ExtConvertHelper.hideItems("saveBtn");
		
		// 组装需要的参数
		var params = ["invoice.invoiceSeqId=", urlPs["invoice.invoiceSeqId"]].join("");
		
		// 加载数据
		ExtConvertHelper.loadForm("form", "/invoice/SendInvoice!getInfo.action", params, function(form, action) {
				//Ext.getCmp("ordergrid").addData(action.result.data.custOrder.orderlist);
		});
		
		// 提交完成后的操作
		submitSuccessPro = function() {
			// 用户单击后关闭此页面
			HBSConvertHelper.defaultCloseTab();
		}
	}
	
	// 根据不同的操作类型，做出不同的处理
	eval(urlPs.editorType + "InitFun")();
	
});