/**
 * system ：hbs
 * desc:    银行信息的接口类，定义统一方法供客户和供应商使用
 * version: 1.0
 * author : yangzj
 */
package com.hbs.common.manager.baseinfo;


import java.util.List;

import com.hbs.common.springhelper.BeanLocator;
import com.hbs.customer.common.constants.StateConstants;

import com.hbs.domain.common.dao.baseinfo.BankInfoDao;

import com.hbs.domain.common.pojo.baseinfo.BankInfo;


/**
 * 本类及实现的子类供客户基本信息和供应商基本信息调用
 * action一层不需要调用，action面对的只是客户基本信息
 * 服务类和供应商基本信息服务类
 */
public abstract class BankInfoMgr {

	/**
	 * 抽象方法，子类实现
	 * @return
	 */
	public abstract String getBankInfoDao();
	
	/**
	 *  抽象方法，子类实现
	 * @return
	 */
	public abstract String getLogDao();
	/**
	 * 插入银行信息,银行信息的插入，
	 * 客户信息同时提交，则跟随客户信息的状态	 
	 * @param bankInfo	 
	 * @return  0--成功 1---数据库已经存在重复数据
	 * @throws Exception
	 */
	public int insertBankInfo(BankInfo bankInfo) throws Exception{
		int ret =0;
		BankInfoDao bankInfoDao =(BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		BankInfo bInfo = bankInfoDao.findBankInfo(bankInfo);
		if(null == bInfo){
			bankInfoDao.insertBankInfo(bankInfo);
		}else{
			ret = 1;
		}
		return ret;
	}
	/**
	 * 批量插入银行信息
	 * @param bankInfoList
	 * @param staffId
	 * @param staffName
	 * @return
	 * @throws Exception
	 */
	public int insertBankInfoList(List<BankInfo> bankInfoList) throws Exception{
		int ret =0;
		for(BankInfo bankInfo : bankInfoList){
			insertBankInfo(bankInfo);
		}
		return ret;
	}
	/**
	 * 删除正式信息及临时信息，供基本信息调用
	 * @param bankInfo
	 * @param isDelCurrent  是否同时删除本次操作的数据
	 * @throws Exception
	 */
	public void deleteBankInfo(BankInfo bankInfo,boolean isDelCurrent)throws Exception{
		BankInfoDao bankInfoDao = (BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		bankInfoDao.deleteBankInfo(bankInfo);
		if(isDelCurrent){
			bankInfoDao.deleteBankInfoByID(bankInfo.getSeqId());
		}		
	}
	/**
	 * 更新银行信息
	 * @param bankInfo
	 * @param state
	 * @param staffId
	 * @param staffName
	 * @throws Exception
	 */
	public int updateBankInfo(BankInfo bankInfo)throws Exception{
		int ret =0;
		int state = Integer.parseInt(bankInfo.getState());
		BankInfoDao bankInfoDao = (BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		//String strLogType = null;
		switch (state){
		case 0:  //审批通过,先删除后插入,同时删除待审批数据
		         //删除操作由基本信息类删除
			//bankInfoDao.deleteBankInfo(bankInfo);
			bankInfoDao.insertBankInfo(bankInfo);			
			//bankInfoDao.deleteBankInfoByID(bankInfo.getSeqId());
			//strLogType = "审批数据";
			break;
		case 1://没有提交的数据修改		
			bankInfoDao.updateBankInfo(bankInfo);
			//strLogType = "修改临时数据";
			break;
		case 2://提交数据只修改状态
			bankInfoDao.updateBankInfo(bankInfo);
			//strLogType = "提交临时数据";
			break;
		case 3://审批不通过数据只修改状态
			bankInfoDao.updateBankInfoByState(bankInfo);
			//strLogType = "审批不通过数据";
			break;
		case 4://废弃数据，把已经废弃的数据清除掉，同时如果把本次废弃的数据状态置为废弃
			//删除废弃数据由基本信息操作
			//bankInfoDao.deleteBankInfo(bankInfo);
			bankInfoDao.updateBankInfoByState(bankInfo);
			//strLogType = "废弃数据";
			break;
		case 5://锁定数据只修改状态
			bankInfoDao.updateBankInfoByState(bankInfo);
			//strLogType = "锁定数据";
			break;
			
		case 6 ://解锁数据，只修改状态
			
			bankInfo.setState(new Integer(StateConstants.STATE_0).toString());
			
			bankInfoDao.updateBankInfoByState(bankInfo);
			//strLogType = "解锁数据";
		default:
			ret =1;
		}
//		if(null != staffName){
//			operLog( staffId, staffName, strLogType, bankInfo.getBaseSeqId(), otherInfo);
//		}
		return ret;
	}
	
	/**
	 * 批量更新银行信息
	 * @param bankInfoList
	 * @param staffId
	 * @param staffName
	 * @param otherInfo
	 * @return
	 * @throws Exception
	 */
	public int updateBankInfoList(List<BankInfo> bankInfoList ) throws Exception{
		int ret =0;
		for(BankInfo bankInfo : bankInfoList){
			updateBankInfo( bankInfo);
		}
		return ret;
	}
	
	/**
	 * 记录操作日志
	 * @param staffId
	 * @param staffName
	 * @param logType
	 * @param operKey
	 * @param otherInfo
	 */
//	private void operLog(String staffId,String staffName,String logType,String operKey,String otherInfo){
//		OperLogDao logDao = (OperLogDao)BeanLocator.getInstance().getBean(getLogDao());
//		OperLog log = new OperLog();
//		log.setStaffId(staffId);
//		log.setStaffName(staffName);
//		log.setOperTime(new Date());
//		log.setOperObject("银行信息");
//		log.setOperKey(operKey);
//		log.setOperType(logType);
//		if(otherInfo != null){
//			log.setOperContent(otherInfo);
//		}
//		logDao.insertOperLog(log);
//	}
	
	/**
	 * 查询银行信息，以主键查询
	 * @param bankInfo 
	 * @return
	 * @throws Exception
	 */
	public BankInfo getBankInfo(BankInfo bankInfo) throws Exception{
		BankInfoDao bankInfoDao = (BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		return bankInfoDao.findBankInfo(bankInfo);
	}
	
	public BankInfo getBankInfoById(String seqId) throws Exception{
		BankInfoDao bankInfoDao = (BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		return bankInfoDao.findBankInfoById(seqId);
	}
	/**
	 * 查询银行信息列表
	 * @param bankInfo
	 * @return
	 * @throws Exception
	 */
	public List<BankInfo> listBankInfo(BankInfo bankInfo) throws Exception{
		BankInfoDao bankInfoDao = (BankInfoDao)BeanLocator.getInstance().getBean(getBankInfoDao());
		return bankInfoDao.listBankInfo(bankInfo);
	}
}
