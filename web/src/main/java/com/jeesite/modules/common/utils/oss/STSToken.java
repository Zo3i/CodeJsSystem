package com.jeesite.modules.common.utils.oss;

import java.io.Serializable;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

public class STSToken implements Serializable {
    private static final long serialVersionUID = -5306339582888191087L;

    private String securityToken;

    private String accessKeySecret;

    private String accessKeyId;

    private Integer expirationSeconds;

    private String endpoint;

    private String bucketName;

    public STSToken() {

    }

    public STSToken(AssumeRoleResponse.Credentials credential, String endpoint, String bucketName) {
        this.accessKeyId = credential.getAccessKeyId();
        this.accessKeySecret = credential.getAccessKeySecret();
        this.expirationSeconds = 3600;
        this.securityToken = credential.getSecurityToken();
        this.endpoint = endpoint;
        this.bucketName = bucketName;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public Integer getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(Integer expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
