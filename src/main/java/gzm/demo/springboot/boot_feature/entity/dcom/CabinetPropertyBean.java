package gzm.demo.springboot.boot_feature.entity.dcom;

import java.io.Serializable;
import java.util.Date;

public class CabinetPropertyBean  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String strEndDeliverTime;
	private String strEndUseTime;
	private String strEndPowerNoTime;
	private String strEndPowerOffTime;
	private String strEndTestOffTime;
	private String strEndTestNoTime;

	private Integer cabinetId;

	private String cabinetNumber;
	private String sn;
	private String assetNo;
	private String roomNo;// 机房编号
	private String compartmentNo;// 包间编号
	private String powerNoStatus;
	private String powerNoStatusText;
	private String contractNo;
	private Date useTime;
	private String strUseTime;

	private String productid;

	public void setStrUseTime(String strUseTime) {
		this.strUseTime = strUseTime;
	}

	public void setStrDeliverTime(String strDeliverTime) {
		this.strDeliverTime = strDeliverTime;
	}

	private String useType;
	private String useTypeText;
	private Date deliverTime;
	private String strDeliverTime;
	private String lifeCycleStatus;
	private String lifeCycleStatusText;

	private String busiDetailType;
	private String busiDetailTypeText;

	private String businessType;
	private String businessTypeText;

	// 机柜用电类型： 测试用电、正式用电
	private String powermodel;

	public void setStrHoldEndTime(String strHoldEndTime) {
		this.strHoldEndTime = strHoldEndTime;
	}

	public void setStrHoldTime(String strHoldTime) {
		this.strHoldTime = strHoldTime;
	}

	public void setStrLockEndTime(String strLockEndTime) {
		this.strLockEndTime = strLockEndTime;
	}

	public void setStrLockTime(String strLockTime) {
		this.strLockTime = strLockTime;
	}

	public void setStrPowerNoTime(String strPowerNoTime) {
		this.strPowerNoTime = strPowerNoTime;
	}

	public void setStrPowerOffTime(String strPowerOffTime) {
		this.strPowerOffTime = strPowerOffTime;
	}

	public void setStrTestNoTime(String strTestNoTime) {
		this.strTestNoTime = strTestNoTime;
	}

	public void setStrTestOffTime(String strTestOffTime) {
		this.strTestOffTime = strTestOffTime;
	}

	private String customerName;
	private Integer holdCount;
	private Date holdEndTime;
	private String strHoldEndTime;
	private Date holdTime;
	private String strHoldTime;
	private Date lockEndTime;
	private String strLockEndTime;
	private String lockFlag;
	private Date lockTime;
	private String strLockTime;
	private Date powerNoTime;
	private String strPowerNoTime;
	private Date powerOffTime;
	private String strPowerOffTime;
	private Date testNoTime;
	private String strTestNoTime;
	private Date testOffTime;
	private String strTestOffTime;

	private Integer cabinetCount;
	private String cabinetType;
	private String cabinetTypeText;

	private String detailType;
	private String detailTypeText;

	private String netType;
	private String netTypeText;
	private Integer usedCabCount;

	private Integer height;
	private Integer high;
	private Integer length;
	private String pduType;
	private String pduTypeText;

	private String power;

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	private String powerNo;
	private String powerType;
	private String powerTypeText;
	private String standardType;
	private String standardTypeText;
	private Integer width;

	public String getPowermodel() {
		return powermodel;
	}

	public void setPowermodel(String powermodel) {
		this.powermodel = powermodel;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getPduType() {
		return pduType;
	}

	public void setPduType(String pduType) {
		this.pduType = pduType;
	}

	/*
	 * public String getPower() { return power; } public void setPower(String
	 * power) { this.power = power; }
	 */

	public String getPowerNo() {
		return powerNo;
	}

	public void setPowerNo(String powerNo) {
		this.powerNo = powerNo;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public String getStandardType() {
		return standardType;
	}

	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public String getCabinetNumber() {
		return cabinetNumber;
	}

	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getAssetNo() {
		return assetNo;
	}

	public void setAssetNo(String assetNo) {
		this.assetNo = assetNo;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getCompartmentNo() {
		return compartmentNo;
	}

	public void setCompartmentNo(String compartmentNo) {
		this.compartmentNo = compartmentNo;
	}

	public String getPowerNoStatus() {
		return powerNoStatus;
	}

	public void setPowerNoStatus(String powerNoStatus) {
		this.powerNoStatus = powerNoStatus;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getLifeCycleStatus() {
		return lifeCycleStatus;
	}

	public void setLifeCycleStatus(String lifeCycleStatus) {
		this.lifeCycleStatus = lifeCycleStatus;
	}

	public String getBusiDetailType() {
		return busiDetailType;
	}

	public void setBusiDetailType(String busiDetailType) {
		this.busiDetailType = busiDetailType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getHoldCount() {
		return holdCount;
	}

	public void setHoldCount(Integer holdCount) {
		this.holdCount = holdCount;
	}

	public Date getHoldEndTime() {
		return holdEndTime;
	}

	public void setHoldEndTime(Date holdEndTime) {
		this.holdEndTime = holdEndTime;
	}

	public Date getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Date holdTime) {
		this.holdTime = holdTime;
	}

	public Date getLockEndTime() {
		return lockEndTime;
	}

	public void setLockEndTime(Date lockEndTime) {
		this.lockEndTime = lockEndTime;
	}

	public String getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Date getPowerNoTime() {
		return powerNoTime;
	}

	public void setPowerNoTime(Date powerNoTime) {
		this.powerNoTime = powerNoTime;
	}

	public Date getPowerOffTime() {
		return powerOffTime;
	}

	public void setPowerOffTime(Date powerOffTime) {
		this.powerOffTime = powerOffTime;
	}

	public Date getTestNoTime() {
		return testNoTime;
	}

	public void setTestNoTime(Date testNoTime) {
		this.testNoTime = testNoTime;
	}

	public Date getTestOffTime() {
		return testOffTime;
	}

	public void setTestOffTime(Date testOffTime) {
		this.testOffTime = testOffTime;
	}

	public Integer getCabinetCount() {
		return cabinetCount;
	}

	public void setCabinetCount(Integer cabinetCount) {
		this.cabinetCount = cabinetCount;
	}

	public String getCabinetType() {
		return cabinetType;
	}

	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public Integer getUsedCabCount() {
		return usedCabCount;
	}

	public void setUsedCabCount(Integer usedCabCount) {
		this.usedCabCount = usedCabCount;
	}

}
