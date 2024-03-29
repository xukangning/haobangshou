/**
 * 
 */
package com.hbs.customerinfo.action;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hbs.common.action.base.BaseAction;
import com.hbs.customerinfo.manager.CustPartNoInfoMgr;
import com.hbs.customerinfo.manager.CustomerInfoMgr;
import com.hbs.domain.customer.customerinfo.pojo.CustPartNoInfo;
import com.hbs.domain.customer.customerinfo.pojo.CustomerInfo;

/**
 * 客户物料关系经理用户Action
 * @author xyf
 * @actions doAuditAgree doAuditDisAgree doList
 */
@SuppressWarnings("serial")
public class CustPartNoInfoManagerAction extends BaseAction {

	/**
	 * Manager名
	 */
	static final String custPartNoInfoMgrName = "custPartNoInfoMgr";
		
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(CustPartNoInfoNormalAction.class);

    CustPartNoInfo custPartNoInfo;
    public CustPartNoInfo getCustPartNoInfo() { return custPartNoInfo; }
    public void setCustPartNoInfo(CustPartNoInfo custPartNoInfo) { this.custPartNoInfo = custPartNoInfo; }
    
	String auditDesc;
	public String getAuditDesc() { return auditDesc; }
	public void setAuditDesc(String a) { auditDesc = a; }

    /**
     * 查询客户物料关系
 	 * @action.input custPartNoInfo.commCode + custPartNoInfo.查询条件
	 * @action.result list：列表 count：总数
     * @return
     */
    public String doList()
    {
    	try
    	{
			if (logger.isDebugEnabled())    logger.debug("begin doList");

			if(custPartNoInfo == null)
				custPartNoInfo = new CustPartNoInfo();

			/*
			// 缺省查询正式数据
			if(StringUtils.isEmpty(custPartNoInfo.getState()))
				custPartNoInfo.setState("0");
			*/
			
//			if(!checkCommonFields())
//				return ERROR;
//			
			setPagination(custPartNoInfo);
			CustPartNoInfoMgr mgr = (CustPartNoInfoMgr)getBean(custPartNoInfoMgrName);
			setResult("list", mgr.listCustPartNoInfo(custPartNoInfo));
			setTotalCount(mgr.listCustPartNoInfoCount(custPartNoInfo));
			setResult("count", getTotalCount());
			if (logger.isDebugEnabled())    logger.debug("end doList");
    		return SUCCESS;
    	}
    	catch(Exception e)
    	{
    		logger.error("catch Exception in doList.", e);
			setErrorReason("内部错误");
			return ERROR;
    	}
    }

    /**
     * 审批同意
     * @action.input 	custPartNoInfo.*
	 * @action.input	auditDesc	审批意见
     * @return
     */
    public String doAuditAgree()
    {
    	try{
    		if (logger.isDebugEnabled())    logger.debug("begin doAuditAgree");
    		
			CustPartNoInfoMgr mgr = (CustPartNoInfoMgr)getBean(custPartNoInfoMgrName);
    		
			/*
			if(!checkCommonFields())
				return ERROR;
			List<FieldErr> errs = CustPartNoInfoUtil.checkInputFields(custPartNoInfo);
			if(errs.isEmpty())
			{
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			
			*/
			if(custPartNoInfo == null || custPartNoInfo.getSeqId() == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
    		custPartNoInfo = mgr.getCustPartNoInfoByID(custPartNoInfo.getSeqId().toString());
			if(custPartNoInfo == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
			
			int i = mgr.auditAgreeCustPartNoInfo(custPartNoInfo, getLoginStaff().getStaffId().toString(), getLoginStaff().getStaffName(), auditDesc);
			if(i != 0)
			{
				logger.info("审批出错！");
				setErrorReason("审批出错！");
				return ERROR;
			}
		
    		if (logger.isDebugEnabled())    logger.debug("end doAuditAgree");
    		return SUCCESS;
    	}catch(Exception e){
    		logger.error("catch Exception in doAuditAgree.", e);
			setErrorReason(e.getMessage());
			return ERROR;
    	}
    }
    
    /**
     * 审批不同意
     * @action.input 	custPartNoInfo.*
	 * @action.input	auditDesc	审批意见
     * @return
     */
    public String doAuditDisAgree()
    {
    	try{
    		if (logger.isDebugEnabled())    logger.debug("begin doAuditDisAgree");
    		
			CustPartNoInfoMgr mgr = (CustPartNoInfoMgr)getBean(custPartNoInfoMgrName);
			
    		/*
    		if(!checkCommonFields())
				return ERROR;
			List<FieldErr> errs = CustPartNoInfoUtil.checkInputFields(custPartNoInfo);
			if(errs.isEmpty())
			{
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			*/
    		
			if(custPartNoInfo == null || custPartNoInfo.getSeqId() == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
    		custPartNoInfo = mgr.getCustPartNoInfoByID(custPartNoInfo.getSeqId().toString());
			if(custPartNoInfo == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
			
			int i = mgr.auditDisAgreeCustPartNoInfo(custPartNoInfo, getLoginStaff().getStaffId().toString(), getLoginStaff().getStaffName(), auditDesc);
			if(i != 0)
			{
				logger.info("审批出错！");
				setErrorReason("审批出错！");
				return ERROR;
			}
		
    		if (logger.isDebugEnabled())    logger.debug("end doAuditDisAgree");
    		return SUCCESS;
    	}catch(Exception e){
    		logger.error("catch Exception in doAuditDisAgree.", e);
			setErrorReason(e.getMessage());
			return ERROR;
    	}
    }
    
   /**
     * 审批
     * @action.input 	custPartNoInfo.seqId
	 * @action.input audit	审批结果 0：审批不通过；1：审批通过
	 * @action.input	auditDesc	审批意见
     * @return
     */
    public String doAudit()
    {
		try {
			String audit = this.getHttpServletRequest().getParameter("audit");
			if(audit == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
			if(audit.equals("0")) {
				return doAuditDisAgree();
			} else {
				return doAuditAgree();
			}
		} catch(Exception e) {
			logger.error("catch Exception in doAudit", e);
			setErrorReason("内部错误");
            return ERROR;
		}
    }
    
    /**
     * 获取客户物料关系
     * @action.input	custPartNoInfo.seqId
     * @action.result	custPartNoInfo.*
     * @return
     */
   public String doGetInfo() {
    	try {
    		CustPartNoInfoMgr mgr = (CustPartNoInfoMgr)getBean(custPartNoInfoMgrName);
    		if(custPartNoInfo == null || custPartNoInfo.getSeqId() == null) {
    			logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
    		}
    		CustPartNoInfo info =  mgr.getCustPartNoInfoByID(custPartNoInfo.getSeqId().toString());
    		if(null != info){
	    		//获取客户信息
	    		CustomerInfoMgr custmgr = (CustomerInfoMgr)getBean(CustomerInfoNormalAction.custInfoMgrName);
	    		CustomerInfo custInfo = new CustomerInfo();
				custInfo.setCommCode(info.getCommCode());
				custInfo.setState("0");
				custInfo = custmgr.getCustomerInfo(custInfo, false);
				setResult("custInfo", custInfo);
				
    		}else{
    			logger.info("无对应的客户物料信息！");
				setErrorReason("无对应的客户物料信息！");
				return ERROR;
    		}
    		setResult("custPartNoInfo", info);
    		return SUCCESS;
    	} catch(Exception e) {
    		logger.error("catch Exception in doGetInfo.", e);
			setErrorReason("内部错误");
			return ERROR;
    	}
    }

	/**
	* 批量审批
	* @action.input baseSeqId	以,分割
	* @action.input audit	审批通过与否，为"pass"时审批通过
	* @action.input jqyy	拒绝意见
	* @return
	*/
	public String doAuditList() {
			try{
				if (logger.isDebugEnabled())    logger.debug("begin doAuditList");		
				CustPartNoInfoMgr mgr = (CustPartNoInfoMgr)getBean(custPartNoInfoMgrName);

				String jqyy = this.getHttpServletRequest().getParameter("jqyy");
				String audit = this.getHttpServletRequest().getParameter("audit");
				String idlist = this.getHttpServletRequest().getParameter("baseSeqId");
				if(StringUtils.isEmpty(idlist)){
					logger.info("参数错误！");
					setErrorReason("参数错误！");
					return ERROR;
				}
				StringBuffer sb = new StringBuffer();
				for(String id : idlist.split(",")){
					custPartNoInfo = mgr.getCustPartNoInfoByID(id);
					if(custPartNoInfo == null) {
						String s = "id=" + id + " 没有找到！";
						if(sb.length() > 0)
							sb.append("<br />");
						sb.append(s);
						logger.info(s);
						continue;
					}
					
					int i;
					if("pass".equals(audit))
						i = mgr.auditAgreeCustPartNoInfo(custPartNoInfo, getLoginStaff().getStaffId().toString(), getLoginStaff().getStaffName(), auditDesc);
					else
						i = mgr.auditDisAgreeCustPartNoInfo(custPartNoInfo, getLoginStaff().getStaffId().toString(), getLoginStaff().getStaffName(), jqyy);
					if(i != 0)
					{
						String s = custPartNoInfo.getLogContent() + " 审批出错！";
						if(sb.length() > 0)
							sb.append("<br />");
						sb.append(s);
						logger.error(s);
					}
				}
				if(sb.length() > 0){
					setErrorReason(sb.toString());
					return ERROR;
				}
				if (logger.isDebugEnabled())    logger.debug("end doAuditList");
				return SUCCESS;
			}catch(Exception e){
				logger.error("catch Exception in doAuditList.", e);
				setErrorReason("内部错误");
				return ERROR;
			} 
	}

	/**
	 * 检查客户编码是否填写。如果出现问题，本函数内设置了ErrorReaseon。
	 * @return
	 */
	protected boolean checkCommonFields()
	{
		try{
			if(custPartNoInfo == null)
			{
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return false;
			}
			
			String commCode = custPartNoInfo.getCommCode();
			String shortName = custPartNoInfo.getShortName();
			if(StringUtils.isEmpty(commCode) && StringUtils.isEmpty(shortName))
			{
				logger.info("客户编码或客户简称没有填写！");
				setErrorReason("客户编码或客户简称没有填写！");
				return false;
			}
			
			return true;
		}catch(Exception e){
			logger.error("catch Exception in checkCommonFields", e);
			setErrorReason("内部错误");
			return false;
		}
	}

	protected void fixCommCode()
	{
		try {
			if(custPartNoInfo == null)
				return;
			if(StringUtils.isEmpty(custPartNoInfo.getCommCode()))
			{
				String s = this.getHttpServletRequest().getParameter("custInfo.commCode");
				if(StringUtils.isNotEmpty(s))
				{
					custPartNoInfo.setCommCode(s);
				}
			}
		} catch (Exception e) {
			logger.error("catch Exception in fixCommCode", e);
		}
	}
}
