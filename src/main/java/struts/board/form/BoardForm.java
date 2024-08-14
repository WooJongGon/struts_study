package struts.board.form;

import org.apache.struts.action.ActionForm;

public class BoardForm  extends ActionForm{
	
	private static final long serialVersionUID = 1L;
	
	private int boardNo;
	private String name;
	private String pwd;
	private String title;
	private String content;
	private String created;
	private int hitCount;
	private String mode;
	
	public int getBoardNo() {
		return boardNo;
	}
	
	public void setBoardNo(int BoardNo) {
		this.boardNo = BoardNo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public int getHitCount() {
		return hitCount;
	}
	
	public void setHitCount(int hitCount) {
		this.hitCount = hitCount;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
