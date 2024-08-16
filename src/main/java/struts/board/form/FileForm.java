package struts.board.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FileForm extends ActionForm{

	private static final long serialVersionUID = 1L;

	private int fileNo;
	private String fileName;
	private String fileOrigin;
	private String localPath;
	private String extension;
	private FormFile image;
	
	public int getFileNo() {
		return fileNo;
	}
	
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileOrigin() {
		return fileOrigin;
	}
	
	public FormFile getImage() {
		return image;
	}

	public void setImage(FormFile image) {
		this.image = image;
	}

	public void setFileOrigin(String fileOrigin) {
		this.fileOrigin = fileOrigin;
	}
	
	public String getLocalPath() {
		return localPath;
	}
	
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
