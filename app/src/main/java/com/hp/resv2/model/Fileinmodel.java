package com.hp.resv2.model;

public class Fileinmodel {
    String  filename,fileurl;

    public Fileinmodel(){
    }
    public Fileinmodel(String filename, String fileurl){
        this.filename=filename;
        this.fileurl=fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
