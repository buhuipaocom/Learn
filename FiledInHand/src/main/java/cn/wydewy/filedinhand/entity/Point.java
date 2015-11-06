package cn.wydewy.filedinhand.entity;

import org.litepal.crud.DataSupport;

public class Point extends DataSupport {
	private int id;
	private String name;
	private String notes;
	private String detail ;
	private String location;
	private String kmllocation;
	private String imageUrl;

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setKmllocation(String kmllocation) {
		this.kmllocation = kmllocation;
	}

	public String getKmllocation() {
		return kmllocation;
	}


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public String getDetail() {
		return detail;
	}

	public String getLocation() {
		return location;
	}


	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}