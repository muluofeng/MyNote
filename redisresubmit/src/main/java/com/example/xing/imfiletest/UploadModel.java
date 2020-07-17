package com.example.xing.imfiletest;

/**
 * Created by BellLiu on 2015/9/2.
 */
public class UploadModel extends BaseRequestModel {

    private  String fileName;

    private  int srcOffset;

    private  String fileUrl;

    private  int fileStatus;
    
    private String to;
    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getSrcOffset() {
        return srcOffset;
    }

    public void setSrcOffset(int srcOffset) {
        this.srcOffset = srcOffset;
    }

    public String getFileUrl() {return fileUrl;}

    public void setFileUrl(String fileUrl) {this.fileUrl = fileUrl;}

    public int getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(int fileStatus) {
        this.fileStatus = fileStatus;
    }

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}
