package com.jeesite.modules.common.utils.oss;

public class OssFile {

    private String key;
    private String path;// 原图
    private String thumbnail;// 缩略图
    
    public OssFile(String key, String path, String thumbnail) {
        super();
        this.key = key;
        this.path = path;
        this.thumbnail = thumbnail;
    }

    public OssFile() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}
