package com.hbs.vendorinfo.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hbs.common.action.FieldErr;
import com.hbs.domain.auth.pojo.Staff;
import com.hbs.common.manager.configencode.ConfigEncodeMgr;
import com.hbs.common.utils.ListDataUtil;
import com.hbs.common.utils.StaffUtil;
import com.hbs.domain.common.pojo.ConfigEncode;
import com.hbs.domain.common.pojo.baseinfo.AccountPreiod;
import com.hbs.domain.common.pojo.baseinfo.BankInfo;
import com.hbs.domain.common.pojo.baseinfo.ContactInfo;
import com.hbs.domain.common.pojo.baseinfo.PrePaidInfo;
import com.hbs.domain.vendor.vendorinfo.pojo.VendorInfo;
import com.hbs.vendorinfo.manager.VendorInfoMgr;

/**
 * Action中对CustomerInfo的一些通用处理函数集
 * @author xyf
 * modified by yangzj 20100317
 *
 */
public class VendorInfoUtil {

	/**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(VendorInfoUtil.class);
    
	/**
	 * 判断是否填写了key字段。vendorInfo.baseSeqId 或 (vendorInfo.commCode + vendorInfo.state)
	 * @param vendorInfo
	 * @return
	 */
	public static boolean checkKeyFields(VendorInfo vendorInfo)
	{
		boolean ret = true;
		try
		{
			if(null == vendorInfo){
				ret = false;
			}else{
				if(vendorInfo.getState() == null){
					vendorInfo.setState("0");
				}
				Integer i = vendorInfo.getBaseSeqId();
				String code = vendorInfo.getCommCode();
				String shortName = vendorInfo.getShortName();
				if((null == i || i.intValue() == 0) && (StringUtils.isEmpty(code))&& (StringUtils.isEmpty(shortName))){
					ret = false;
				}
			}
		}
		catch(Exception e)
		{
			ret = false;
		}
		
		return ret;
	}
	
	/**
	 * 判断是否填写了key字段。vendorInfo.baseSeqId 或 vendorInfo.commCode
	 * @param vendorInfo
	 * @return
	 */
	public static boolean checkKeyFields2(VendorInfo vendorInfo) {
		try
		{
			if(vendorInfo == null)
				return false;
			Integer i = vendorInfo.getBaseSeqId();
			if(i != null && !i.equals(0))
				return true;
			String s = vendorInfo.getCommCode();
			if(StringUtils.isEmpty(s))
				return false;
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

	/**
	 * 对输入的供应商信息进行校验，内部调用checkSelectFields。
	 * @param vendorInfo	供应商信息
	 * @return 出错信息，格式：Map<出错字段,出错信息>
	 */
	public static List<FieldErr> checkInputFields(VendorInfo vendorInfo) throws Exception
	{
		if(vendorInfo == null)
			return new ArrayList<FieldErr>();
		
		List<FieldErr> list = checkSelectFields(vendorInfo);
		if(list == null)
			list = new ArrayList<FieldErr>();
		
		String s;
		// DONE:完成checkInputFields，对输入的供应商信息进行校验
		/*
		s = vendorInfo.getVendorCode();
		if(StringUtils.isEmpty(s))
		{
			list.add(new FieldErr("venderCode","venderCode没有填写"));
		}
		*/
//		s = vendorInfo.getCommCode();
//		if(StringUtils.isEmpty(s))
//		{
//			list.add(new FieldErr("CommCode","CommCode没有填写"));
//		}
		s = vendorInfo.getShortName();
		if(StringUtils.isEmpty(s))
		{
			list.add(new FieldErr("ShortName","供应商简称没有填写"));
		}
		s = vendorInfo.getAllName();
		if(StringUtils.isEmpty(s))
		{
			list.add(new FieldErr("AllName","中文名称没有填写"));
		}
		s = vendorInfo.getIsShowPrice();
		if(StringUtils.isEmpty(s))
		{
			list.add(new FieldErr("IsShowPrice","是否显示单价没有填写"));
		}
		
		Integer baseSeqId = vendorInfo.getBaseSeqId();
		if(baseSeqId != null){
			s = vendorInfo.getCommCode();
			if(StringUtils.isEmpty(s))
			{
				list.add(new FieldErr("CommCode","供应商编码没有填写"));
			}
		}
		String settleMentType = vendorInfo.getSettlementType();
		if(settleMentType.equals("1")){
			vendorInfo.setPrePaidInfo(null);
		}else{
			vendorInfo.setAccountPreiod(null);
		}
		AccountPreiod accountPreiod = vendorInfo.getAccountPreiod();
		if(accountPreiod != null) {
			accountPreiod.setCommCode(vendorInfo.getCommCode());
			accountPreiod.setState(vendorInfo.getState());
			
			accountPreiod.setBaseSeqId(baseSeqId == null ? null : baseSeqId.toString());
			
			accountPreiod.setAccounDay("1");//对账日，缺省1
			accountPreiod.setAccountType("1");//账期类型，缺省1 月结
			accountPreiod.setAccountPeriod("1"); //账期  缺省1  一个月
			accountPreiod.setReminderDay("2"); //提醒日
		}
		PrePaidInfo prePaidInfo = vendorInfo.getPrePaidInfo();
		if(prePaidInfo != null) {
			prePaidInfo.setCommCode(vendorInfo.getCommCode());
			prePaidInfo.setState(vendorInfo.getState());
			
			prePaidInfo.setBaseSeqId(baseSeqId == null ? null : baseSeqId.toString());
			
		
		}
		return list;
	}
		
	/**
	 * 判断是否需要设置staffId
	 * @param vendorInfo
	 */
	public static boolean checkSetStaffId(VendorInfo vendorInfo)
	{
		int userid = 0;
		try
		{
			String s = vendorInfo.getStaffId();
			userid = Integer.parseInt(s);
		}
		catch(NumberFormatException e)
		{
			userid = 0;
		}
		return (userid == 0);
	}

	/**
	 * 联系人列表字符串参数名
	 */
	private static final String contactListName1 = "contactlist";
	/**
	 * 收货人列表字符串参数名
	 */
	private static final String contactListName2 = "consigneelist";
	/**
	 * 银行列表字符串参数名
	 */
	private static final String bankListName = "custbanklist";
	/**
	 * 联系人列表字符串对应字段名
	 */
	
	public static final String splitter = "\\|\\|;;";
	
	public static final String fieldNameSplitter = ",";
	private static final String contactListFields1 = "contactlistFields";
	private static final String contactListFields2 = "consigneelistFields";
	private static final String bankListFields = "custbanklistFields";
	
	/**
	 * 处理上传的List数据。将String数组转换为List
	 */
	@SuppressWarnings("unchecked")
	public static void processListData(VendorInfo vendorInfo, HttpServletRequest request) throws Exception
	{
		// Done: 处理上传的List数据
		
			Integer id = vendorInfo.getBaseSeqId();
			String baseSeqId = id == null ? null : id.toString();
			String commCode = vendorInfo.getCommCode();
			String state = vendorInfo.getState();
			List<ContactInfo> listAll = new ArrayList<ContactInfo>();
			
				logger.debug("联系人信息值数据：" + request.getParameterValues(contactListName1));
				logger.debug("联系人信息字段值："+request.getParameter(contactListFields1));
				
				List<ContactInfo> listCon = ListDataUtil.splitIntoList(ContactInfo.class, 
						request.getParameterValues(contactListName1), 
						request.getParameter(contactListFields1).split(fieldNameSplitter), 
						splitter);
				if(null != listCon && listCon.size()>0 ){
					logger.debug("联系人信息数量为：" + listCon.size());
					for(ContactInfo info : listCon){
						info.setBaseSeqId(baseSeqId);
						info.setCommCode(commCode);
						info.setState(state);
						info.setConType("1");
					}
					listAll.addAll(listCon);					
				}else{
					logger.debug("联系人信息数量为：0");
				}
			
			
			
				logger.debug("收货人信息值数据：" + request.getParameterValues(contactListName2));
				logger.debug("收货人信息字段值："+request.getParameter(contactListFields2));
				
				List<ContactInfo> list = ListDataUtil.splitIntoList(ContactInfo.class, 
						request.getParameterValues(contactListName2), 
						request.getParameter(contactListFields2).split(fieldNameSplitter), 
						splitter);
				if(null != list && list.size()>0 ){
					logger.debug("收货人信息数量为：" + list.size());
					for(ContactInfo info : list){
						info.setBaseSeqId(baseSeqId);
						info.setCommCode(commCode);
						info.setState(state);
						info.setConType("2");
					}
					listAll.addAll(list);					
				}else{
					logger.debug("收货人信息数量为：0");
				}
				
			
			if(listAll.size()>0){
				vendorInfo.setListContactInfo(listAll);
			}
			
				logger.debug("银行信息值数据：" + request.getParameterValues(bankListName));
				logger.debug("银行信息字段值："+request.getParameter(bankListFields));
				
				List<BankInfo> listBank = ListDataUtil.splitIntoList(BankInfo.class, 
						request.getParameterValues(bankListName), 
						request.getParameter(bankListFields).split(fieldNameSplitter), 
						splitter);
				if(null != listBank && listBank.size() >0){
					logger.debug("银行信息数量为：" + listBank.size());
					for(BankInfo info : listBank){
						info.setBaseSeqId(baseSeqId);
						info.setCommCode(commCode);
						info.setState(state);
					}
					vendorInfo.setListBankInfo(listBank);
				}else{
					logger.debug("银行信息数量为：0");
				}
	}
	
	/**
	 * 检查选择数据。检查选项值，填写选项的说明字段
	 * @param vendorInfo
	 */
	public static List<FieldErr> checkSelectFields(VendorInfo vendorInfo) throws Exception
	{
		if(vendorInfo == null)
			return null;
		List<FieldErr> list = new ArrayList<FieldErr>();
		
		
			String s;
			ConfigEncode ce;
			s = vendorInfo.getImportantCode();
			if(StringUtils.isNotEmpty(s))
			{
				ce = getEncode("IMPORTANT_CODE", s);
				if(ce == null)
					list.add(new FieldErr("importantCode", "重要程度的值不正确"));
				else
				{
				
					vendorInfo.setImportantDesc(ce.getEncodeValue());
				}
			}
				
			s = vendorInfo.getCreditRate();
			// 没有填写CreditRate，缺省为一般（3）
			if(StringUtils.isEmpty(s))
				s = "3";
			//if(StringUtils.isNotEmpty(s))
			{
				ce = getEncode("CREDIT_RATE", s);
				if(s == null)
					list.add(new FieldErr("CreditRate", "信用度的值不正确"));
				else
				{
					
					vendorInfo.setCreditDesc(ce.getEncodeValue());
				}
			}
				
			s = vendorInfo.getSettlementType();
			if(StringUtils.isNotEmpty(s))
			{
				ce = getEncode("SETTLEMENT_TYPE", s);
				if(s == null)
					list.add(new FieldErr("SettlementType", "结算类型的值不正确"));
				else{
					
					vendorInfo.setSettlementDesc(ce.getEncodeValue());
				}
			}
			
			s = vendorInfo.getCurrency();
			if(StringUtils.isNotEmpty(s))
			{
				ce = getEncode("CURRENCY", s);
				if(s == null)
					list.add(new FieldErr("Currency", "结算币种的值不正确"));
				else
				{
					
					vendorInfo.setCurrencyDesc(ce.getEncodeValue());
				}
			}
			
			s = vendorInfo.getIsShowPrice();
			if(StringUtils.isNotEmpty(s))
			{
				ce = getEncode("IS_SHOW_PRICE", s);
				if(s == null)
					list.add(new FieldErr("IsShowPrice", "是否显示单价的值不正确"));
				
			}

			s = vendorInfo.getStaffId();
			if(StringUtils.isNotEmpty(s))
			{
				Staff u = StaffUtil.getStaffById(s);
				if(u == null)
					list.add(new FieldErr("StaffId", "操作人员错误"));
				else
					vendorInfo.setStaffName(u.getStaffName());
			}
		
		
		return list;
	}
	
	/**
	 * 获取编码。如果s可以解析为数值，则将s当作key，否则当作value
	 * @param type
	 * @param s
	 * @return 编码对象，如果找不到，则返回null
	 */
	private static ConfigEncode getEncode(String type, String s)
	{
		ConfigEncode ce = new ConfigEncode();
		
		ce.setEncodeType(type);		
		ce.setEncodeKey(s);
		
		
		ce = ConfigEncodeMgr.getConfigEncode(ce);
		
		return ce;
	}
	
	/**
	 * 获取供应商信息详情，将联系人与收货人分开。
	 * @param mgr
	 * @param vendorInfo
	 * @return
	 */
	public static VendorInfo getVendorInfo(VendorInfoMgr mgr, VendorInfo vendorInfo) {
		try {
			vendorInfo = mgr.getVendorInfo(vendorInfo, true);
			if(vendorInfo == null || vendorInfo.getListContactInfo() == null)
				return vendorInfo;
			Iterator<ContactInfo> it = vendorInfo.getListContactInfo().iterator();
			List<ContactInfo> l1 = new ArrayList<ContactInfo>();
			List<ContactInfo> l2 = new ArrayList<ContactInfo>();
			while(it.hasNext()) {
				ContactInfo info = it.next();
				if(info == null)
					continue;
				if(info.getConType().equals("1")) {
					l1.add(info);
				} else {
					l2.add(info);
				}
			}
			vendorInfo.setField(contactListName1, l1);
			vendorInfo.setField(contactListName2, l2);
		} catch (Exception e) {
		}
		return vendorInfo;
	}

}
