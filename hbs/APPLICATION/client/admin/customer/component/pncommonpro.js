var queryformFun = function() {
	var cph = new ColumnPanelHelper;
		
	var p1 = cph.createLayoutPanel(3);
	
	p1.push({xtype: "textfield", fieldLabel: "客户简称"     , name: "custPartNoInfo.shortName"});
	p1.push({xtype: "textfield", fieldLabel: "客户编码"     , name: "custPartNoInfo.commCode"});
	if(urlPs.pageType != "audit") p1.push({xtype: "dictcombo", fieldLabel: "状态"         , hiddenName: "custPartNoInfo.state", paramsValue: "CUSTOMER_INFO_STATE"});
	p1.push({xtype: "textfield", fieldLabel: "客户P/N"      , name: "custPartNoInfo.custPartNo"});
	p1.push({xtype: "textfield", fieldLabel: "本公司P/N"    , name: "custPartNoInfo.partNo"});
	
	return cph;
};

var complexgridFun = function() {
	var cgh = new ComplexGridHelper;
	
	cgh.appendField("shortName");
	cgh.appendField("commCode");
	cgh.appendField("custPartNo");
	cgh.appendField("partNo");
	cgh.appendField("pnDesc");
	cgh.appendField("importantDesc");
	cgh.appendField("price");
	cgh.appendField("priceTax");
	cgh.appendField("minAmount");
	cgh.appendField("sampleCode");
	cgh.appendField("stateDesc");
	cgh.appendField("vendorCode");
	cgh.appendField("seqId");
	cgh.appendField("state");
			    				
	cgh.appendColumn({dataIndex: "seqId"	, isCheck: true});
	cgh.appendColumn({header: "客户简称"	, dataIndex: "shortName"});
	cgh.appendColumn({header: "客户编码"	, dataIndex: "commCode", id: "commCode"});	
	cgh.appendColumn({header: "客户P/N"		, dataIndex: "custPartNo", id : "custPartNo"});
	cgh.appendColumn({header: "本公司P/N"	, dataIndex: "partNo"});
	cgh.appendColumn({header: "描述"			, dataIndex: "pnDesc"});
	cgh.appendColumn({header: "单价"	    , dataIndex: "price"});
	cgh.appendColumn({header: "税率"			, dataIndex: "priceTax"});
	cgh.appendColumn({header: "最小订单量"	, dataIndex: "minAmount"});
	cgh.appendColumn({header: "样品编码"	, dataIndex: "sampleCode"});
	cgh.appendColumn({header: "供应商编码"	, dataIndex: "vendorCode"});
	cgh.appendColumn({header: "状态"			, dataIndex: "stateDesc"});
	var operatorCell = {header: "操作"			, dataIndex: ""              , id: "operator"};
	if(urlPs.pageType != "audit") operatorCell.width = 250;
	cgh.appendColumn(operatorCell);
	
	return cgh;
};