<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>客户订单信息查看</title>
	<script type="text/javascript" src="<%=contextPath %>/customer/component/ordercommonpro.js"></script>
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
				    			<layoutpanel columnNum="2">
				    				<label fieldLabel="客户编码"	         name="custOrder.commCode"         labelStyle="width:150" id="acCommCode" />
				    				<label fieldLabel="客户订单号"         name="custOrder.poNo"             labelStyle="width:150"/>
				    				<label fieldLabel="对应分公司"         name="custOrder.companyBranchDesc"  labelStyle="width:150"/>
				    				<label        fieldLabel="币种"           id="acCurrency" name="custOrder.currencyDesc"                        labelStyle="width:150"/>


				    				<label fieldLabel="客户简称"           name="custOrder.shortName"        labelStyle="width:150"/>
				    				<label fieldLabel="客户订单日期"       name="custOrder.oderTime" 	       labelStyle="width:150"/>
				    				<label fieldLabel="结算类型"           name="custOrder.settlementTypeDesc" labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel id="contact1" columnNum="1">
				    				<label fieldLabel="选择联系人"	       name="custOrder.conName"          labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel id="contact2" columnNum="2">
				    				<label fieldLabel="电话"               name="custOrder.conTel"	         labelStyle="width:150"/>
				    				<label fieldLabel="传真"               name="custOrder.conFax"	         labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel id="contact3" columnNum="1">
				    				<label fieldLabel="选择收货人"	       name="custOrder.receiveName"      labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel id="contact4" columnNum="2">
				    				<label fieldLabel="收货地址"           name="custOrder.receiveAddress"	 labelStyle="width:150"/>
				    				<label fieldLabel="邮编"               name="custOrder.receiveZip"	     labelStyle="width:150"/>
				    			</layoutpanel>
				    		</listpanel>

				    		<complexgrid id="custbankgrid"  title="订单详情" itemsFun="displayordergridFun" frame="true" height="320" url="1" editorFlag="false"/>

				    		<listpanel>
				    			<layoutpanel columnNum="2:.8,">
				    				<label labelSeparator="" />
				    				<label labelSeparator="" />

				    				<label fieldLabel="汇总数量"   id="countAmount" />
				    				<label fieldLabel="汇总金额"   id="countMoney" />
				    			</layoutpanel>
				    		</listpanel>

				    		<auditpanel id="auditPanel" />
			    		</items></form>

			    		<panel buttonAlign="center">
			    			<buttons>
			    				<button text="提交"         id="submitBtn"    hidden="true"/>
			    				<button text="继续"         id="goonBtn"      hidden="true" url="/custOrder/custOrder!controlActiveState.action" />
			    				<button text="暂停"         id="stopBtn"      hidden="true" url="/custOrder/custOrder!controlActiveState.action" />
			    				<button text=""             id="operatorBtn1" hidden="true" />
			    				<button text=""             id="operatorBtn2" hidden="true" />
			    				<button text=""             id="operatorBtn3" hidden="true" />
			    				<button text="取消"         id="backBtn"      />
			    				<button text="查看操作历史" id="historyBtn"   />
			    			</buttons>
			    		</panel>

			    	<!-- service ext ui.  end. -->
			    	</items>
			    </panel>
			  </items>
			</viewport>
			<window id="modifyWindow" title="修改客户订单明细" width="900" closeAction="hide">
				<items>
					<form id="mwform"><items>
						<listpanel frame="true" title="" collapsible="true" titleCollapse="true">
							<layoutpanel columnNum="2">
								<label fieldLabel="原交期" id="mwDeliveryDate" />
								<datefield fieldLabel="新交期" name="deliveryDate" emptyText="格式：YYYYMMDD"   format="Ymd" />
								<label fieldLabel="原数量" id="mwAmount" />
								<numberfield fieldLabel="新数量" name="amount" emptyText="为0，则不修改" />
							</layoutpanel>
							<layoutpanel columnNum="2">
								<autocomplete fieldLabel="供应商编码" id="mwVendorCode" name="vendorCode" url="/vendorInfo/vendorInfoMgr!list.action"  displayField="commCode"  valueField="commCode" queryParam="vendorInfo.commCode" />
								<autocomplete fieldLabel="供应商简称" id="mwVendorName"  url="/vendorInfo/vendorInfoMgr!list.action"  displayField="shortName"  valueField="shortName" queryParam="vendorInfo.shortName" />
							</layoutpanel>
							<layoutpanel columnNum="1">
								<textarea fieldLabel="说明" name="memo" width="600" height="80" />
							</layoutpanel>
							<layoutpanel columnNum="1">
								<hidden name="orderDetail.operSeqId" id="mwOperSeqId" />
							</layoutpanel>
						</listpanel>
					</items></form>
					<panel buttonAlign="center">
		    			<buttons>
		    				<button text="提交" id="mwSubmitBtn" />
		    				<button text="取消" id="mwBackBtn"   />
		    			</buttons>
		    		</panel>
				</items>
			</window>
		</application>
	</xmp>
</body>
</html>
<script type="text/javascript" src="<%=contextPath %>/customer/detailorder.js"></script>