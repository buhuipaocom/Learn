package cn.wydewy.filedinhand.entity;

import org.litepal.crud.DataSupport;

public class User extends DataSupport {
	private int id;
	private int database;//数据库规模
	private int vectorgra;//矢量图图幅数量
	private String uudi;//唯一ID
	private String name;//用户名
	private String email;//注册邮箱
	private String usertype;//用户类型
	private String language;//用户语言
	private String password;//密码

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public int getVectorgra() {
		return vectorgra;
	}

	public void setVectorgra(int vectorgra) {
		this.vectorgra = vectorgra;
	}

	public String getUudi() {
		return uudi;
	}

	public void setUudi(String uudi) {
		this.uudi = uudi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}