package com.hbs.domain.vendor.order.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.hbs.domain.common.pojo.base.BaseDomain;

/**
 * VendorOrderDetail����.
 * @author hbs
 *
 */
public class VendorOrderDetail extends BaseDomain{
    
    /**
     * Ψһ��seqid�����кţ�.
     */
    private Integer operSeqId;
    
    /**
     * ��Ӧ�̱���.
     */
    private String commCode;
    
    /**
     * ���.
     */
    private String shortName;
    
    /**
     * ��Ӧ�̶�������
        0----�ͻ��ɹ���
        1---�˻���
        2---���汸���ɹ���
        3--�ض��ͻ������ɹ���
     */
    private String poNoType;
    
    /**
     * �������.
     */
    private String poNo;
    
    /**
     * ���ϱ��.
     */
    private String cpartNo;
    
    /**
     * ��˾���ϱ��.
     */
    private String partNo;
    
    /**
     * ��������.
     */
    private String pnDesc;
    
    /**
     * ����.
     */
    private BigDecimal cprice;
    
    /**
     * �Ƿ�˰����1--��0--����������Ǻ�˰�ģ���һ����1���������ѡ���Ƿ�˰����.
     */
    private String isTax;
    
    /**
     * ˰��.
     */
    private BigDecimal taxRate ;
    
    /**
     * ���ⱸע�����еĿͻ������������θ������������ֶ�.
     */
    private String specDesc;
    
    /**
     * һ�㱸ע.
     */
    private String commDesc;
    
    /**
     * ��������.
     */
    private Integer amount =0;
    
    /**
     * �ܽ��.
     */
    private BigDecimal money;
    
    /**
     * �Ѿ��ջ�����.
     */
    private Integer deliveryAmount;
    
    /**
     * ����ԭʼ��������.
     */
    private Date orgDeliveryDate;
    
    /**
     * �ɹ�������ȷ�Ͻ�������.
     */
    private Date verDeliveryDate;
    
    /**
     * ������ϸ��������.
     */
    private String period;
    
    /**
     * �����Ŀͻ������ţ������ж���ͻ��������ţ���.
     */
    private String rltOrderPoNo;
    
    /**
     * ������ϸ״̬.
     */
    private String state;
    
    /**
     * �����Ļ״̬.
     */
    private String activeState="active";
    
    /**
     * ��������ⵥ�ţ������ж������.
     */
    private String rltRecPoNo;


    /**
     * ������ԱID
     */
    private String staffId;
    
    /**
     * ������Ա����
     */
    private String staffName;
    
    /**
     * ���㷽ʽ
     */
    private String SettlementType;
    
    
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
	 * @return the staffId
	 */
	public String getStaffId() {
		return staffId;
	}

	/**
	 * @param staffId the staffId to set
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * @return the staffName
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * @param staffName the staffName to set
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * @return the settlementType
	 */
	public String getSettlementType() {
		return SettlementType;
	}

	/**
	 * @param settlementType the settlementType to set
	 */
	public void setSettlementType(String settlementType) {
		SettlementType = settlementType;
	}

	public Integer getOperSeqId() {
        return this.operSeqId;
    }	
  
    public void setOperSeqId(Integer operSeqId) {
        this.operSeqId = operSeqId;
    }
    
    public String getCommCode() {
        return this.commCode;
    }	
  
    public void setCommCode(String commCode) {
        this.commCode = commCode;
    }
    
    public String getShortName() {
        return this.shortName;
    }	
  
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public String getPoNoType() {
        return this.poNoType;
    }	
  
    public void setPoNoType(String poNoType) {
        this.poNoType = poNoType;
    }
    
    public String getPoNo() {
        return this.poNo;
    }	
  
    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }
    
    public String getCpartNo() {
        return this.cpartNo;
    }	
  
    public void setCpartNo(String cpartNo) {
        this.cpartNo = cpartNo;
    }
    
    public String getPartNo() {
        return this.partNo;
    }	
  
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public String getPnDesc() {
        return this.pnDesc;
    }	
  
    public void setPnDesc(String pnDesc) {
        this.pnDesc = pnDesc;
    }
    
    public BigDecimal getCprice() {
        return this.cprice;
    }	
  
    public void setCprice(BigDecimal cprice) {
        this.cprice = cprice;
    }
    
    public String getIsTax() {
        return this.isTax;
    }	
  
    public void setIsTax(String isTax) {
        this.isTax = isTax;
    }
    
    public BigDecimal getTaxRate() {
        return this.taxRate;
    }	
  
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
    public String getSpecDesc() {
        return this.specDesc;
    }	
  
    public void setSpecDesc(String specDesc) {
        this.specDesc = specDesc;
    }
    
    public String getCommDesc() {
        return this.commDesc;
    }	
  
    public void setCommDesc(String commDesc) {
        this.commDesc = commDesc;
    }
    
    public Integer getAmount() {
        return this.amount;
    }	
  
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public BigDecimal getMoney() {
        return this.money;
    }	
  
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    
    public Integer getDeliveryAmount() {
        return this.deliveryAmount;
    }	
  
    public void setDeliveryAmount(Integer deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }
    
    public Date getOrgDeliveryDate() {
        return this.orgDeliveryDate;
    }	
  
    public void setOrgDeliveryDate(Date orgDeliveryDate) {
        this.orgDeliveryDate = orgDeliveryDate;
    }
    
    public Date getVerDeliveryDate() {
        return this.verDeliveryDate;
    }	
  
    public void setVerDeliveryDate(Date verDeliveryDate) {
        this.verDeliveryDate = verDeliveryDate;
    }
    
    public String getPeriod() {
        return this.period;
    }	
  
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public String getRltOrderPoNo() {
        return this.rltOrderPoNo;
    }	
  
    public void setRltOrderPoNo(String rltOrderPoNo) {
        this.rltOrderPoNo = rltOrderPoNo;
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
    
    public String getRltRecPoNo() {
        return this.rltRecPoNo;
    }	
  
    public void setRltRecPoNo(String rltRecPoNo) {
        this.rltRecPoNo = rltRecPoNo;
    }

    
    public String getLogKey(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(this.commCode).append(";").append(this.poNo).append(this.operSeqId);
    	return sb.toString();
    }
}