package com.jeesite.modules.file.entity;

import com.ctc.wstx.util.StringUtil;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.lang.ByteUtils;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.service.BaseService;
import com.jeesite.modules.common.utils.OSSUtils;
import com.jeesite.modules.common.utils.SpringContextUtils;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hyperic.sigar.ProcUtil;

@Table(name = "${_prefix}sys_file_entity", alias = "a", 
columns = {
        @com.jeesite.common.mybatis.annotation.Column(name = "file_id", attrName = "fileId", label = "文件编号", isPK = true),
        @com.jeesite.common.mybatis.annotation.Column(name = "file_md5", attrName = "fileMd5", label = "文件MD5"),
        @com.jeesite.common.mybatis.annotation.Column(name = "file_path", attrName = "filePath", label = "文件相对路径"),
        @com.jeesite.common.mybatis.annotation.Column(name = "file_content_type", attrName = "fileContentType", label = "文件内容类型"),
        @com.jeesite.common.mybatis.annotation.Column(name = "file_extension", attrName = "fileExtension", label = "文件后缀扩展名"),
        @com.jeesite.common.mybatis.annotation.Column(name = "file_size", attrName = "fileSize", label = "文件大小", comment = "文件大小(单位B)"),
        @com.jeesite.common.mybatis.annotation.Column(name = "oss_uri", attrName = "ossUri", label = "ossUri", comment = "ossUri")
        }, orderBy = "a.file_id DESC")
public class FileEntity extends DataEntity<FileEntity> {
    private String fileId;
    private static final long serialVersionUID = 1L;
    private String filePath;
    private String fileMd5;
    private Long fileSize;
    private String fileExtension;
    private String fileContentType;
    private String ossUri; // modify by linwei 20180926

    public FileEntity() {
    }
    
    public String getOssUrlThumbnail() {
        return StringUtils.isNotBlank(ossUri) ? SpringContextUtils.getBean(OSSUtils.class).getUrlThumbnail(ossUri) : null;
    }
    public String getOssUrl() {
        return StringUtils.isNotBlank(ossUri) ? SpringContextUtils.getBean(OSSUtils.class).getUrl(ossUri) : null;
    }

    @NotBlank(message = "文件后缀扩展名不能为空")
    @Length(min = 0, max = 100, message = "文件后缀扩展名长度不能超过 100 个字符")
    public String getFileExtension() {
        return this.fileExtension;
    }

    public String getFileSizeFormat() {
        if (this.fileSize != null)
            return ByteUtils.formatByteSize(fileSize);
        return null;
    }

    public String getFileRealPath() {
        return Global.getUserfilesBaseDir(filePath) + fileId + "."+fileExtension;
    }
    public String getFileUrl() {
        return Global.getUserfilesBaseDir(filePath) + fileId + "."+fileExtension;
    }
    
    @NotBlank(message = "文件内容类型不能为空")
    @Length(min = 0, max = 200, message = "文件内容类型长度不能超过 200 个字符")
    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Length(min = 0, max = 64, message = "文件编号不能超过 64 个字符")
    public String getFileId() {
        return this.fileId;
    }

    @NotBlank(message = "文件相对路径不能为空")
    @Length(min = 0, max = 1000, message = "文件相对路径长度不能超过 1000 个字符")
    public String getFilePath() {
        return this.filePath;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public FileEntity(String fileId) {
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @NotNull(message = "文件大小不能为空")
    public Long getFileSize() {
        return this.fileSize;
    }

    @NotBlank(message = "文件MD5不能为空")
    @Length(min = 0, max = 64, message = "文件MD5长度不能超过 64 个字符")
    public String getFileMd5() {
        return this.fileMd5;
    }

    public String getOssUri() {
        return ossUri;
    }

    public void setOssUri(String ossUri) {
        this.ossUri = ossUri;
    }
}
