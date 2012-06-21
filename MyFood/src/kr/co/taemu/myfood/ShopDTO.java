package kr.co.taemu.myfood;

import java.util.Date;

public class ShopDTO {
	int idx;
	String name;
	String image;
	String tel;
	String saddr;
	String lon; // 위도
	String lat; // 경도
	String rating;
	String contents;
	Date regdate;
	Date edtdate;

	public ShopDTO(String name,String tel,String image,String contents) {
		this.name = name;
		this.tel = tel;
		this.image = image;
		this.contents = contents;
	}
	
	public ShopDTO(String name,String tel,String image,String contents,String lon, String lat) {
		this(name,tel,image,contents);
		this.lon = lon;
		this.lat = lat;
	}
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String imag) {
		this.image = imag;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSaddr() {
		return saddr;
	}
	public void setSaddr(String saddr) {
		this.saddr = saddr;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDetail() {
		return contents;
	}
	public void setDetail(String detail) {
		this.contents = detail;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getEdtdate() {
		return edtdate;
	}
	public void setEdtdate(Date edtdate) {
		this.edtdate = edtdate;
	}
}
