package gzm.demo.springboot.boot_feature.entity.dcom;

import java.io.Serializable;

import java.util.Date;


/**
 * 机柜使用状态表印设类
 * 
 * @author zhenmin.gu
 * 
 */
public class CabinetStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer consuming;

	private String cabinetNum;

	private String computerNum;

	private String compartmentNum;

	private Integer status;

	private Date changeDate;

	private String operator;

	private String customerName;

	private String contractNo;

	private Date createDate;

	private String powerStatus;

	private String strChangeDate;

	private String strCreateDate;
	
	private Integer customerId;
	
	private Date startDate;
	
	private Date endDate;
	
	private String power;
	
	private String orderId;
	
	private String closeOrderId;
	
	private Date warndate;
	
	private Integer userid;
	
	private String username;
	
	// 是否最新计费记录
	private Integer iscurrent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCabinetNum() {
		return cabinetNum;
	}

	public void setCabinetNum(String cabinetNum) {
		this.cabinetNum = cabinetNum;
	}

	public String getComputerNum() {
		return computerNum;
	}

	public void setComputerNum(String computerNum) {
		this.computerNum = computerNum;
	}

	public String getCompartmentNum() {
		return compartmentNum;
	}

	public void setCompartmentNum(String compartmentNum) {
		this.compartmentNum = compartmentNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getConsuming() {
		return consuming;
	}

	public void setConsuming(Integer consuming) {
		this.consuming = consuming;
	}

	public String getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(String powerStatus) {
		this.powerStatus = powerStatus;
	}

	public String getStrChangeDate() {
		return strChangeDate;
	}

	public void setStrChangeDate(String strChangeDate) {
		this.strChangeDate = strChangeDate;
	}

	public String getStrCreateDate() {
		return strCreateDate;
	}

	public void setStrCreateDate(String strCreateDate) {
		this.strCreateDate = strCreateDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCloseOrderId() {
		return closeOrderId;
	}

	public void setCloseOrderId(String closeOrderId) {
		this.closeOrderId = closeOrderId;
	}

	public Date getWarndate() {
		return warndate;
	}

	public void setWarndate(Date warndate) {
		this.warndate = warndate;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getIscurrent() {
		return iscurrent;
	}

	public void setIscurrent(Integer iscurrent) {
		this.iscurrent = iscurrent;
	}

	
}