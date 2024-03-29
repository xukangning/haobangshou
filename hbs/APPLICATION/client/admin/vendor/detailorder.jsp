<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>供应商订单信息查看</title>
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
				    			<layoutpanel columnNum="2">
				    				<label        fieldLabel="供应商"	    name="vendorOrder.shortName"            labelStyle="width:150"/>
				    				<label        fieldLabel="开单日期"     name="vendorOrder.createTime"           labelStyle="width:150"/>
				    				<label        fieldLabel="联系人"       name="vendorOrder.conName"              labelStyle="width:150"/>
				    				<label        fieldLabel="传真"         name="vendorOrder.conFax"               labelStyle="width:150"/>

				    				<label        fieldLabel="订单编号"     name="vendorOrder.poNo"                 labelStyle="width:150"/>
				    				<label        fieldLabel="对应分公司"	name="vendorOrder.companyBranchDesc"	labelStyle="width:150"/>
				    				<label        fieldLabel="联系电话"     name="vendorOrder.conTel"               labelStyle="width:150"/>
				    				<label        fieldLabel="月结方式"     name="vendorOrder.settlementTypeDesc"   labelStyle="width:150"/>
				    				<label        fieldLabel="追货提醒/天"	name="vendorOrder.hastenReminder"	    labelStyle="width:150"/>
				    				<label        fieldLabel="币种"           id="acCurrency" name="vendorOrder.currencyDesc"                        labelStyle="width:150"/>
				    			</layoutpanel>
				    		</listpanel>

				    		<complexgrid id="ordergrid"   title="订单详情" itemsFun="displayordergridFun"   frame="true" height="200" url="1" editorFlag="false"/>

				    		<listpanel>
				    			<layoutpanel columnNum="2:.8,">
				    				<label labelSeparator="" />
				    				<label labelSeparator="" />

				    				<label fieldLabel="汇总数量"   id="countAmount" />
				    				<label fieldLabel="汇总金额"   id="countMoney" />
				    			</layoutpanel>
				    		</listpanel>

			    			<listpanel title="处理信息" frame="true" collapsible="true" titleCollapse="true" id="04process">
			    				<layoutpanel columnNum="1">
				    				<datefield fieldLabel="确认的交期"           name=""	format="Y-m-d" />
				    			</layoutpanel>
				    		</listpanel>
			    		</items></form>

			    		<panel buttonAlign="center">
			    			<buttons>
			    				<button text="提交"         id="submitBtn"    hidden="true"/>
			    				<button text="继续"         id="goonBtn"      hidden="true" url="/vendorOrder/vendorOrder!controlActiveState.action" />
			    				<button text="暂停"         id="stopBtn"      hidden="true" url="/vendorOrder/vendorOrder!controlActiveState.action" />
			    				<button text=""             id="operatorBtn1" hidden="true" />
			    				<button text=""             id="operatorBtn2" hidden="true" />
			    				<button text=""             id="operatorBtn3" hidden="true" />
			    				<button text="打印"         id="printBtn"     hidden="true" />
			    				<button text="取消"         id="backBtn"      />
			    				<button text="查看操作历史" id="historyBtn"   />
			    			</buttons>
			    		</panel>

			    	<!-- service ext ui.  end. -->
			    	</items>
			    </panel>
			  </items>
			</viewport>
			<window id="modifyWindow" title="修改供应商订单明细" width="900" closeAction="hide">
				<items>
					<form id="mwform"><items>
						<listpanel frame="true" title="" collapsible="true" titleCollapse="true">
							<layoutpanel columnNum="2">
								<label fieldLabel="原交期" id="mwDeliveryDate" />
								<datefield fieldLabel="新交期" name="deliveryDate" emptyText="格式：YYYYMMDD"   format="Ymd" />
								<label fieldLabel="原数量" id="mwAmount" />
								<numberfield fieldLabel="新数量" name="amount" emptyText="为0，则不修改" />
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
<script type="text/javascript" src="<%=contextPath %>/vendor/detailorder.js"></script>