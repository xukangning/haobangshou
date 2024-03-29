﻿
HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象

	// 获取提交按钮
	var submitBtn 	= Ext.getCmp("submitBtn");
	// 获取保存按钮
	var saveBtn 	  = Ext.getCmp("saveBtn");
	// 获取返回按钮
	var backBtn 		= Ext.getCmp("backBtn");
	// 获取订单详情表格
	var warehousegrid   = Ext.getCmp("warehousegrid");
	// 获取查询客户订单 window 控件
	var selectWindow = Ext.getCmp("selectWindow");


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

		// 获取（客户联系人信息、客户收货人信息、客户银行信息）表格中的提交数据
		var girdData = HBSConvertHelper.getGridSubmitData("warehousegrid", "orderlist");

		// 提交数据
		ExtConvertHelper.submitForm("form", url, girdData, function(form, action) {
			// 获取成功后的提示信息
			var msg = ExtConvertHelper.getMessageInfo(action, "操作成功！");

			// 弹出提示框给用户
			Ext.Msg.alert("提示", msg, submitSuccessPro);
		});
	}

	(function() {
		// 当提交按钮被单击时
		submitBtn.on("click", function() {
			submitData("/warehouseRec/warehouseRec!commit.action");
		});

		// 当保存按钮被单击时
		saveBtn.on("click", function() {
			submitData("/warehouseRec/warehouseRec!saveTemp.action");
		});

		// 当单机取消按钮时，调用默认的关闭窗口方法
		backBtn.on("click", HBSConvertHelper.defaultCloseTab);

		Ext.getCmp("addInfoBtn").on("click", function() {
			selectWindow.show();
			ExtConvertHelper.setItemsReadOnly("acVendorCode", true);
			//HBSConvertHelper.refreshGrid("querygrid");
			//Ext.getCmp("wokBtn").fireEvent("click");
		});

		warehousegrid.getView().on("refresh", function(view) {
			// 删除按钮触发事件
				var deleteBtnFun = function() {
				var seqid =this.config.get("recDetailSeqId");
				if(seqid > 0){
					Ext.Msg.confirm("提示", "您要执行的是入库单明细取消，请确认是否继续？", function(btn) {
					if(btn == "no") return;

							ExtConvertHelper.request("/warehouseRec/warehouseRec!cancelDetail.action?recDetailSeqId=" + this.config.get("recDetailSeqId"), null, ExtConvertHelper.defaultDeleteFun);
						}, this);
		        	}else{
			       	Ext.Msg.confirm("提示", "您要执行的是入库单明细操作，本条明细还没有保存，请确认是否继续删除？", function(btn) {
									if(btn == "no") return;
			        		warehousegrid.store.remove(this.config);
			        	}, this);
		        	}
				//warehousegrid.store.remove(this.config);
			};

			for(var i = 0 ; i < view.ds.getCount() ; i++) {
				// 获取操作列
				var operator_cell  = view.getCell(i, view.grid.getColumnIndexById("operator"));
				if(view.ds.getAt(i).get("state") != "03" && view.ds.getAt(i).get("state") != "02") {
					// 创建按钮到操作列
					var operatorBtn = HBSConvertHelper.renderButton2Cell(["删除"], operator_cell, view.ds.getAt(i));
					// 删除按钮的单击事件
					operatorBtn.on("click", deleteBtnFun);
				}
			}
		})

		/*
		warehousegrid.getSelectionModel().on("beforerowselect", function(sm, rowIndex, keepExisting, record) {
			switch(record.get("state")) {
				case "02":
				case "03":
					warehousegrid.getColumnById("cisTax").editable = false;
					break;
				default:
					warehousegrid.getColumnById("cisTax").editable = true;
			}
		});
		*/
	}())

	// -------------------------------------- 页面操作逻辑处理
	// 新增页面的处理逻辑
	function addInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("入库");

		// 到货日期缺省为当前日期
		Ext.getCmp("acApplyDate").setValue(new Date());

		// 提交完成后的操作
		submitSuccessPro = function() {
			// 用户单击后重载此页面
			location.reload();
		}
	}

	// 修改页面的处理逻辑
	function updateInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("入库修改");

		// 组装需要的参数
		var params = ["warehouseRec.recPoNo=", urlPs["warehouseRec.recPoNo"],"&warehouseRec.vendorCode=" , urlPs["warehouseRec.vendorCode"]].join("");

		// 加载数据
		ExtConvertHelper.loadForm("form", "/warehouseRec/warehouseRec!getInfo.action", params, function(form, action) {
			var warehouseRec = action.result.data.warehouseRec;
			Ext.getCmp("warehousegrid").addData(warehouseRec.detailList);
			Ext.getCmp("acOperTime").setValue(FormatUtil.data2string(warehouseRec.operTime));
			Ext.getCmp("acPoNoDate").setValue(FormatUtil.data2string(warehouseRec.poNoDate));
			Ext.getCmp("acApplyDate").setValue(FormatUtil.data2string(warehouseRec.applyDate));
			Ext.getDom("txtVendorCode").readOnly = true;
			Ext.getCmp("txtVendorCode").vType="";
			Ext.getCmp("txtVendorCode").allowBlank=true;
			Ext.getDom("txtRecPoNo").readOnly = true;
			Ext.getCmp("txtRecPoNo").vType="";
			Ext.getCmp("txtRecPoNo").allowBlank=true;
			Ext.getCmp("acCommCode").fireEvent("select");

			// 隐藏不需要的控件
			if(warehouseRec.state != "01") ExtConvertHelper.hideItems("saveBtn");
		});
	}

	// 根据不同的操作类型，做出不同的处理
	eval(urlPs.editorType + "InitFun")();

	// -------------------------------------- window 部分功能实现代码
	(function() {
		function setCommCode(action){
			if(!action.success)
				return;
			Ext.getCmp("acCommCode").setValue(action.data.vendorInfo.commCode);
			Ext.getCmp("acShortName").setValue(action.data.vendorInfo.shortName);
			// 给需要的隐藏域赋值
			Ext.getCmp("hidSettlementType").setValue(action.data.vendorInfo.settlementType);
			Ext.getCmp("acVendorCode").setValue(action.data.vendorInfo.commCode);
		}
		
		
		Ext.getCmp("acCommCode").setProcessConfig("/vendorInfo/vendorInfoMgr!getInfo.action?vendorInfo.state=0", "vendorInfo.commCode", null, setCommCode);
		Ext.getCmp("acShortName").setProcessConfig("/vendorInfo/vendorInfoMgr!getInfo.action?vendorInfo.state=0", "vendorInfo.shortName", null, setCommCode);

		Ext.getCmp("aWarehouseType").on("select", function(){
			if(this.selectedIndex < 0){
				var val = this.getValue();
				if(val){
					// 根据val设置selectedIndex
					this.selectedIndex = this.store.findExact("id", val);
				}
				if(this.selectedIndex < 0){
					Ext.getCmp("aAddress").setValue("");
					return;
				}
			}
			var data = this.store.getAt(this.selectedIndex);
			Ext.getCmp("aAddress").setValue(data.get("address"));
		});

		// 点击取消按钮的事件
		Ext.getCmp("wbackBtn").on("click", function() {
			selectWindow.hide();
		});

		// 点击确定按钮的事件
		Ext.getCmp("wokBtn").on("click", function() {
			// 获取查询列表
			var querygrid = Ext.getCmp("querygrid");
			// 获取选择的数据集
			var records = querygrid.getSelectionModel().getSelections();

			// 缓存数据
			var oldRecords = {};
			warehousegrid.store.each(function() {
				oldRecords[this.get("rltPoNo")] = true;
			});

			var newRecords = [];
			// 添加标示
			Ext.each(records, function(record) {
				if(!oldRecords[record.get("rltPoNo")]) {
					record.set("selectType", "window");
					newRecords.push(record);
				}
			});
			// 添加选择的数据至订单详情表格
			warehousegrid.store.add(newRecords);
			// 刷新表格，好像加了这句会有JS错误
			//setTimeout(function() {	warehousegrid.getView().refresh() }, 0);
			// 隐藏 window 控件
			selectWindow.hide();

		});
	}())
});