/**
 * 
 */
package com.hbs.warehousesend.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hbs.common.action.FieldErr;
import com.hbs.common.utils.ListDataUtil;
import com.hbs.customerinfo.action.CustomerInfoUtil;
import com.hbs.domain.warehouse.pojo.WarehouseSendDetail;
import com.hbs.domain.warehouse.pojo.WarehouseSendInfo;

/**
 * @author xyf
 *
 */
public class WarehouseSendUtil {
	private static final Logger logger = Logger.getLogger(WarehouseSendUtil.class);
	private static final String detailListName = "orderlist";
	private static final String detailListFields = "orderlistFields";

	public static boolean checkKeyFields(WarehouseSendInfo warehouseSend) {
		if(warehouseSend == null)
			return false;
		if(StringUtils.isEmpty(warehouseSend.getCustCode())
				|| StringUtils.isEmpty(warehouseSend.getSendPoNo())
				|| StringUtils.isEmpty(warehouseSend.getPoNoType())){
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public static void processListData(WarehouseSendInfo warehouseSend,
			HttpServletRequest request, Map otherData) {
		try {
			List<WarehouseSendDetail> list = ListDataUtil.splitIntoList(WarehouseSendDetail.class, 
				request.getParameterValues(detailListName), 
				request.getParameter(detailListFields).split(CustomerInfoUtil.fieldNameSplitter), 
				CustomerInfoUtil.splitter);
			warehouseSend.setDetailList(list);
			
			if(list.size() <= 0)
				return;
			
			String custCode = warehouseSend.getCustCode();
			String sendPoNo = warehouseSend.getSendPoNo();
			String shortName = warehouseSend.getShortName();
			String houseType = warehouseSend.getHouseType();
			String settlementType = warehouseSend.getSettlementType();
			String poNoType = warehouseSend.getPoNoType();
			String staffId = warehouseSend.getOperId();
			String staffName = warehouseSend.getOperStaff();
			
			for(WarehouseSendDetail detail : list){
				if(detail == null)
					continue;
				detail.setVendorCode(custCode);
				detail.setSendPoNo(sendPoNo);
				detail.setShortName(shortName);
				detail.setHouseType(houseType);
				detail.setSettlementType(settlementType);
				detail.setPoNoType(poNoType);
				detail.setStaffId(staffId);
				detail.setStaffName(staffName);
				if(detail.getCreateTime()== null){
					detail.setCreateTime(new Date());
				}
				if(detail.getSendSeqId() == null){
					detail.setActiveState("ACTIVE");
					detail.setFinanceState("0");
				}
				if(detail.getCommAmount() == null){
					detail.setCommAmount(0);
				}
				if(detail.getSelfAmount() == null){
					detail.setSelfAmount(0);
				}
				detail.setAmount(detail.getCommAmount() + detail.getSelfAmount());
			}
		} catch (Exception e) {
			logger.info("processListData处理detailList出错", e);
		}

	}

	@SuppressWarnings("unchecked")
	public static List<FieldErr> checkInputFields(
			WarehouseSendInfo warehouseSend, Map otherData) {
		List<FieldErr> list = new ArrayList<FieldErr>();
		
		if(StringUtils.isEmpty(warehouseSend.getCustCode()))
			list.add(new FieldErr("CustCode","客户编码没有填写"));
		//新的出库单可能存在没有出货单号，出货单号由后台产生的
//		if(StringUtils.isEmpty(warehouseSend.getSendPoNo()))
//			list.add(new FieldErr("SendPoNo","SendPoNo没有填写"));
		if(StringUtils.isEmpty(warehouseSend.getShortName()))
			list.add(new FieldErr("ShortName","客户简称没有填写"));
		if(warehouseSend.getCreateDate() == null)
			list.add(new FieldErr("CreateDate","出库日期没有填写"));
		if(StringUtils.isEmpty(warehouseSend.getHouseType()))
			list.add(new FieldErr("HouseType","出库仓库没有填写"));
		if(StringUtils.isEmpty(warehouseSend.getSettlementType()))
			list.add(new FieldErr("SettlementType","SettlementType没有填写"));
		if(StringUtils.isEmpty(warehouseSend.getPoNoType()))
			list.add(new FieldErr("PoNoType","PoNoType没有填写"));
		
		
		for(WarehouseSendDetail detail : warehouseSend.getDetailList()){
			if(detail == null)
				continue;
			if(StringUtils.isEmpty(detail.getCustPartNo()))
				list.add(new FieldErr("CustPartNo","客户 P/N没有填写"));
			if(StringUtils.isEmpty(detail.getPartNo()))
				list.add(new FieldErr("PartNo","公司 P/N没有填写"));
			if(StringUtils.isEmpty(detail.getPnDesc()))
				list.add(new FieldErr("PnDesc","物料描述没有填写"));
			if(detail.getAmount() == null)
				list.add(new FieldErr("Amount","出库数量没有填写"));
			if(detail.getTaxRate() == null)
				list.add(new FieldErr("TaxRate","税率没有填写"));
			if(StringUtils.isEmpty(detail.getIsTax()))
				list.add(new FieldErr("IsTax","IsTax没有填写"));
		}
		return list;
	}

}
