﻿var amountRenderer = function(value, metadata, record) {
	if(value) {
		try{
			record.set("money", Math.FloatMul(value, record.get("cprice")));
		} catch(e) {}
	}
	return value;
}

var cpriceTaxRenderer = function(value, metadata, record, rowIndex, colIndex, store) {
	if(value == "") return value;
	if(value > 0) {
		record.set("isTax", "1");
		var _grid = Ext.getCmp(store.gridId);
		_grid.getColumnById("cisTax").editable = false;
	}

	return value;
}

var ordergridFun = function() {
	var cgh = new ComplexGridHelper;

	cgh.appendField("operSeqId");
	cgh.appendField("pnName");
	cgh.appendField("cpartNo");
	cgh.appendField("partNo");
	cgh.appendField("pnDesc");
	cgh.appendField("cprice");
	cgh.appendField("cpriceTax");
	cgh.appendField("isTax");
	cgh.appendField("amount");
	cgh.appendField("money");
	cgh.appendField("orgDeliveryDate");
	cgh.appendField("specDesc");
	cgh.appendField("commDesc");
	cgh.appendField("state");
	cgh.appendField("stateDesc");

	cgh.appendColumn({dataIndex: "operSeqId"	, isCheck: true});
//	cgh.appendColumn({header: "货品名称"	, dataIndex: "pnName"});
	cgh.appendColumn({header: "客户型号<font color=red>*</font>"	, dataIndex: "cpartNo", xtype: "autocomplete", id: "cCpartNo", queryParam: "custPartNoInfo.custPartNo" ,displayField:"custPartNo" , valueField:"custPartNo", url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "GLE型号<font color=red>*</font>"		, dataIndex: "partNo", xtype: "autocomplete", id : "cPartNo", queryParam: "custPartNoInfo.partNo"  ,displayField:"partNo" , valueField:"partNo" , url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "描述"	, dataIndex: "pnDesc"});
	cgh.appendColumn({header: "单价"			, dataIndex: "cprice"});
	cgh.appendColumn({header: "税率"	    , dataIndex: "cpriceTax", renderer: "cpriceTaxRenderer"});
	cgh.appendColumn({header: "是否含税交易<font color=red>*</font>", dataIndex: "isTax" , xtype: "dictcombo"  , paramsValue: "IS_TAX_DEALER", id: "cisTax"});
	cgh.appendColumn({header: "数量<font color=red>*</font>"	      , dataIndex: "amount", xtype: "numberfield", renderer: "amountRenderer"});
	cgh.appendColumn({header: "金额", dataIndex: "money"});
	cgh.appendColumn({header: "交货日期<font color=red>*</font>", dataIndex: "orgDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "特殊备注<font color=red>*</font>"		, dataIndex: "specDesc", xtype: "textfield"});
	cgh.appendColumn({header: "备注<font color=red>*</font>"		, dataIndex: "commDesc", xtype: "textfield"});
	cgh.appendColumn({header: "状态"			, dataIndex: "stateDesc"});
	cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator", width: 250});
	/*
	switch(urlPs.state) {
		case "20":
		case "21":
			cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator"});
			break;
	}
	*/

	cgh.setSubmitFields("operSeqId,pnName,cpartNo,partNo,pnDesc,cprice,cpriceTax,isTax,amount,money,orgDeliveryDate,specDesc,commDesc");
	return cgh;
};

var displayordergridFun = function() {
	var cgh = new ComplexGridHelper;

	cgh.appendField("operSeqId");
	cgh.appendField("pnName");
	cgh.appendField("cpartNo");
	cgh.appendField("partNo");
	cgh.appendField("pnDesc");
	cgh.appendField("cprice");
	cgh.appendField("cpriceTax");
	cgh.appendField("isTax");
	cgh.appendField("amount");
	cgh.appendField("money");
	cgh.appendField("orgDeliveryDate");
	cgh.appendField("preDeliveryDate");
	cgh.appendField("verDeliveryDate");
	cgh.appendField("specDesc");
	cgh.appendField("commDesc");
	cgh.appendField("state");
	cgh.appendField("stateDesc");
	cgh.appendField("lockAmount");
	cgh.appendField("deliveryAmount");
	cgh.appendField("orderAmount");
	cgh.appendField("vendorCode");
	cgh.appendField("rltOrderPoNo");

	cgh.appendColumn({dataIndex: "operSeqId"	, isCheck: true});
//	cgh.appendColumn({header: "货品名称"	, dataIndex: "pnName"});
	cgh.appendColumn({header: "客户型号<font color=red>*</font>"	, dataIndex: "cpartNo", xtype: "autocomplete", id: "cCpartNo", queryParam: "custPartNoInfo.custPartNo" ,displayField:"custPartNo" , valueField:"custPartNo", url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "GLE型号<font color=red>*</font>"		, dataIndex: "partNo", xtype: "autocomplete", id : "cPartNo", queryParam: "custPartNoInfo.partNo"  ,displayField:"partNo" , valueField:"partNo" , url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "描述"	, dataIndex: "pnDesc"});
	cgh.appendColumn({header: "单价"			, dataIndex: "cprice"});
	cgh.appendColumn({header: "税率"	    , dataIndex: "cpriceTax", renderer: "cpriceTaxRenderer"});
	cgh.appendColumn({header: "是否含税交易<font color=red>*</font>", dataIndex: "isTax" , xtype: "dictcombo"  , paramsValue: "IS_TAX_DEALER", id: "cisTax"});
	cgh.appendColumn({header: "数量<font color=red>*</font>"	      , dataIndex: "amount", xtype: "numberfield"});
	cgh.appendColumn({header: "金额", dataIndex: "money"});
	cgh.appendColumn({header: "原始交货日期<font color=red>*</font>", dataIndex: "orgDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "交货日期<font color=red>*</font>", dataIndex: "preDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "最终交货日期<font color=red>*</font>", dataIndex: "verDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "特殊备注<font color=red>*</font>"		, dataIndex: "specDesc", xtype: "textfield"});
	cgh.appendColumn({header: "备注<font color=red>*</font>"		, dataIndex: "commDesc", xtype: "textfield"});
	cgh.appendColumn({header: "锁定数量"			, dataIndex: "lockAmount"});
	cgh.appendColumn({header: "已发货数量"			, dataIndex: "deliveryAmount"});
	cgh.appendColumn({header: "已下订单数量"	, dataIndex: "orderAmount"});
	cgh.appendColumn({header: "供应商编码"		, dataIndex: "vendorCode"});
	cgh.appendColumn({header: "关联订单号"		, dataIndex: "rltOrderPoNo"});
	cgh.appendColumn({header: "状态"			, dataIndex: "stateDesc"});
	cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator", width: 250});
	/*
	switch(urlPs.state) {
		case "20":
		case "21":
			cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator"});
			break;
	}
	*/

	cgh.setSubmitFields("operSeqId,pnName,cpartNo,partNo,pnDesc,cprice,cpriceTax,isTax,amount,money,orgDeliveryDate,specDesc,commDesc");
	return cgh;
};

var displayorderCggridFun = function() {
	var cgh = new ComplexGridHelper;

	cgh.appendField("operSeqId");
	cgh.appendField("pnName");
	cgh.appendField("partNo");
	cgh.appendField("pnDesc");
	cgh.appendField("orgDeliveryDate");
	cgh.appendField("preDeliveryDate");
	cgh.appendField("verDeliveryDate");
	cgh.appendField("specDesc");
	cgh.appendField("commDesc");
	cgh.appendField("state");
	cgh.appendField("stateDesc");
	cgh.appendField("lockAmount");
	cgh.appendField("deliveryAmount");
	cgh.appendField("orderAmount");
	cgh.appendField("vendorCode");
	cgh.appendField("rltOrderPoNo");

	cgh.appendColumn({dataIndex: "operSeqId"	, isCheck: true});
//	cgh.appendColumn({header: "货品名称"	, dataIndex: "pnName"});
//	cgh.appendColumn({header: "客户型号<font color=red>*</font>"	, dataIndex: "cpartNo", xtype: "autocomplete", id: "cCpartNo", queryParam: "custPartNoInfo.custPartNo" ,displayField:"custPartNo" , valueField:"custPartNo", url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "GLE型号<font color=red>*</font>"		, dataIndex: "partNo", xtype: "autocomplete", id : "cPartNo", queryParam: "custPartNoInfo.partNo"  ,displayField:"partNo" , valueField:"partNo" , url: "/customerInfo/custPartNoInfo!list.action"});
	cgh.appendColumn({header: "描述"	, dataIndex: "pnDesc"});
/*	cgh.appendColumn({header: "单价"			, dataIndex: "cprice"});
	cgh.appendColumn({header: "税率"	    , dataIndex: "cpriceTax", renderer: "cpriceTaxRenderer"});
	cgh.appendColumn({header: "是否含税交易<font color=red>*</font>", dataIndex: "isTax" , xtype: "dictcombo"  , paramsValue: "IS_TAX_DEALER", id: "cisTax"});
	cgh.appendColumn({header: "金额", dataIndex: "money"});
*/
	cgh.appendColumn({header: "数量<font color=red>*</font>"	      , dataIndex: "amount", xtype: "numberfield", renderer: "amountRenderer"});
	cgh.appendColumn({header: "原始交货日期<font color=red>*</font>", dataIndex: "orgDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "交货日期<font color=red>*</font>", dataIndex: "preDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "最终交货日期<font color=red>*</font>", dataIndex: "verDeliveryDate", xtype: "datefield", format: "Y-m-d", renderer: FormatUtil.dateRenderer});
	cgh.appendColumn({header: "特殊备注<font color=red>*</font>"		, dataIndex: "specDesc", xtype: "textfield"});
	cgh.appendColumn({header: "备注<font color=red>*</font>"		, dataIndex: "commDesc", xtype: "textfield"});
	cgh.appendColumn({header: "锁定数量"			, dataIndex: "lockAmount"});
	cgh.appendColumn({header: "已发货数量"			, dataIndex: "deliveryAmount"});
	cgh.appendColumn({header: "已下订单数量"	, dataIndex: "orderAmount"});
	cgh.appendColumn({header: "供应商编码"		, dataIndex: "vendorCode"});
	cgh.appendColumn({header: "关联订单号"		, dataIndex: "rltOrderPoNo"});
	cgh.appendColumn({header: "状态"			, dataIndex: "stateDesc"});
	cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator", width: 250});
	/*
	switch(urlPs.state) {
		case "20":
		case "21":
			cgh.appendColumn({header: "操作"			, dataIndex: ""         , id: "operator"});
			break;
	}
	*/

	cgh.setSubmitFields("operSeqId,pnName,cpartNo,partNo,pnDesc,cprice,cpriceTax,isTax,amount,money,orgDeliveryDate,specDesc,commDesc");
	return cgh;
};