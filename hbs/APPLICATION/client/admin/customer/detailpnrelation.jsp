<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>客户P/N对照查看</title>
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
				    			<layoutpanel columnNum="1">
				    				<hidden name="custPartNoInfo.seqId" />
				    				<hidden name="custPartNoInfo.state" />
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<label fieldLabel="客户编码"           name="custPartNoInfo.commCode"                id="commCode"      labelStyle="width:150" allowBlank="false"/>
				    			</layoutpanel>
				    			
				    			<layoutpanel columnNum="2">
				    				<label fieldLabel="客户简称"           name="custInfo.shortName"                     labelStyle="width:150" allowBlank="false"/>
				    				<label fieldLabel="客户结算币种"       name="custInfo.currencyDesc"                  labelStyle="width:150" paramsValue="CURRENCY"        emptyText="请选择" />
				    			</layoutpanel>
				    		</listpanel>
				    		
				    		<listpanel frame="true" title="P/N对照信息" collapsible="true" titleCollapse="true">
				    			<layoutpanel columnNum="2">
				    				<label fieldLabel="客户"               name="custPartNoInfo.custPartNo"              id="custPartNo"             labelStyle="width:150" allowBlank="false"/>
				    				<label fieldLabel="本公司"             name="custPartNoInfo.partNo"                  id="partNo"        labelStyle="width:150" allowBlank="false"/>
				    			</layoutpanel>
				    			
				    			<layoutpanel columnNum="1">
				    				<label fieldLabel="描述"                   name="custPartNoInfo.pnDesc"                  labelStyle="width:150" allowBlank="false"/>
				    			</layoutpanel>
				    		</listpanel>
				    		
				    		<listpanel frame="true" title="关联信息" collapsible="true" titleCollapse="true">
				    			<layoutpanel columnNum="2">
				    				<label fieldLabel="单价"               name="custPartNoInfo.price"                   labelStyle="width:150" />
				    				<!--
									<label fieldLabel="最小包装" 			     name=""                        labelStyle="width:150" value="1" />
									-->
				    				<label fieldLabel="样品编码" 					 name="custPartNoInfo.sampleCode"              labelStyle="width:150" />
				    				
				    				<label fieldLabel="税率"               name="custPartNoInfo.priceTax"                labelStyle="width:150" />				    				
				    				<label fieldLabel="最小订单量" 				 name="custPartNoInfo.minAmount"               labelStyle="width:150" value="0" />
				    			</layoutpanel>
								<layoutpanel columnNum="1">
				    				<label style="color:red" text="注意：单价和税率的关系，税率为0.00，单价为不含税，税率不为0.00，单价为含税" />
				    			</layoutpanel>
				    		</listpanel>
				    		
				    		<auditpanel id="auditPanel" />
				    			
			    		</items></form>
			    		
			    		<panel buttonAlign="center">
			    			<buttons>
			    				<button text="提交" id="submitBtn" />
			    				<button text="取消" id="backBtn"   />
			    				<button text="历史变更查看" id="historyBtn"   />
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
<script type="text/javascript" src="<%=contextPath %>/customer/detailpnrelation.js"></script>