package gzm.demo.springboot.boot_feature.entity;

import java.util.Date;

public class User {
	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 是否同步
	 */
	private Integer syflg;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 性别
	 */
	private String strSex;

	/**
	 * 生日long型 用於es
	 */
	private Long birthdayl;

	/**
	 * 生日 用於顯示
	 */
	private String birthdays;

	/**
	 * 通配检索
	 */
	private String keyWord;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSyflg() {
		return syflg;
	}

	public void setSyflg(Integer syflg) {
		this.syflg = syflg;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getStrSex() {
		return strSex;
	}

	public void setStrSex(String strSex) {
		this.strSex = strSex;
	}

	public Long getBirthdayl() {
		return birthdayl;
	}

	public void setBirthdayl(Long birthdayl) {
		this.birthdayl = birthdayl;
	}

	public String getBirthdays() {
		return birthdays;
	}

	public void setBirthdays(String birthdays) {
		this.birthdays = birthdays;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

}