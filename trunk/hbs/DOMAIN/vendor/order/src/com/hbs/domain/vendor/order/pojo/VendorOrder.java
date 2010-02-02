package com.hbs.domain.vendor.order.pojo;

import java.util.Date;
import java.util.List;

/**
 * VendorOrder����.
 * @author hbs
 *
 */
public class VendorOrder {
    
    /**
     * ��Ӧ�̱���.
     */
    private String commCode;
    
    /**
     * ��Ӧ�̶������.
     */
    private String poNo;
    
    /**
     * ��Ӧ�̶�������
        0----�ͻ��ɹ���
        1---�˻���
        2---���汸���ɹ���
        3--�ض��ͻ������ɹ���
     */
    private String poNoType;
    
    /**
     * ���.
     */
    private String shortName;
    
    /**
     * ��������.
     */
    private Date createTime;
    
    /**
     * ��ϵ�ˣ���Ӧ��ϵ���е�����ϵ��.
     */
    private String conName;
    
    /**
     * �绰����Ӧ����ϵ�˵ĵ绰.
     */
    private String conTel;
    
    /**
     * ���棬��Ӧ����ϵ�˵Ĵ���.
     */
    private String conFax;
    
    /**
     * ��Ӧ��Ϣ�ж�Ӧ�ķֹ�˾���֧����.
     */
    private String companyBranch;
    
    /**
     * ���㷽ʽ����Ӧ��Ϣ�еĽ��㷽ʽ.
     */
    private String SettlementType;
    
    /**
     * �ջ��ˣ���Ӧ��Ϣ���ջ���.
     */
    private String ReceiveName;
    
    /**
     * �ջ���ַ����Ӧ��Ϣ���ջ���ַ.
     */
    private String receiveAddress;
    
    /**
     * �ջ��ʱ࣬��Ӧ��Ϣ�е��ջ��ʱ�.
     */
    private String receiveZip;
    
    /**
     * ¼����ID.
     */
    private String staffId;
    
    /**
     * ¼�붩������.
     */
    private String staffName;
    
    /**
     * �������۵�������Ա����Ӧ��Ӧ�̵�������Ա.
     */
    private String sales;
    
    /**
     * �ͻ������Ƿ���ʾ���ۣ���̨���ݿͻ���Ϣ�Զ�������.
     */
    private String isShowPrice;
    
    /**
     * ��ʽ�γɶ�������.
     */
    private Date orderTime;
    
    /**
     * ������������.
     */
    private String period;
    
    /**
     * ������ҵ��״̬.
     */
    private String state;
    
    /**
     * �����Ļ״̬.
     */
    private String activeState="active";
    
    /**
     * ���0----�ͻ��ɹ�����3-- �ض��ͻ�������Ч
     */
    private String custCcode;

    public String getCustCcode() {
		return custCcode;
	}

	public void setCustCcode(String custCcode) {
		this.custCcode = custCcode;
	}

	/**
     * ������ϸ�б�
     */
    private List<VendorOrderDetail> vendorOrderDetailList;
    
    public List<VendorOrderDetail> getVendorOrderDetailList() {
		return vendorOrderDetailList;
	}

	public void setVendorOrderDetailList(
			List<VendorOrderDetail> vendorOrderDetailList) {
		this.vendorOrderDetailList = vendorOrderDetailList;
	}

	public String getCommCode() {
        return this.commCode;
    }	
  
    public void setCommCode(String commCode) {
        this.commCode = commCode;
    }
    
    public String getPoNo() {
        return this.poNo;
    }	
  
    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }
    
    public String getPoNoType() {
        return this.poNoType;
    }	
  
    public void setPoNoType(String poNoType) {
        this.poNoType = poNoType;
    }
    
    public String getShortName() {
        return this.shortName;
    }	
  
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }	
  
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getConName() {
        return this.conName;
    }	
  
    public void setConName(String conName) {
        this.conName = conName;
    }
    
    public String getConTel() {
        return this.conTel;
    }	
  
    public void setConTel(String conTel) {
        this.conTel = conTel;
    }
    
    public String getConFax() {
        return this.conFax;
    }	
  
    public void setConFax(String conFax) {
        this.conFax = conFax;
    }
    
    public String getCompanyBranch() {
        return this.companyBranch;
    }	
  
    public void setCompanyBranch(String companyBranch) {
        this.companyBranch = companyBranch;
    }
    
    public String getSettlementType() {
        return this.SettlementType;
    }	
  
    public void setSettlementType(String SettlementType) {
        this.SettlementType = SettlementType;
    }
    
    public String getReceiveName() {
        return this.ReceiveName;
    }	
  
    public void setReceiveName(String ReceiveName) {
        this.ReceiveName = ReceiveName;
    }
    
    public String getReceiveAddress() {
        return this.receiveAddress;
    }	
  
    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
    
    public String getReceiveZip() {
        return this.receiveZip;
    }	
  
    public void setReceiveZip(String receiveZip) {
        this.receiveZip = receiveZip;
    }
    
    public String getStaffId() {
        return this.staffId;
    }	
  
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    
    public String getStaffName() {
        return this.staffName;
    }	
  
    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    
    public String getSales() {
        return this.sales;
    }	
  
    public void setSales(String sales) {
        this.sales = sales;
    }
    
    public String getIsShowPrice() {
        return this.isShowPrice;
    }	
  
    public void setIsShowPrice(String isShowPrice) {
        this.isShowPrice = isShowPrice;
    }
    
    public Date getOrderTime() {
        return this.orderTime;
    }	
  
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
    
    public String getPeriod() {
        return this.period;
    }	
  
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public String getState() {
        return this.state;
    }	
  
    public void setState(String state) {
        this.state = state;
    }
    
    public String getActiveState() {
        return this.activeState;
    }	
  
    public void setActiveState(String activeState) {
        this.activeState = activeState;
    }

    public String getLogKey(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(this.commCode).append(";").append(this.poNo);
    	return sb.toString();
    }
}