var submitUrl = "";

HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象
	
	// 获取返回按钮
	var backBtn 		= Ext.getCmp("backBtn");
	// 获取提交按钮
	var submitBtn   = Ext.getCmp("submitBtn");
	
	// -------------------------------------- 应用逻辑处理
	
	// 当单击提交按钮时，调用默认的关闭窗口方法
	submitBtn.on("click", function() {
		ExtConvertHelper.submitForm("form", submitUrl, null, function(form, action) {
			// 获取成功后的提示信息
			var msg = ExtConvertHelper.getMessageInfo(action, "操作成功！");
			
			// 弹出提示框给用户
			Ext.Msg.alert("提示", msg, function() {
				// 用户单击后关闭此页面
				HBSConvertHelper.defaultCloseTab();
			});
		});
	});
	
	// 当单击取消按钮时，调用默认的关闭窗口方法
	backBtn.on("click", HBSConvertHelper.defaultCloseTab);
	
	// 初始化方法
	(function() {		
		// 组装需要的参数
		var params = ["vendorInfo.baseSeqId=", urlPs.baseSeqId].join("");
		var getInfoUrl = (urlPs.roleType == "cgy") ? "/vendorInfo/vendorInfo!getInfo.action"
			: "/vendorInfo/vendorInfoMgr!getInfo.action";
		// 加载数据
		ExtConvertHelper.loadForm("form", getInfoUrl, params, function(form, action) {
				Ext.getCmp("contactgrid").addData(action.result.data.vendorInfo.dynamicFields.contactlist);
				Ext.getCmp("consigneegrid").addData(action.result.data.vendorInfo.dynamicFields.consigneelist);
				Ext.getCmp("custbankgrid").addData(action.result.data.vendorInfo.listBankInfo);
				//add by yangzj
				var settlementType= action.result.data.vendorInfo.settlementType;
				switch(settlementType) {
					case "1":
						// 显示：账期类型,账期的起始日,账期的对账日,账期的结算日,客户账期的最大交易金额,提醒设置,供应商的账期设置,提醒日
						//ExtConvertHelper.showItems("vaAccountType,vaPeriodStart,vaAccounDay,vaSettlementDay,vaMaxMoney,vaReminderDay,vaAccountPeriod,vpReminderDay");
						ExtConvertHelper.showItems("vaPeriodStart,vaSettlementDay");
						ExtConvertHelper.hideItems("vpPrePaid");
						break;
					case "2":
					case "3":
						// 隐藏：账期类型,账期的起始日,账期的对账日,账期的结算日,客户账期的最大交易金额,提醒设置,供应商的账期设置,提醒日
						//ExtConvertHelper.hideItems("vaAccountType,vaPeriodStart,vaAccounDay,vaSettlementDay,vaMaxMoney,vaReminderDay,vaAccountPeriod,vpReminderDay");
						ExtConvertHelper.hideItems("vaPeriodStart,vaSettlementDay");
						ExtConvertHelper.showItems("vpPrePaid");
						break;
				}
		});
	}())
	
	var queryInitFun = function() {
		// 隐藏不需要的按钮
		ExtConvertHelper.hideItems("auditPanel,submitBtn");
	};
	
	var auditInitFun = function() {
		submitUrl = "/vendorInfo/vendorInfoMgr!audit.action";
	};
	
	// 根据不同的操作类型，做出不同的处理
	if(urlPs.pageType) eval(urlPs.pageType + "InitFun")();

});