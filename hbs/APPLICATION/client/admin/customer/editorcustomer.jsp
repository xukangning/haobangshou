<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title></title>
	<script type="text/javascript" src="<%=contextPath %>/customer/common/CommonPro.js"></script>
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
				    		<listpanel frame="true" title="客户基本信息" collapsible="true" titleCollapse="true">
								<layoutpanel columnNum="2">
				    				<textfield fieldLabel="客户简称"               name="custInfo.shortName"            labelStyle="width:150" allowBlank="false"/>
									<textfield fieldLabel="客户编码"       id ="textCustCode"        name="custInfo.commCode"             labelStyle="width:150"   readOnly="true" />					    				
								</layoutpanel>
								<layoutpanel columnNum="1">
									<textfield fieldLabel="客户全称"           name="custInfo.allName"              labelStyle="width:150" width="600" allowBlank="false"/>
									<textfield fieldLabel="客户公司地址"           name="custInfo.address"              labelStyle="width:150" width="600" allowBlank="false"/>
									<textfield fieldLabel="英文简称"               name="custInfo.enShortName"          labelStyle="width:150" />
									<textfield fieldLabel="公司英文名称"           name="custInfo.enName"               labelStyle="width:150" width="600" />
				    				<textfield fieldLabel="公司英文地址"           name="custInfo.enAddress"            labelStyle="width:150" width="600"/>
									<textfield fieldLabel="客户纳税人识别号"       name="custInfo.taxCode"              labelStyle="width:150" width="600"/>
									<textfield fieldLabel="客户公司网址"           name="custInfo.webSite"              labelStyle="width:150" width="600"/>
								</layoutpanel>
								<layoutpanel columnNum="2">
				    				<textfield fieldLabel="客户法人代表"           name="custInfo.representative"       labelStyle="width:150" />
								</layoutpanel>
								</listpanel>
								<listpanel frame="true" title="客户性质信息" collapsible="true" titleCollapse="true">

								<layoutpanel columnNum="2">
									<dictcombo fieldLabel="企业性质"                   hiddenName="custInfo.commType"             labelStyle="width:150" paramsValue="COMM_TYPE"     emptyText="请选择" allowBlank="false"/>

									<dictcombo fieldLabel="客户的重要程度"         hiddenName="custInfo.importantCode"  labelStyle="width:150" paramsValue="IMPORTANT_CODE"  emptyText="请选择" allowBlank="false"/>
								</layoutpanel>

								<layoutpanel columnNum="2">	
									<dictcombo fieldLabel="企业规模"                   hiddenName="custInfo.commScale"            labelStyle="width:150" paramsValue="SCALE_TYPE"     emptyText="请选择" allowBlank="false"/>
									
									<dictcombo fieldLabel="客户信用度"             hiddenName="custInfo.creditRate"     labelStyle="width:150" paramsValue="CREDIT_RATE"     emptyText="请选择" allowBlank="false"/>
								</layoutpanel>
								<layoutpanel columnNum="2">					    				
									<dictcombo fieldLabel="企业类型"                   hiddenName="custInfo.saleType"            labelStyle="width:150" paramsValue="SALE_TYPE"     emptyText="请选择" allowBlank="false"/>
								</layoutpanel>
								</listpanel>
								<listpanel frame="true" title="客户在公司内部标记信息" collapsible="true" titleCollapse="true">
								<layoutpanel columnNum="2">
				    				<dictcombo fieldLabel="对应的分公司或分支机构" hiddenName="custInfo.companyBranch"  labelStyle="width:150" paramsValue="COMPANY_BRANCH"  emptyText="请选择" allowBlank="false"/>	

				    				<dictcombo fieldLabel="对应的业务部助理"       hiddenName="custInfo.assStaffId"     labelStyle="width:150"  url="/auth/user!listByRoleId.action?roleId=7" root="data.list"  displayField="staffName"  valueField="staffId" allowBlank="false"/>
				    				
				    				
				    				<dictcombo fieldLabel="发货单是否显示单价"     hiddenName="custInfo.isShowPrice"    labelStyle="width:150" paramsValue="IS_SHOW_PRICE"   emptyText="请选择" allowBlank="false"/>
				    			</layoutpanel>
				    			
				    			<layoutpanel columnNum="1">
				    				<textarea fieldLabel="客户备注/企业经营产品"               name="custInfo.commDesc"              labelStyle="width:150" width="600" height="80" />
				    			</layoutpanel>
				    			
				    			<layoutpanel columnNum="1">
				    				<hidden name="custInfo.baseSeqId" />
				    				<hidden name="custInfo.state" />
				    			</layoutpanel>
				    		</listpanel>
				    		
				    		<listpanel frame="true" title="客户结算信息" collapsible="true" titleCollapse="true">
				    			<layoutpanel columnNum="2">
				    				<dictcombo fieldLabel="客户结算类型"           hiddenName="custInfo.settlementType"            labelStyle="width:150" paramsValue="SETTLEMENT_TYPE" emptyText="请选择" id="vSettlementType" allowBlank="false"/>
				    				<!--  账期类型固定为月结
									<dictcombo fieldLabel="账期类型" 			         hiddenName="custInfo.accountPreiod.accountType" labelStyle="width:150" paramsValue="ACCOUNT_TYPE" emptyText="请选择" id="vaAccountType"/>
									-->
				    				<datefield fieldLabel="账期的起始日" 					 name="custInfo.accountPreiod.periodStart"       labelStyle="width:150" id="vaPeriodStart" emptyText="格式：YYYYMMDD" format="Ymd" allowBlank="false"/>
				    				<!--改用下拉列表框选择
									<numberfield fieldLabel="账期的结算日" 					 name="custInfo.accountPreiod.settlementDay"     labelStyle="width:150" id="vaSettlementDay"   emptyText="账期结束的第几日"   relate="vaAccounDay|;&gt;|;必须大于账期的对账日" vtype="commCheck"/>
									-->
				    				<dictcombo fieldLabel="结算方式" 					 hiddenName="custInfo.accountPreiod.settlementDay"     labelStyle="width:150" paramsValue="SETTLEMENT_DAY" id="vaSettlementDay"   allowBlank="false"/>
									<!--
									<numberfield fieldLabel="提醒设置(单位:天)" 							 name="custInfo.accountPreiod.reminderDay"       labelStyle="width:150" id="vaReminderDay"     emptyText="提前几天提醒对账/结算" />
				    				<numberfield fieldLabel="提醒市场人员催款" 			 name="custInfo.prePaidInfo.reminderDay"         labelStyle="width:150" id="vpReminderDay"  emptyText="货到付款催款提醒"/>
									-->
				    				
				    				<dictcombo fieldLabel="客户结算币种"           hiddenName="custInfo.currency"                  labelStyle="width:150" paramsValue="CURRENCY"        emptyText="请选择" allowBlank="false"/>
				    				<!--账期设置固定为1个月
									<textfield fieldLabel="客户的账期设置" 				 name="custInfo.accountPreiod.accountPeriod"     labelStyle="width:150" id="vaAccountPeriod" emptyText="1代表30天结2为60天"/>
									采用缺省值1
				    				<textfield fieldLabel="账期的对账日" 					 name="custInfo.accountPreiod.accounDay"         labelStyle="width:150" id="vaAccounDay"       emptyText="账期结束后的第几日"/>
									-->
				    				<numberfield fieldLabel="账期最大交易金额(元)" name="custInfo.accountPreiod.maxMoney"          labelStyle="width:150" id="vaMaxMoney" allowBlank="false"/>
				    				<numberfield fieldLabel="预付百分比" 						 name="custInfo.prePaidInfo.prePaid"             labelStyle="width:150" id="vpPrePaid" emptyText="如:预付17%,请输入17" allowBlank="false"/>
				    				<numberfield fieldLabel="合同费"                 name="custInfo.contactFee"                      labelStyle="width:150" emptyText="如:合同费2%,请输入0.02" allowBlank="false"/>
				    				<numberfield fieldLabel="交易税率"               name="custInfo.taxRate"                         labelStyle="width:150" emptyText="如:税率17%,请输入0.17" allowBlank="false"/>
				    			</layoutpanel>
				    			
				    			<layoutpanel columnNum="1">
				    				<hidden name="custInfo.accountPreiod.seqId" />
				    				<hidden name="custInfo.prePaidInfo.seqId" />
				    			</layoutpanel>
				    		</listpanel>
			    		</items></form>
			    		
			    		<complexgrid id="contactgrid" title="客户联系人信息" itemsFun="contactFun" frame="true" height="200" deftbar="true" url="1">
			    			<fields>
			    				<field name="isPrimary" />
			    			</fields>
			    			
			    			<columns>
			    				<column header="是否是主联系人" dataIndex="isPrimary" xtype="dictcombo" paramsValue="IS_PRIMARY" />
			    			</columns>
			    			
			    			<submitFields value="seqId,conName,conDuty,conTel,conMobile,conFax,conMail,conQq,conMsn,conOther,isPrimary" />
			    		</complexgrid>
			    		
			    		<complexgrid id="consigneegrid" title="客户收货人信息" itemsFun="contactFun" frame="true" height="200" deftbar="true" url="1">
			    			<fields>
			    				<field name="conAddress" />
			    				<field name="conZip" />
			    				<field name="isPrimary" />
			    			</fields>
			    			
			    			<columns>
			    				<column header="是否是主收货人" dataIndex="isPrimary"  xtype="dictcombo" paramsValue="IS_PRIMARY" />
			    				<column header="收货地址" 			dataIndex="conAddress" xtype="textfield" />
			    				<column header="收货邮编" 			dataIndex="conZip"     xtype="textfield" />
			    			</columns>
			    			
			    			<submitFields value="seqId,conName,conDuty,conTel,conMobile,conFax,conMail,conQq,conMsn,conOther,conAddress,conZip,isPrimary" />
			    		</complexgrid>
			    		
			    		<complexgrid id="custbankgrid" title="客户银行信息" itemsFun="custbankFun" frame="true" height="200" deftbar="true" url="1" />
			    		
			    		<panel buttonAlign="center">
			    			<buttons>
			    				<button text="提交审批" id="submitBtn" />
			    				<button text="暂存" id="saveBtn"   />
			    				<button text="取消" id="backBtn"   />
			    			</buttons>
			    		</panel>
			    		
			    	<!-- service ext ui.  end. -->
			    	</items>
			    </panel>
			  </items>
			</viewport>
		</application>
	</xmp>
</body>
</html>
<script type="text/javascript" src="<%=contextPath %>/customer/editorcustomer.js"></script>