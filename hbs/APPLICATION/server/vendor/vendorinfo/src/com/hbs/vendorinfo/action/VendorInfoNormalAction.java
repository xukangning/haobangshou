/**
 * 客户信息Action
 */
package com.hbs.vendorinfo.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hbs.common.action.FieldErr;
import com.hbs.common.action.JianQuanUtil;
import com.hbs.common.action.base.BaseAction;


import com.hbs.domain.common.pojo.baseinfo.ContactInfo;

import com.hbs.domain.vendor.vendorinfo.pojo.VendorInfo;
import com.hbs.vendorinfo.manager.VendorContactMgr;
import com.hbs.vendorinfo.manager.VendorInfoMgr;

/**
 * 普通角色供应商信息Action
 * @author xyf
 * @actions doList doGetInfo doSaveTemp doSave doDelete
 */
@SuppressWarnings("serial")
public class VendorInfoNormalAction extends BaseAction {

	/**
	 * Manager名
	 */
	public static final String vendorInfoMgrName = "vendorInfoMgr";

	/**
	 * logger.
	 */
	private static final Logger logger = Logger
			.getLogger(VendorInfoNormalAction.class);

	public static final String roleName = "cgnormal";
	VendorInfo vendorInfo;	
	/**
	 * 获取供应商信息
	 * @return 供应商信息
	 */
	public VendorInfo getVendorInfo() {
		return vendorInfo;
	}

	/**
	 * 设置供应商信息
	 * @param a 供应商信息
	 */
	public void setVendorInfo(VendorInfo a) {
		this.vendorInfo = a;
	}

	

	/**
	 * 查询，限定自己能管理的供应商信息。调用mgr.getCustomerInfoListByUser。
	 * @action.input vendorInfo.查询条件
	 * @action.result list：列表 count：总数
	 * @return
	 */
	public String doList() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doList");
			if (vendorInfo == null) {
				vendorInfo = new VendorInfo();
			}
			setPagination(vendorInfo);
			setMyId(false);
			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			setResult("list", mgr.getVendorInfoList(vendorInfo));
			setTotalCount(mgr.getVendorInfoCount(vendorInfo));
			setResult("count", getTotalCount());
			setResult("jq", JianQuanUtil.getJQ(JianQuanUtil.TypeCustState, roleName));
			if (logger.isDebugEnabled())
				logger.debug("end doList");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doList.", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	/**
	 * 非采购的下拉框使用
	 * @return
	 */
	public String doListDict() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doListDict");
			if (vendorInfo == null) {
				vendorInfo = new VendorInfo();
				vendorInfo.setState("0");
			}else{
				vendorInfo.setState("0");
			}			
			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			setResult("list", mgr.getVendorInfoList(vendorInfo));
			
			if (logger.isDebugEnabled())
				logger.debug("end doListDict");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doList.", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	
	/**
	 * 临时保存供应商信息
	 * @action.input vendorInfo.*
	 * @action.result	seqId	insert的id。如果没有insert操作，则没有这一项。
	 * @return
	 */
	public String doSaveTemp() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doSaveTemp");

			if (vendorInfo == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}

			vendorInfo.setState("1");
			vendorInfo.setIsShowPrice("0");
			if (VendorInfoUtil.checkSetStaffId(vendorInfo))
				setMyId(true);
			VendorInfoUtil.processListData(vendorInfo, this.getHttpServletRequest());
			List<FieldErr> errs = VendorInfoUtil.checkInputFields(vendorInfo);
			if (!errs.isEmpty()) {
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}

			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			
		    if(checkVendorName(mgr,vendorInfo)){
		    	setErrorReason("您提交的客户资料存在着相同的供应商简称或供应商全称！不能提交");
				return ERROR;
		    }
//			if(vendorInfo.getBaseSeqId() == null) {
//				VendorInfo vInfo = new VendorInfo();
//				vInfo.setCommCode(vendorInfo.getCommCode());
//				Integer i = mgr.getVendorInfoCount(vInfo);
//				if(i == null || i.compareTo(0) > 0) {
//					logger.error("供应商编码重复！" + vendorInfo.getCommCode());
//					setErrorReason("供应商编码重复！");
//					return ERROR;
//				}
//			}
//			VendorInfo info2 = mgr.getVendorInfo(vendorInfo, false);
			int ret;
//			if (info2 != null)
//				ret = mgr.updateVendorInfo(vendorInfo);
//			else
//				ret = mgr.saveTempVendorInfo(vendorInfo);
			ret = mgr.saveTempVendorInfo(vendorInfo);
//			
			if (ret < 0) {
				logger.info("临时保存出错！");
				setErrorReason("临时保存出错！");
				return ERROR;
			}
			if(ret > 0) {
				this.setResult("seqId", ret);
				if (logger.isDebugEnabled()) logger.debug("seqId="+ret);
			}
			setResult("state", "1");
			this.setAlertMsg("临时保存成功！");
			if (logger.isDebugEnabled())
				logger.debug("end doSaveTemp");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doSaveTemp", e);
			setErrorReason(e.getMessage());
			return ERROR;
		}
	}

	/**
	 * 保存用户信息，对于不同的状态，进行不同的操作
	 * @action.input vendorInfo.*
	 * @action.result	seqId	insert的id。如果没有insert操作，则没有这一项。
	 * @return
	 */
	public String doSave() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doSave");

			if (vendorInfo == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
			vendorInfo.setIsShowPrice("0");
			if (StringUtils.isEmpty(vendorInfo.getState()))
				vendorInfo.setState("2");
			if (VendorInfoUtil.checkSetStaffId(vendorInfo))
				setMyId(true);
			VendorInfoUtil.processListData(vendorInfo, this
					.getHttpServletRequest());
			List<FieldErr> errs = VendorInfoUtil.checkInputFields(vendorInfo);
			if (!errs.isEmpty()) {
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}

			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			if(checkVendorName(mgr,vendorInfo)){
		    	setErrorReason("您提交的客户资料存在着相同的供应商简称或供应商全称！不能提交");
				return ERROR;
		    }
//			if(vendorInfo.getBaseSeqId() == null) {
//				VendorInfo vInfo = new VendorInfo();
//				vInfo.setCommCode(vendorInfo.getCommCode());
//				Integer i = mgr.getVendorInfoCount(vInfo);
//				if(i == null || i.compareTo(0) > 0) {
//					logger.error("供应商编码重复！" + vendorInfo.getCommCode());
//					setErrorReason("供应商编码重复！");
//					return ERROR;
//				}
//			}
//			VendorInfo info2 = mgr.getVendorInfo(vendorInfo, false);
			int ret;
//			if (info2 != null) {
//				if(vendorInfo.getState().equals("1"))
//					ret = mgr.commitVendorInfo(vendorInfo);
//				else
//					ret = mgr.updateCustomerInfo(vendorInfo);
//			}
//			else {
//				vendorInfo.setState("1");
//				//delete by yangzj 为什么要设置此值？
//				//vendorInfo.setCreditRate("3");
				ret = mgr.commitVendorInfo(vendorInfo);
//			}
			if (ret < 0) {
				String s;
				switch (ret) {
				case -1:
					s = "已经存在待领导审批的修改信息，无法再次修改！";
					break;
				case -2:
					s = "状态不正确！";
					break;
				default:
					s = "保存出错！";
					logger.info(s + " ret=" + ret);
					break;
				}
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			if(ret > 0) {
				this.setResult("seqId", ret);
				if (logger.isDebugEnabled()) logger.debug("seqId="+ret);
			}
			setResult("state", "2");
			this.setAlertMsg("提交成功！");
			if (logger.isDebugEnabled())
				logger.debug("end doSave");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doSave", e);
			setErrorReason(e.getMessage());
			return ERROR;
		}

	}

	/**
	 * 获取供应商详细信息
	 * @action.input 
	 *	vendorInfo.baseSeqId 或 (vendorInfo.commCode + vendorInfo.state)
	 * @action.result vendorInfo.*
	 * @return
	 */
	public String doGetInfo() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doGetInfo");
			if (!VendorInfoUtil.checkKeyFields(vendorInfo)) {
				logger.info("参数为空！");
				setErrorReason("参数为空！");
				return ERROR;
			}
			setMyId(false);
			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			vendorInfo = VendorInfoUtil.getVendorInfo(mgr, vendorInfo);
			String id = getLoginStaff().getStaffId().toString();
			if(vendorInfo != null &&
					StringUtils.isNotEmpty(id) &&
					id.equals(vendorInfo.getStaffId())
				)
			{
				this.setResult("vendorInfo", vendorInfo);
				if (logger.isDebugEnabled())
					logger.debug("end doGetInfo");
				return SUCCESS;
			}
			this.setErrorReason("权限错误");
			return ERROR;
		} catch (Exception e) {
			logger.error("catch Exception in doGetInfo", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}

	/**
	 * 删除审批不通过的数据
	 * 
	 * @action.input vendorInfo.baseSeqId 或 (vendorInfo.commCode + vendorInfo.state)
	 * @return
	 */
	public String doDelete() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doGetInfo");
			if (!VendorInfoUtil.checkKeyFields(vendorInfo)) {
				logger.info("参数为空！");
				setErrorReason("参数为空！");
				return ERROR;
			}
			try {
				if (3 != Integer.parseInt(vendorInfo.getState())) {
					logger.info("状态不正确！");
					setErrorReason("状态不正确！");
					return ERROR;
				}
			} catch (Exception e) {
				logger.info("状态不正确！");
				setErrorReason("状态不正确！");
				return ERROR;
			}
			setMyId(false);
			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			vendorInfo = mgr.getVendorInfo(vendorInfo, true);
			if (vendorInfo == null) {
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;
			}
			int i = mgr.deleteVendorInfo(vendorInfo,
					getHttpServletRequest().getParameter("delDesc"));
			switch (i) {
			case 0:
				this.setAlertMsg("删除成功！");
				return SUCCESS;
			case 2:
				logger.info("状态不正确！");
				setErrorReason("状态不正确！");
				return ERROR;
			default:
				logger.info("删除出错！");
				setErrorReason("删除出错！");
				return ERROR;
			}
		} catch (Exception e) {
			logger.error("catch Exception in doGetInfo", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}

	/**
	 * 获取正式数据中的收货人信息
	 * @action.input	custInfo.baseSeqId 或 (custInfo.commCode + custInfo.state)
	 * @action.result list：列表 count：总数
	 * @return
	 */
	public String doGetConsigneeList()
	{
		try
		{
			if (logger.isDebugEnabled())
				logger.debug("begin doGetConsigneeList");
			setResult("list", getPersonList("2"));
			logger.debug("end doGetConsigneeList");
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("catch Exception in doGetConsigneeList", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	
	/**
	 * 获取正式数据中的联系人信息
	 * @action.input	custInfo.baseSeqId 或 (custInfo.commCode + custInfo.state)
	 * @action.result list：列表 count：总数
	 * @return
	 */
	public String doGetContactList()
	{
		try
		{
			if (logger.isDebugEnabled())
				logger.debug("begin doGetContactList");
			setResult("list", getPersonList("1"));
			logger.debug("end doGetContactList");
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("catch Exception in doGetContactList", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	
	/**
	 * 获取正式数据中的联系人信息，doGetConsigneeList、doGetContactList的具体操作函数
	 * @param type 联系人类别。1：联系人；2：收货人
	 * @return
	 */
	protected List<ContactInfo> getPersonList(String type) throws Exception
	{
		if (!VendorInfoUtil.checkKeyFields(vendorInfo)) {
			logger.info("参数为空！");
			setErrorReason("参数为空！");
			return null;
		}
		VendorContactMgr mgr = (VendorContactMgr)getBean("vendorContactMgr");
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setState("0");
		contactInfo.setConType(type);
		Integer id = vendorInfo.getBaseSeqId();
		if(id != null)
			contactInfo.setBaseSeqId(id.toString());
		else
			contactInfo.setCommCode(vendorInfo.getCommCode());
		
		return mgr.listContactInfo(contactInfo);
	}
	
	/**
	 * 根据seqId获取联系人信息
	 * @action.input seqId
	 * @action.result contactInfo.*
	 * @return
	 */
	public String doGetContactInfoById() {
		try {
			String s = this.getHttpServletRequest().getParameter("seqId");
			logger.debug("begin doGetContactInfoById " + s);
			if(StringUtils.isEmpty(s)) {
				logger.info("参数为空！");
				setErrorReason("参数为空！");
				return ERROR;
			}
			VendorContactMgr mgr = (VendorContactMgr)getBean("vendorContactMgr");
			setResult("contactInfo", mgr.getContactInfo(s));
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doGetContactInfoById", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	
	/**
	 * 检查编码
	 * @action.input value
	 * @return
	 */
	public String doCheckCommCode() {
		try {
			String s = this.getHttpServletRequest().getParameter("value");
			logger.debug("begin doCheckCommCode " + s);
			if(StringUtils.isEmpty(s)) {
				logger.info("参数为空！");
				setErrorReason("参数为空！");
				return ERROR;
			}
			vendorInfo = new VendorInfo();
			vendorInfo.setCommCode(s);
			VendorInfoMgr mgr = (VendorInfoMgr)getBean(vendorInfoMgrName);
			Integer i = mgr.getVendorInfoCount(vendorInfo);
			if(i == null || i.compareTo(0) > 0 ){
				logger.debug("编码重复！");
				setErrorReason("编码重复！");
				return ERROR;
			}
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doCheckCommCode", e);
			setErrorReason("内部错误");
			return ERROR;
		}
	}
	/**
	 * 获取新的供应商编码
	 * @return
	 */
//	public String doGetNewVendorCode(){
//		try {
//			
//			logger.debug("begin doGetNewVendorCode" );
//			
//			
//			String vendorCode = SysSequenceMgr.getCode(SysSequenceMgr.GV_CODE);
//			setResult("commCode",vendorCode);
//			
//			return SUCCESS;
//		} catch (Exception e) {
//			logger.error("catch Exception in doGetNewVendorCode", e);
//			setErrorReason("无法获取新的供应商编码！请检查配置！");
//			return ERROR;
//		}
//	}
	/**
	 * 设置STAFF信息为当前用户信息
	 * 
	 * @param setName 是否设置用户名。
	 * 	为true时设置staffName为当前用户的staffName；为false时设置staffName为null。
	 * 	在查询时，为false；在增、改时，为true。
	 * @throws Exception
	 */
	protected void setMyId(boolean setName) throws Exception {
		vendorInfo.setStaffId(getLoginStaff().getStaffId().toString());
		vendorInfo.setStaffName(setName ? getLoginStaff().getStaffName() : null);
	}

	
	/**
	 * 查询所有客户简称和名称
	 * @param mgr
	 * @param info
	 * @return
	 * @throws Exception
	 */
	private boolean checkVendorName(VendorInfoMgr mgr , VendorInfo info) throws Exception{
		boolean ret = false;
		
		
		Integer icount = mgr.getVendorInfoCheckCount(info);
		if(icount != null && icount.intValue() >0){
			ret = true;
		}
		return ret;
	}
}
