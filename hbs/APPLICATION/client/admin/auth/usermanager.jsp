<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/includejs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<script type="text/javascript" src="<%=contextPath %>/customer/common/CommonPro.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/customer/component/querycustomer.js"></script>
</head>

<body>
	<xmp id="config" style="display:none">
		<application>
			<viewport layout="fit">
			  <items>
			    <panel frame="true" autoScroll="true">
			    	<items>
			    	<!-- service ext ui.  begin. -->
			    		<panel>
			    			<buttons>
			    				<button text="新增" id="addBtn" />
			    			</buttons>
			    		</panel>
			    		
			    		<queryform gridId="querygrid">
			    			<layoutpanel columnNum="3">
			    				<textfield fieldLabel="姓名"                   name="staff.staffName"       />
			    				<textfield fieldLabel="职务"                   name="staff.duty"              />
			    				<textfield fieldLabel="帐号"                   name="staff.dynamicFields.account"             />
			    			</layoutpanel>
			    		</queryform>
			    		
				    	<complexgrid id="querygrid" title="用户列表" frame="true" page="true" root="data.list" url="/auth/user!list.action">
				    		<fields>
			    				<field name="staffName"	/>
			    				<field name="dynamicFields.account" />
			    				<field name="gender"	/>
			    				<field name="duty"	/>
			    				<field name="mobile"	/>
			    				<field name="staffId" />
			    			</fields>
			    			
			    			<columns>
			    				<column header="姓名"     dataIndex="staffName" id="staffName" />
			    				<column header="帐号"     dataIndex="dynamicFields.account"      />
			    				<column header="性别"     dataIndex="gender"    />
			    				<column header="职务"     dataIndex="duty"      />
			    				<column header="手机"     dataIndex="mobile"    />
			    				<column header="操作"			dataIndex=""          id="operator" width="150" />
			    			</columns>
				    	</complexgrid>
			    		
			    	<!-- service ext ui.  end. -->
			    	</items>
			    </panel>
			  </items>
			</viewport>
		</application>
	</xmp>
</body>
</html>
<script type="text/javascript" src="<%=contextPath %>/auth/usermanager.js"></script>