/**
 * system ：hbs
 * desc:   预付费信息的管理类 
 * version: 1.0
 * author : yangzj
 */
package com.hbs.common.manager.baseinfo;

import java.util.Date;
import java.util.List;

import com.hbs.common.springhelper.BeanLocator;
import com.hbs.customer.common.constants.StateConstants;


import com.hbs.domain.common.dao.baseinfo.OperLogDao;
import com.hbs.domain.common.dao.baseinfo.PrePaidInfoDao;

import com.hbs.domain.common.pojo.baseinfo.OperLog;
import com.hbs.domain.common.pojo.baseinfo.PrePaidInfo;

public abstract class PrePaidMgr {
	
	/**
	 * 抽象方法，子类实现
	 * @return
	 */
	public abstract String getPrePaidDao();
	
	/**
	 *  抽象方法，子类实现
	 * @return
	 */
	public abstract String getLogDao();
	/**
	 * 插入预付费信息,预付费信息的插入
	 * 如果是跟客户信息同时提交，则跟随客户信息的状态
	 * @param accountPreiod
	 * @throws Exception
	 * @return 0--成功 1---数据库已经存在重复数据
	 */
	public int insertPrePaidInfo(PrePaidInfo prePaidInfo) throws Exception{
		int ret = 0;
		PrePaidInfoDao aPrepaidDao = (PrePaidInfoDao) BeanLocator
				.getInstance().getBean(
						getPrePaidDao());
		PrePaidInfo tempPrePaid = aPrepaidDao.findPrePaidInfo(prePaidInfo);
		if (null == tempPrePaid) {
			aPrepaidDao.insertPrePaidInfo(prePaidInfo);
		} else {
			ret = 1;
		}
		return ret;
	}
	
	/**
	 * 更新预付费信息
	 * @param accountPreiod
	 * @param staffId
	 * @param staffName
	 * @throws Exception
	 */
	public int updatePrePaidInfo(PrePaidInfo prePaidInfo,String staffId,String staffName,String otherInfo)throws Exception{
		int ret =0;
		int state = Integer.parseInt(prePaidInfo.getState());
		PrePaidInfoDao aPrepaidDao = (PrePaidInfoDao)BeanLocator.getInstance().getBean(getPrePaidDao());
		String strLogType = null;
		switch (state){
		case 0:  //审批通过,先删除后插入,同时删除待审批数据,待办未做
			aPrepaidDao.deletePrePaidInfo(prePaidInfo);
			aPrepaidDao.insertPrePaidInfo(prePaidInfo);			
			aPrepaidDao.deletePrePaidInfoByID(prePaidInfo.getSeqId());
			strLogType = "审批数据";
			break;
		case 1://没有提交的数据修改		
			aPrepaidDao.updatePrePaidInfo(prePaidInfo);
			strLogType = "修改临时数据";
			break;
		case 2://提交数据只修改状态
			aPrepaidDao.updatePrePaidInfo(prePaidInfo);
			strLogType = "提交临时数据";
			break;
		case 3://审批不通过数据只修改状态
			aPrepaidDao.updatePrePaidInfoByState(prePaidInfo);
			strLogType = "审批不通过数据";
			break;
		case 4://废弃数据只修改状态
			aPrepaidDao.deletePrePaidInfo(prePaidInfo);
			aPrepaidDao.updatePrePaidInfoByState(prePaidInfo);
			strLogType = "废弃数据";
			break;
		case 5://锁定数据只修改状态
			aPrepaidDao.updatePrePaidInfoByState(prePaidInfo);
			strLogType = "锁定数据";
			break;
		case 6 ://解锁数据，只修改状态
			
			prePaidInfo.setState(new Integer(StateConstants.STATE_0).toString());
			
			aPrepaidDao.updatePrePaidInfoByState(prePaidInfo);
			strLogType = "解锁数据";
		default:
			ret =1;
		}
		
		if(null != staffId){//不是随主记录操作，需要记录操作日志
			OperLogDao logDao = (OperLogDao)BeanLocator.getInstance().getBean(getLogDao());
			OperLog log = new OperLog();
			log.setStaffId(staffId);
			log.setStaffName(staffName);
			log.setOperTime(new Date());
			log.setOperObject("结算信息");
			log.setOperKey(prePaidInfo.getBaseSeqId());
			log.setOperType(strLogType);
			if(otherInfo != null){
				log.setOperContent(otherInfo);
			}
			logDao.insertOperLog(log);
		}
		return ret;
	}
	
	/**
	 * 获取单个账期信息，可以通过commcode,state 来唯一定位 
	 * @param prePaidInfo
	 * @return
	 * @throws Exception
	 */
	public PrePaidInfo getPrePaidInfo(PrePaidInfo prePaidInfo)throws Exception{
		PrePaidInfoDao aPrepaidDao = (PrePaidInfoDao) BeanLocator
		.getInstance().getBean(
				getPrePaidDao());
		return aPrepaidDao.findPrePaidInfo(prePaidInfo);
	}
	
	/**
	 * 获取账期列表信息，一个客户可能会查询出多个账期信息，主要的区别在于状态
	 * @param accountPreiod
	 * @return
	 * @throws Exception
	 */
	public List<PrePaidInfo> listPrePaidInfo(PrePaidInfo prePaidInfo)throws Exception{
		PrePaidInfoDao aPrepaidDao = (PrePaidInfoDao) BeanLocator
		.getInstance().getBean(
				getPrePaidDao());
		return aPrepaidDao.listPrePaidInfo(prePaidInfo);
	}
}
