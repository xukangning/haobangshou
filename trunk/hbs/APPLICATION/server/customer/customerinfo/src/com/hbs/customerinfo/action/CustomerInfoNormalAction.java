/**
 * �ͻ���ϢAction
 */
package com.hbs.customerinfo.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.hbs.common.action.base.BaseAction;
import com.hbs.common.springhelper.BeanLocator;
import com.hbs.customerinfo.manager.CustomerInfoMgr;
import com.hbs.domain.customer.customerinfo.pojo.CustomerInfo;

/**
 * ��ͨ��ɫ�ͻ���ϢAction
 * @author xyf
 * @actions doList doGetInfo doSaveTemp doSave
 */
@SuppressWarnings("serial")
public class CustomerInfoNormalAction extends BaseAction {

	/**
	 * Manager��
	 */
	public static final String custInfoMgrName = "customerInfoMgr";

	/**
	 * logger.
	 */
	private static final Logger logger = Logger
			.getLogger(CustomerInfoNormalAction.class);

	CustomerInfo custInfo;

	/**
	 * ��ȡ�ͻ���Ϣ
	 * @return �ͻ���Ϣ
	 */
	public CustomerInfo getCustInfo() {
		return custInfo;
	}

	/**
	 * ���ÿͻ���Ϣ
	 * @param a �ͻ���Ϣ
	 */
	public void setCustInfo(CustomerInfo a) {
		this.custInfo = a;
	}

	/**
	 * ��ѯ���޶��Լ��ܹ����Ŀͻ���Ϣ������mgr.getCustomerInfoListByUser��
	 * @action.input custInfo.��ѯ����
	 * @action.result list���б� count������
	 * @return
	 */
	public String doList() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doList");
			if (custInfo == null) {
				custInfo = new CustomerInfo();
			}
			setPagination(custInfo);
			setMyId(false);
			CustomerInfoMgr mgr = (CustomerInfoMgr) BeanLocator.getInstance()
					.getBean(custInfoMgrName);
			setResult("list", mgr.getCustomerInfoList(custInfo));
			setTotalCount(mgr.getCustomerInfoCount(custInfo));
			setResult("count", getTotalCount());
			if (logger.isDebugEnabled())
				logger.debug("end doList");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doList.", e);
			setErrorReason("�ڲ�����");
			return ERROR;
		}
	}

	/**
	 * ��ʱ�����û���Ϣ
	 * @action.input custInfo.*
	 * @return
	 */
	public String doSaveTemp() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doSaveTemp");

			if (custInfo == null) {
				logger.info("��������");
				setErrorReason("��������");
				return ERROR;
			}

			custInfo.setState("1");
			CustomerInfoUtil.processListData(custInfo, this.getHttpServletRequest());
			List<FieldErr> errs = CustomerInfoUtil.checkInputFields(custInfo);
			if (!errs.isEmpty()) {
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			if (CustomerInfoUtil.checkSetStaffId(custInfo))
				setMyId(true);

			CustomerInfoMgr mgr = (CustomerInfoMgr) BeanLocator.getInstance()
					.getBean(custInfoMgrName);

			CustomerInfo info2 = mgr.getCustomerInfo(custInfo, false);
			int ret;
			if (info2 != null)
				ret = mgr.updateCustomerInfo(custInfo, getLoginStaff().getStaffId(), getLoginStaff().getStaffName());
			else
				ret = mgr.saveTempCustomerInfo(custInfo);
			if (ret != 0) {
				logger.info("��ʱ���������");
				setErrorReason("��ʱ���������");
				return ERROR;
			}
			if (logger.isDebugEnabled())
				logger.debug("end doSaveTemp");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doSaveTemp", e);
			setErrorReason("�ڲ�����");
			return ERROR;
		}
	}

	/**
	 * �����û���Ϣ�����ڲ�ͬ��״̬�����в�ͬ�Ĳ���
	 * @action.input custInfo.*
	 * @return
	 */
	public String doSave() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doSave");

			if (custInfo == null) {
				logger.info("��������");
				setErrorReason("��������");
				return ERROR;
			}

			if (custInfo.getState() == null || custInfo.getState() == "")
				custInfo.setState("2");
			if (CustomerInfoUtil.checkSetStaffId(custInfo))
				setMyId(true);
			CustomerInfoUtil.processListData(custInfo, this
					.getHttpServletRequest());
			List<FieldErr> errs = CustomerInfoUtil.checkInputFields(custInfo);
			if (!errs.isEmpty()) {
				String s = FieldErr.formFieldsErrString(errs);
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}

			CustomerInfoMgr mgr = (CustomerInfoMgr) BeanLocator.getInstance()
					.getBean(custInfoMgrName);

			CustomerInfo info2 = mgr.getCustomerInfo(custInfo, false);
			int ret;
			if (info2 != null)
				ret = mgr.updateCustomerInfo(custInfo, getLoginStaff()
						.getStaffId(), getLoginStaff().getStaffName());
			else {
				custInfo.setState("1");
				ret = mgr.commitCustomerInfo(custInfo, getLoginStaff()
						.getStaffId(), getLoginStaff().getStaffName());
			}
			if (ret != 0) {
				String s;
				switch (ret) {
				case 1:
					s = "�޴�״̬��";
					break;
				case 2:
					s = "״̬����ȷ��";
					break;
				default:
					s = "���������";
				}
				logger.info(s);
				setErrorReason(s);
				return ERROR;
			}
			if (logger.isDebugEnabled())
				logger.debug("end doSave");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doSave", e);
			setErrorReason("�ڲ�����");
			return ERROR;
		}

	}

	/**
	 * ��ȡ�ͻ���ϸ��Ϣ
	 * @action.input 
	 *	custInfo.baseSeqId �� (custInfo.commCode + custInfo.state)
	 * @action.result custInfo.*
	 * @return
	 */
	public String doGetInfo() {
		try {
			if (logger.isDebugEnabled())
				logger.debug("begin doGetInfo");
			if (!CustomerInfoUtil.checkKeyFields(custInfo)) {
				logger.info("����Ϊ�գ�");
				setErrorReason("����Ϊ�գ�");
				return ERROR;
			}
			setMyId(false);
			CustomerInfoMgr mgr = (CustomerInfoMgr) BeanLocator.getInstance()
					.getBean(custInfoMgrName);
			custInfo = mgr.getCustomerInfo(custInfo, true);
			this.setResult("custInfo", custInfo);
			if (logger.isDebugEnabled())
				logger.debug("end doGetInfo");
			return SUCCESS;
		} catch (Exception e) {
			logger.error("catch Exception in doGetInfo", e);
			setErrorReason("�ڲ�����");
			return ERROR;
		}
	}

	/**
	 * ����STAFF��ϢΪ��ǰ�û���Ϣ
	 * @param setName �Ƿ������û�����Ϊtrueʱ����staffNameΪ��ǰ�û���staffName��Ϊfalseʱ����staffNameΪnull��
	 * �ڲ�ѯʱ��Ϊfalse����������ʱ��Ϊtrue��
	 * @throws Exception
	 */
	protected void setMyId(boolean setName) throws Exception {
		custInfo.setStaffId(getLoginStaff().getStaffId());
		custInfo.setStaffName(setName ? getLoginStaff().getStaffName() : null);
	}

}