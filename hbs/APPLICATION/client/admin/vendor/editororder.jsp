<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<script type="text/javascript" src="<%=contextPath %>/vendor/component/ordercommonpro.js"></script>
</head>

<body>
	<xmp id="config" style="display:none">
		<application>
			<viewport layout="fit">
			  <items>
			    <panel frame="true" autoScroll="true">
			    	<items>
			    	<!-- service ext ui.  begin. -->

							<form id="form"><items>
				    		<listpanel frame="true" title="订单基本信息" collapsible="true" titleCollapse="true">
				    			<layoutpanel columnNum="4">
				    				<autocomplete    fieldLabel="供应商编码"	url="/vendorInfo/vendorInfo!list.action"  displayField="commCode"  valueField="commCode" queryParam="vendorInfo.commCode" id="acCommCode"          name="vendorOrder.commCode"  />
				    				<autocomplete    fieldLabel="供应商简称"	url="/vendorInfo/vendorInfo!list.action"  displayField="shortName"  valueField="shortName" queryParam="vendorInfo.shortName" id="acShortName"          name="vendorOrder.shortName"    />
				    				<button       text="查询客户订单" id="sxkhddBtn" />
				    				<textfield    fieldLabel="订单编号" id="acPoNo"        name="vendorOrder.poNo"    enable="false"      readOnly="true"  />
				    			</layoutpanel>
				    			<layoutpanel columnNum="2">
				    				<textfield        fieldLabel="开单日期" name="vendorOrder.createTime" id="acCreateTime"   enable="false"      readOnly="true"                                labelStyle="width:150"/>
				    				<label        fieldLabel="对应分公司" id="acCompanyBranch"                              labelStyle="width:150"/>
				    				<label        fieldLabel="结算方式" id="acSettlementType"                                labelStyle="width:150"/>
				    				<textfield	fieldLabel="追货提醒/天"	name="vendorOrder.hastenReminder"	labelStyle="width:150"/>
				    				<label        fieldLabel="币种"           id="acCurrency" name="vendorOrder.currencyDesc"                        labelStyle="width:150"/>
<!--
				    				<label        fieldLabel="联系人" id="acConName"                                  labelStyle="width:150"/>
				    				<label        fieldLabel="传真" id="acConFax"                                    labelStyle="width:150"/>
				    				<label        fieldLabel="联系电话" id="acConTel"                                labelStyle="width:150"/>
-->
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<dictcombo    fieldLabel="选择联系人"	id="acContactList"	url="/vendorInfo/vendorInfo!getContactList.action" record="seqId,conName,conTel,conFax,isPrimary"	root="data.list"	valueField="conName"	displayField="conName"	name="vendorOrder.conName"                        labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="2">
				    				<label        fieldLabel="电话"               name="vendorOrder.conTel"	id="acTel"                        labelStyle="width:150"/>
				    				<label        fieldLabel="传真"               name="vendorOrder.conFax"	id="acFax"                       labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<dictcombo    fieldLabel="入库仓库"	id="acConsigneeList"	url="/warehouse/warehouseAddr!list.action" record="id,name,conName,address,zip,isPrimary"	root="data.list"	valueField="conName"	displayField="name"	hiddenName="vendorOrder.receiveName"                       labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="3:.25,.5,.25">
				    				<label        fieldLabel="收货人"             name="vendorOrder.receiveName"	id="acReceiveName"                        labelStyle="width:150"/>
				    				<label        fieldLabel="收货地址"           name="vendorOrder.receiveAddress"	id="acAddress"                     labelStyle="width:150"/>
				    				<label        fieldLabel="邮编"               name="vendorOrder.receiveZip"	id="acZip"                        labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<hidden name="vendorOrder.isShowPrice" id="hidIsShowPrice" />
				    				<hidden name="vendorOrder.settlementType" id="hidSettlementType" />
				    				<hidden name="vendorOrder.companyBranch" id="hidCompanyBranch" />
				    				<hidden name="vendorOrder.conTel"	id="acTelHidden" />
				    				<hidden name="vendorOrder.conFax"	id="acFaxHidden" />
				    				<hidden name="vendorOrder.receiveAddress"	id="acAddressHidden" />
				    				<hidden name="vendorOrder.receiveZip"	id="acZipHidden" />
				    				<hidden name="vendorOrder.state" />
				    				<hidden name="vendorOrder.poNoType" />
				    				<hidden name="vendorOrder.activeState" />
				    				<hidden name="vendorOrder.period" />
				    			</layoutpanel>
				    		</listpanel>
			    		</items></form>

			    		<complexgrid id="ordergrid" frame="true" height="300" deftbar="true" url="1" title="订单详情" itemsFun="ordergridFun" />

							<listpanel>
				    			<layoutpanel columnNum="2:.8,">
				    				<label labelSeparator="" />
				    				<label labelSeparator="" />

				    				<label fieldLabel="汇总数量"   id="countAmount" />
				    				<label fieldLabel="汇总金额"   id="countMoney" />
				    			</layoutpanel>
				    	</listpanel>

			    		<panel buttonAlign="center">
			    			<buttons>
			    				<button text="提交" id="submitBtn" />
			    				<button text="暂存" id="saveBtn"   />
			    				<button text="取消" id="backBtn"   />
			    			</buttons>
			    		</panel>

			    	<!-- service ext ui.  end. -->
			    	</items>
			    </panel>
			  </items>
			</viewport>

			<window id="selectWindow" title="查询客户订单" width="900" closeAction="hide">
				<items>
					<queryform gridId="querygrid" id="selectform">
					<layoutpanel columnNum="1">
						<hidden name="orderDetail.vendorCode" id="acVendorCode" />
						<hidden name="orderDetail.poNoType" value="0" />
					</layoutpanel>
	    			<layoutpanel columnNum="3">
	    				<textfield fieldLabel="客户编码"           name="orderDetail.commCode"  />
	    				<textfield fieldLabel="供应商 P/N"           name="orderDetail.cpartNo" />
	    				<textfield fieldLabel="客户订单号"         name="orderDetail.poNo"  />

	    				<textfield fieldLabel="本公司 P/N"         name="orderDetail.partNo" />
	    				<textfield fieldLabel="特殊备注"           name="orderDetail.specDesc" />
	    			</layoutpanel>
	    		</queryform>

	    		<complexgrid id="querygrid" buttonAlign="center" title="订单详情" itemsFun="orderquerygridFun"  frame="true" height="300" url="/custOrderDetail/orderDetailCg!listStockupByVendor.action" page="true" root="data.list">
	    			<buttons>
	    				<button text="确定" id="wokBtn" />
	    				<button text="取消" id="wbackBtn"   />
	    			</buttons>
	    		</complexgrid>
				</items>
			</window>
		</application>
	</xmp>
</body>
</html>
<script type="text/javascript" src="<%=contextPath %>/vendor/editororder.js"></script>