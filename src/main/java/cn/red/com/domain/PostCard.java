package cn.red.com.domain;

public class PostCard {
	private String id;
	private String fromName;
	private String toName;
	private String fromAddress;
	private String toAddress;
	private String textContent;
	private String fromDate;
	private String toDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public String getToName() {
		return toName;
	}
	public void setToName(String toName) {
		this.toName = toName;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	@Override
	public String toString() {
		return "PostCard [id=" + id + ", fromName=" + fromName + ", toName=" + toName + ", fromAddress=" + fromAddress
				+ ", toAddress=" + toAddress + ", textContent=" + textContent + ", fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}	
}
