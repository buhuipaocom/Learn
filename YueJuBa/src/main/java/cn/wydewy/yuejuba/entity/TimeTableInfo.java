package cn.wydewy.yuejuba.entity;

import org.litepal.crud.DataSupport;

public class TimeTableInfo extends DataSupport {
	private int id;
	private String name;
	private String time;
	private String teacher;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}


}
