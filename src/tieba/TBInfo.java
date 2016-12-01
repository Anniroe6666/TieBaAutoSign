package tieba;

public class TBInfo {
	private String title = null;
	private String url = null;
	private int exp = -1;
	private int badgelv = -1;
	private String badgeTitle = null;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getBadgelv() {
		return badgelv;
	}
	public void setBadgelv(int badgelv) {
		this.badgelv = badgelv;
	}
	public String getBadgeTitle() {
		return badgeTitle;
	}
	public void setBadgeTitle(String badgeTitle) {
		this.badgeTitle = badgeTitle;
	}

}
