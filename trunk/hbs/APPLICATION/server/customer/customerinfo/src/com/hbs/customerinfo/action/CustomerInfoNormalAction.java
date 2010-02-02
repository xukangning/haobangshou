/**
 * 客户信息Action
 */
package com.hbs.customerinfo.action;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.hbs.common.action.base.BaseAction;
import com.hbs.common.josn.JSONException;
import com.hbs.common.josn.JSONUtil;
import com.hbs.common.springhelper.BeanLocator;
import com.hbs.customerinfo.action.FieldErr;
import com.hbs.customerinfo.manager.CustomerInfoMgr;
import com.hbs.domain.customer.customerinfo.pojo.CustomerInfo;

/**
 * 普通角色客户信息Action
 * @author xyf
 * @actions doList doGetInfo doSaveTemp doSave
 */
@SuppressWarnings("serial")
public class CustomerInfoNormalAction extends BaseAction {

	/**
	 * Manager名
	 */
	static final String custInfoMgrName = "customerInfoMgr";
		
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(CustomerInfoNormalAction.class);

    CustomerInfo custInfo;
	
	/**
	 * 获取客户信息
	 * @return 客户信息
	 */
	public CustomerInfo getCustInfo(){ return custInfo;}
	
	/**
	 * 设置客户信息
	 * @param a 客户信息
	 */
	public void setCustInfo(CustomerInfo a) { this.custInfo = a; }
	
	/**
	 * 查询，限定自己能管理的客户信息。调用mgr.getCustomerInfoListByUser。
	 * @action.input custInfo.查询条件
	 * @action.result list：列表 count：总数
	 * @return
	 */
	public String doList()
	{
		try
		{
			logger.debug("begin doList");
			if(custInfo == null)
			{
				custInfo = new CustomerInfo();
			}
			setPagination(custInfo);
			setMyId(false);
			CustomerInfoMgr mgr = (CustomerInfoMgr)BeanLocator.getInstance().getBean(custInfoMgrName);
			setResult("list", mgr.getCustomerInfoList(custInfo));
			setTotalCount(mgr.getCustomerInfoCount(custInfo));
			setResult("count", getTotalCount());
			logger.debug("end doList");
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
	 * 临时保存用户信息
	 * @action.input custInfo.*
	 * @return
	 */
	public String doSaveTemp()
	{
		try
		{
			logger.debug("begin doSaveTemp");
			
			if(custInfo == null)
			{
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;				
			}

			custInfo.setState("1");
			CustomerInfoUtil.processListData(custInfo, this.getHttpServletRequest());
			List<FieldErr> errs = CustomerInfoUtil.checkInputFields(custInfo);
			if(!errs.isEmpty())
			{
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			if(CustomerInfoUtil.checkSetStaffId(custInfo))
				setMyId(true);
			
			CustomerInfoMgr mgr = (CustomerInfoMgr)BeanLocator.getInstance().getBean(custInfoMgrName);
			
			CustomerInfo info2 = mgr.getCustomerInfo(custInfo, false);
			int ret;
			if(info2 != null)
				ret = mgr.updateCustomerInfo(custInfo, getLoginStaff().getStaffId(), getLoginStaff().getStaffName());
			else
				ret = mgr.saveTempCustomerInfo(custInfo);
			if(ret != 0)
			{
				logger.info("临时保存出错！");
				setErrorReason("临时保存出错！");
				return ERROR;
			}
			logger.debug("end doSaveTemp");
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("catch Exception in doSaveTemp", e);
			setErrorReason("内部错误");
            return ERROR;
		}
	}
	
	/**
	 * 保存用户信息，对于不同的状态，进行不同的操作
	 * @action.input custInfo.*
	 * @return
	 */
	public String doSave()
	{
		try
		{
			logger.debug("begin doSave");
			
			if(custInfo == null)
			{
				logger.info("参数错误！");
				setErrorReason("参数错误！");
				return ERROR;				
			}

			if(custInfo.getState() == null || custInfo.getState() == "")
				custInfo.setState("2");
			if(CustomerInfoUtil.checkSetStaffId(custInfo))
				setMyId(true);
			CustomerInfoUtil.processListData(custInfo, this.getHttpServletRequest());
			List<FieldErr> errs = CustomerInfoUtil.checkInputFields(custInfo);
			if(!errs.isEmpty())
			{
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			
			CustomerInfoMgr mgr = (CustomerInfoMgr)BeanLocator.getInstance().getBean(custInfoMgrName);
			
			CustomerInfo info2 = mgr.getCustomerInfo(custInfo, false);
			int ret;
			if(info2 != null)
				ret = mgr.updateCustomerInfo(custInfo, getLoginStaff().getStaffId(), getLoginStaff().getStaffName());
			else
			{
				custInfo.setState("1");
				ret = mgr.commitCustomerInfo(custInfo, getLoginStaff().getStaffId(), getLoginStaff().getStaffName());
			}
			if(ret != 0)
			{
				String s;
				switch(ret)
				{
				case 1:
					s = "无此状态！";
					break;
				case 2:
					s = "状态不正确！";
					break;
				default:
					s = "保存出错！";
				}
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			logger.debug("end doSave");
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("catch Exception in doSave", e);
			setErrorReason("内部错误");
            return ERROR;
		}

	}
	
	/**
	 * 获取客户详细信息
	 * @action.input 
	 *	custInfo.baseSeqId 或 (custInfo.commCode + custInfo.state)
	 * @action.result custInfo.*
	 * @return
	 */
	public String doGetInfo()
	{
		try
		{
			logger.debug("begin doGetInfo");
			if(!CustomerInfoUtil.checkKeyFields(custInfo))
			{
				logger.info("参数为空！");
				setErrorReason("参数为空！");
				return ERROR;
			}
			setMyId(false);
			CustomerInfoMgr mgr = (CustomerInfoMgr)BeanLocator.getInstance().getBean(custInfoMgrName);
			custInfo = mgr.getCustomerInfo(custInfo, true);
			this.setResult("custInfo", custInfo);
			logger.debug("end doGetInfo");
			return SUCCESS;
		}
		catch(Exception e)
		{
			logger.error("catch Exception in doGetInfo", e);
			setErrorReason("内部错误");
            return ERROR;
		}
	}
	
	/**
	 * 设置STAFF信息为当前用户信息
	 * @param setName 是否设置用户名。为true时设置staffName为当前用户的staffName；为false时设置staffName为null。
	 * 在查询时，为false；在增、改时，为true。
	 * @throws Exception
	 */
	protected void setMyId(boolean setName) throws Exception
	{
		custInfo.setStaffId(getLoginStaff().getStaffId());
		custInfo.setStaffName(setName ? getLoginStaff().getStaffName() : null);
	}
	
}
