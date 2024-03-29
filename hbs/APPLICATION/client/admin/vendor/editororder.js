//TODO：从配置中获取此值，暂不实现
var VendorDate2CustDate = 3;
var isManulSelect = true;	//标记是否人为选择commCode

HBSConvertHelper.init(function() {
	// -------------------------------------- 获取需要持久用到的对象

	// 获取提交按钮
	var submitBtn 	 = Ext.getCmp("submitBtn");
	// 获取保存按钮
	var saveBtn 	   = Ext.getCmp("saveBtn");
	// 获取返回按钮
	var backBtn 		 = Ext.getCmp("backBtn");
	// 获取订单详情表格
	var ordergrid    = Ext.getCmp("ordergrid");
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
		//if(!ExtConvertHelper.isFormValid("form")) return;

		// 获取（客户联系人信息、客户收货人信息、客户银行信息）表格中的提交数据
		var girdData = HBSConvertHelper.getGridSubmitData("ordergrid", "orderlist");

		// 提交数据
		ExtConvertHelper.submitForm("form", url, girdData, function(form, action) {
			// 获取成功后的提示信息
			var msg = ExtConvertHelper.getMessageInfo(action, "操作成功！");
			if(action && action.result && action.result.data && action.result.data.poNo){
				msg += " 订单编号：" + action.result.data.poNo;
			}
			// 弹出提示框给用户
			var o = new Object();
			if(url.indexOf("!saveTemp") < 0){
				o.commCode = Ext.getCmp("acCommCode").getValue();
				if(action && action.result && action.result.data && action.result.data.poNo)
					o.poNo = action.result.data.poNo;
				else
					o.poNo = urlPs.poNo;
			}
			Ext.Msg.alert("提示", msg, checkPrint, o);
		});
	}

	(function() {
		// 当提交按钮被单击时
		submitBtn.on("click", function() {
			submitData("/vendorOrder/vendorOrder!save.action");
		});

		// 当保存按钮被单击时
		saveBtn.on("click", function() {
			submitData("/vendorOrder/vendorOrder!saveTemp.action");
		});

		// 当单机取消按钮时，调用默认的关闭窗口方法
		backBtn.on("click", HBSConvertHelper.defaultCloseTab);

		Ext.getCmp("sxkhddBtn").on("click", function() {
			var commcode = Ext.getCmp("acCommCode").getValue();
			if(commcode) {
				Ext.getCmp("acVendorCode").setValue(commcode);
				selectWindow.show();
				Ext.getCmp("selectform").buttons[0].fireEvent("click");
			} else {
				Ext.Msg.alert("提示", "请先填写正确的供应商。");
			}
		});

		ordergrid.getSelectionModel().on("beforerowselect", function(sm, rowIndex, keepExisting, record) {
			// 初始化 selectType
			if(Ext.isEmpty(record.selectType)) record.selectType = "none";

			switch(record.get("selectType")) {
				case "window":
					ordergrid.getColumnById("cCpartNo").editable = false;
					ordergrid.getColumnById("cPartNo").editable = false;
					ordergrid.getColumnById("colCustCcode").editable = false;
					break;
				default:
					ordergrid.getColumnById("cCpartNo").editable = true;
					ordergrid.getColumnById("cPartNo").editable = true;
					ordergrid.getColumnById("colCustCcode").editable = true;
					break;
			}
		});

		// 获取表格的列模型
		var cm = ordergrid.getColumnModel();

		// 输入客户P/N或本公司P/N时，自动填写名称、描述、单价、税率
		var orderacfun = function(action){
			if(!action.success)
				return;

			var sm = ordergrid.getSelectionModel();
			//sm.getSelected().set("pnName"   , action.data.vendorPartNoInfo.pnName);
			sm.getSelected().set("pnDesc"   , action.data.vendorPartNoInfo.pnDesc);
			sm.getSelected().set("cprice"   , action.data.vendorPartNoInfo.price);
			sm.getSelected().set("cpriceTax", action.data.vendorPartNoInfo.priceTax);
			sm.getSelected().set("cpartNo"   , action.data.vendorPartNoInfo.custPartNo);
			sm.getSelected().set("partNo", action.data.vendorPartNoInfo.partNo);
		};

		// 获取客户型号控件并加载事件
		cm.getColumnById("cCpartNo").editor.setProcessConfig("/vendorInfo/vendorPartNoInfo!getInfoDict.action", "cpartNo", null, orderacfun);

		// 获取GLE型号控件并加载事件
		cm.getColumnById("cPartNo").editor.setProcessConfig("/vendorInfo/vendorPartNoInfo!getInfoDict.action", "partNo", null, orderacfun);

		//根据税率设置是否含税交易：税率为0时，可选；否则设为是，并不可修改。
		//输入数量时，自动填写金额。
		ordergrid.getSelectionModel().on("beforerowselect", function(sm, rowIndex, keepExisting, record) {
			// 初始化 selectType
			if(Ext.isEmpty(record.get("cpriceTax")) || record.get("cpriceTax") == 0) ordergrid.getColumnById("cisTax").editable = true;
			else ordergrid.getColumnById("cisTax").editable = false;
		});

		ordergrid.getView().on("refresh", function(view) {
			//alert(this.ds.getCount())
			var countAmount = 0;
			var countMoney  = 0;
			for(var i = 0 ; i < view.ds.getCount() ; i++) {
				// 获取数据集
				var record = view.ds.getAt(i);

				//  汇总数量与金额
				countAmount = Math.FloatAdd(countAmount, record.get("amount"));
				countMoney  = Math.FloatAdd(countMoney, record.get("money"));

			}

			Ext.getCmp("countAmount").setValue(countAmount);
			Ext.getCmp("countMoney").setValue(countMoney);
		});

		// 选择收货信息
		var list = Ext.getCmp("acConsigneeList");
		list.store.list = list;
		list.store.on("load", afterListLoad);
		list.selectPrimary = true;
		list.store.load();
	}())

	function afterListLoad(){
		if(!(this && this.list))
			return
		if(this.list.selectPrimary){
			selectPrimary(this.list)
		}else if(this.list.getValue()){
			this.list.fireEvent("select");
		}
	}

	function selectPrimary(list){
		list.selectPrimary = false;
		// DONE:选择主联系人
		if(!list || !list.store)
			return;
		var i = -1;
		if(list.store.getCount() == 1)
			i = 0;
		else
			i = list.store.findExact("isPrimary", "0");
		if(i >= 0){
			list.setValue(list.store.getAt(i).get("conName"));
			list.selectedIndex = i;
		}else
			list.selectedIndex = -1;
		list.fireEvent("select");
	}

	function setCommCode(action){
		if(!action.success)
			return;
		Ext.getCmp("acCompanyBranch").setValue(action.data.vendorInfo.companyBranchDesc);
		Ext.getCmp("acCommCode").setValue(action.data.vendorInfo.commCode);
		Ext.getCmp("acSettlementType").setValue(action.data.vendorInfo.settlementDesc2);
		Ext.getCmp("acCurrency").setValue(action.data.vendorInfo.currencyDesc);
		Ext.getCmp("acShortName").setValue(action.data.vendorInfo.shortName);

		// 给需要的隐藏域赋值
		Ext.getCmp("hidIsShowPrice").setValue(action.data.vendorInfo.isShowPrice);
		Ext.getCmp("hidSettlementType").setValue(action.data.vendorInfo.settlementType);
		Ext.getCmp("hidCompanyBranch").setValue(action.data.vendorInfo.companyBranch);

		var o = action.data.vendorInfo.commCode;
		Ext.getCmp("acVendorCode").setValue(o);
		var list = Ext.getCmp("acContactList");
		list.store.baseParams["vendorInfo.commCode"] = o;
		list.store.baseParams["vendorInfo.state"] = "0";
		list.store.list = list;
		list.store.on("load", afterListLoad);
		if(isManulSelect){
			list.setValue("");
			list.selectPrimary = true;
			list.store.load();
		}else{
			if(list.getValue()){
				list.selectPrimary = false;
				list.store.load();
			}
		}
		/*list = Ext.getCmp("acConsigneeList");
		list.store.baseParams["vendorInfo.commCode"] = o;
		list.store.baseParams["vendorInfo.state"] = "0";
		list.store.list = list;
		list.store.on("load", afterListLoad);
		if(isManulSelect){
			list.setValue("");
			list.selectPrimary = true;
			list.store.load();
		}else{
			if(list.getValue()){
				list.selectPrimary = false;
				list.store.load();
			}
		}*/

		var cm = ordergrid.getColumnModel();
		list = cm.getColumnById("cCpartNo").editor;
		list.store.baseParams["vendorPartNoInfo.commCode"] = o;
		list.store.baseParams["vendorPartNoInfo.state"] = "0";
		list.__processConfig.params["commCode"] = o;
		list.__processConfig.params["vendorPartNoInfo.commCode"] = o;
		list = cm.getColumnById("cPartNo").editor;
		list.store.baseParams["vendorPartNoInfo.commCode"] = o;
		list.store.baseParams["vendorPartNoInfo.state"] = "0";
		list.__processConfig.params["commCode"] = o;
		list.__processConfig.params["vendorPartNoInfo.commCode"] = o;

		isManulSelect = true
	}
	Ext.getCmp("acCommCode").setProcessConfig("/vendorInfo/vendorInfo!getInfo.action?vendorInfo.state=0", "vendorInfo.commCode", null, setCommCode);
	Ext.getCmp("acShortName").setProcessConfig("/vendorInfo/vendorInfo!getInfo.action?vendorInfo.state=0", "vendorInfo.shortName", null, setCommCode);

	Ext.getCmp("acContactList").on("select", function() {
		if(this.selectedIndex < 0){
			var val = this.getValue();
			if(val){
				// 根据val设置selectedIndex
				this.selectedIndex = this.store.findExact("conName", val);
			}
			if(this.selectedIndex < 0){
				Ext.getCmp("acTel").setValue("");
				Ext.getCmp("acTelHidden").setValue("");
				Ext.getCmp("acFax").setValue("");
				Ext.getCmp("acFaxHidden").setValue("");
				return;
			}
		}
		var data = this.store.getAt(this.selectedIndex);
		var o = data.get("conTel");
		Ext.getCmp("acTel").setValue(o);
		Ext.getCmp("acTelHidden").setValue(o);
		o = data.get("conFax");
		Ext.getCmp("acFax").setValue(o);
		Ext.getCmp("acFaxHidden").setValue(o);
	});

	Ext.getCmp("acConsigneeList").on("select", function() {
		if(this.selectedIndex < 0){
			var val = this.getValue();
			if(val){
				// 根据val设置selectedIndex
				this.selectedIndex = this.store.findExact("conName", val);
			}
			if(this.selectedIndex < 0){
				Ext.getCmp("acReceiveName").setValue("");
				Ext.getCmp("acAddress").setValue("");
				Ext.getCmp("acAddressHidden").setValue("");
				Ext.getCmp("acZip").setValue("");
				Ext.getCmp("acZipHidden").setValue("");
				return;
			}
		}
		var data = this.store.getAt(this.selectedIndex);
		var o = data.get("address");
		Ext.getCmp("acAddress").setValue(o);
		Ext.getCmp("acAddressHidden").setValue(o);
		o = data.get("zip");
		Ext.getCmp("acZip").setValue(o);
		Ext.getCmp("acZipHidden").setValue(o);
		o = data.get("conName");
		Ext.getCmp("acReceiveName").setValue(o);

	});

	// -------------------------------------- 页面操作逻辑处理
	function checkPrint(){
		//alert("checkPrint this.poNo=" + this.poNo + " this.commCode=" + this.commCode);
		if(!(this.commCode && this.poNo)){
			submitSuccessPro();
			return;
		}
		Ext.Msg.confirm("是否打印", "是否打印供应商订单？", function(choice){
			//alert("choice=" + choice + " Ext.Msg.YES=" + Ext.Msg.YES);
			if(choice != "yes"){
				submitSuccessPro();
				return;
			}
			var url = [CONTEXT_PATH, "/print/cgdd.jsp?vendorOrder.commCode=", this.commCode, "&vendorOrder.poNo=", this.poNo].join("");
			// DONE: 打开新窗口和关闭当前窗口冲突了。打开新窗口在前的话，新窗口就被关闭了；关闭窗口在前的话，打开新窗口的语句就不执行了。
			// 使用open，不使用HBSConvertHelper.openNewWin。因为打印需要单独窗口。
			// 并且这样的新开窗口不会被submitSuccessPro中的函数关闭。
			open(url);
			submitSuccessPro();
		}, this);
	}

	// 提交操作成功后要做的事情
	var submitSuccessPro;

	// 新增页面的处理逻辑
	function addInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("下供应商订单");

		ExtConvertHelper.setItemsReadOnly("acCommCode", false);
		//Ext.getCmp("acCommCode").readOnly = false;

		// 默认增加 8 条空记录
		for(var i = 0 ; i < 8 ; i++) ordergrid.addData(ordergrid.__getDefData__());

		// 提交完成后的操作
		submitSuccessPro = function() {
			// 用户单击后重载此页面
			location.reload();
		}
	}

	// 修改页面的处理逻辑
	function updateInitFun() {
		// 更改页签标题
		HBSConvertHelper.setDocumentTitle("供应商订单修改");
		// 隐藏不需要的控件
		if(urlPs.state != "01") ExtConvertHelper.hideItems("saveBtn");

		//DONE: readOnly不管用，不能禁止用户输入。disable又变成灰色的，不协调。
		ExtConvertHelper.setItemsReadOnly("acCommCode", true);
		//Ext.getCmp("acCommCode").readOnly = true;
		//Ext.getCmp("acCommCode").disable();

		// 组装需要的参数
		var params = ["vendorOrder.commCode=", urlPs.commCode, "&vendorOrder.poNo=", urlPs.poNo, "&vendorOrder.poNoType=", urlPs.poNoType].join("");

		// 加载数据
		ExtConvertHelper.loadForm("form", "/vendorOrder/vendorOrder!getInfo.action", params, function(form, action) {
				Ext.getCmp("ordergrid").addData(action.result.data.vendorOrder.vendorOrderDetailList);
				isManulSelect = false;
				Ext.getCmp("acCommCode").fireEvent("select");
		});

		// 提交完成后的操作
		submitSuccessPro = function() {
			// 用户单击后关闭此页面
			HBSConvertHelper.defaultCloseTab();
		}
	}

	// 根据不同的操作类型，做出不同的处理
	eval(urlPs.editorType + "InitFun")();


	// -------------------------------------- window 部分功能实现代码
	(function() {
		// 点击取消按钮的事件
		Ext.getCmp("wbackBtn").on("click", function() {
			selectWindow.hide();
		});

		Ext.getCmp("querygrid").getView().on("refresh", function(view) {
			for(var i = 0 ; i < view.ds.getCount() ; i++) {
				var record = view.ds.getAt(i);
				record.set("fromTo", "window");
			}
		});

		// 点击确定按钮的事件
		Ext.getCmp("wokBtn").on("click", function() {
			// 获取查询列表
			var querygrid = Ext.getCmp("querygrid");
			// 获取选择的数据集
			var records = querygrid.getSelectionModel().getSelections();
			// 订单详情需要的字段集
			var ordergridFields = ['operSeqId', 'pnName', 'cpartNo', 'partNo', 'pnDesc', 'cprice', 'cpriceTax', 'isTax', 'amount', 'money', 'orgDeliveryDate', 'specDesc', 'commDesc', 'custCcode', 'hastenReminder', 'selectType', 'fromTo'];
			// 添加标示
			Ext.each(records, function(record) {
				record.set("selectType", "window");
				//DONE:获取客户订单明细的交期(按照ver、pre、org的顺序获取非空时间)，减(VendorDate2CustDate)天，放入orgDeliveryDate
				var s = record.get("verDeliveryDate");
				if(!s)
					s = record.get("preDeliveryDate");
				if(!s)
					s = record.get("orgDeliveryDate");
				if(s){
					var d = new Date(s.replace(/-/g,   "/"));
					var a   =   d.valueOf();
  					a   -=   VendorDate2CustDate   *   24   *   60   *   60   *   1000;
  					d = new Date(a);
  					record.set("orgDeliveryDate", Ext.util.Format.date(d, 'Y-m-d'));
				}

				if(record.get('orgDeliveryDate') instanceof Date)
				{
					record.set('orgDeliveryDate', Ext.util.Format.date(record.get('orgDeliveryDate'), 'Y-m-d'))
				}

				// 将客户订单号显示在第一列
				record.set('custCcode', record.get('poNo'));
			});

			// 添加选择的数据至订单详情表格
			ordergrid.store.add(records);

			// 隐藏 window 控件
			selectWindow.hide();

			var id = 0;
			var f = function(){
				var record;
				while(record = ordergrid.store.getAt(id++)){
					if(record.get("hasStock")){
						// DONE:提示用户该物料有库存，是否添加
						var s = ["本公司P/N：", record.get("partNo"), "，供应商P/N：", record.get("cpartNo"), "，存在可用库存。是否添加？"].join("");
						Ext.Msg.show({
							title:'问题',
							msg: s,
							buttons: Ext.Msg.YESNO,
							fn: function(btn){
								if(btn == "no") {ordergrid.store.remove(record);id --;}
								else if(btn == "cancel") return;
								f();
							},
							icon: Ext.MessageBox.QUESTION
							,closable:false
							,modal:true
						});
						break;
					}
				}
			}
			f();
		});
	}())
});