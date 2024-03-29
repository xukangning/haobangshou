<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商发票信息查看</title>
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
				    		<listpanel frame="true" title="发票基本信息" collapsible="true" titleCollapse="true">
				    			<layoutpanel columnNum="2">
				    				<label        fieldLabel="供应商编码"           name="invoice.ccode"                    labelStyle="width:150"/>
				    				<label        fieldLabel="操作人"               name="invoice.staffName"                    labelStyle="width:150"/>
				    				<label        fieldLabel="入库单号"             name="invoice.poNo"                    labelStyle="width:150"/>
				    				<label        fieldLabel="本公司物料编码"       name="invoice.partNo"                    labelStyle="width:150"/>
				    					
				    				<label        fieldLabel="供应商简称"           name="invoice.shortName"                    labelStyle="width:150"/>
				    				<label        fieldLabel="操作日期"             name="invoice.createTime"                    labelStyle="width:150"/>
				    				<label        fieldLabel="送货日期"             name="invoice.poNoDate"                    labelStyle="width:150"/>
				    				<label        fieldLabel="供应商物料编码"       name="invoice.cpartNo"                    labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<label        fieldLabel="物料描述"             name="invoice.pnDesc"                    labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="2">
				    				<label        fieldLabel="送货数量"             name="invoice.amount"                    labelStyle="width:150"/>
				    				<label        fieldLabel="本次开票金额"         name="invoice.currMoney"                    labelStyle="width:150"/>
				    					
				    				<label        fieldLabel="总金额"               name="invoice.allMoney"                    labelStyle="width:150"/>
				    				<label        fieldLabel="剩余开票金额"         name="invoice.leftMoney"                    labelStyle="width:150"/>
				    			</layoutpanel>
				    			<layoutpanel columnNum="1">
				    				<label        fieldLabel="发票描述"             name="invoice.invoiceDesc"                      labelStyle="width:150" width="600" />
				    			</layoutpanel>
				    		</listpanel>
			    		</items></form>
			    		
			    		<panel buttonAlign="center">
			    			<buttons>
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
<script type="text/javascript" src="<%=contextPath %>/invoice/detailvendorinvoiced.js"></script>