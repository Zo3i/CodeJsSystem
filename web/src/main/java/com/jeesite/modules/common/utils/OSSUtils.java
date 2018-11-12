package com.jeesite.modules.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.jeesite.modules.common.utils.oss.OssFile;
import com.jeesite.modules.common.utils.oss.STSToken;

/**
 * @author LinWei
 * @version 1.0
 * @data 2015年3月17日下午11:57:36
 * @描述 开放式存储客户端工具
 * @参考 https://help.aliyun.com/ 对象存储 OSS > SDK 参考 > Java-SDK > 图片处理
 */
@Component
public class OSSUtils {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static final String SPLITTER = "/";
    private OSSClient client;

    @Resource
    private RedisUtils redisUtils;

    @Value("${custom.ossRegionId}")
    private String ossRegionId;

    @Value("${custom.ossEndpoint}")
    private String ossEndpoint;

    @Value("${custom.ossBucketName}")
    private String bucketName;

    @Value("${custom.ossVisitUrl}")
    private String visitUrl;
    @Value("${custom.localVisitUrl}")
    private String localVisitUrl;

    @Value("${custom.ossStyleZoomOut}")
    private String styleZoomOut;

    @Value("${custom.ossStyleZoomIn}")
    private String styleZoomIn;

    @Value("${custom.ossAliyunUserid}")
    private String ossAliyunUserid;

    @Value("${custom.osskeyId}")
    private String osskeyId;

    @Value("${custom.osskeySecret}")
    private String osskeySecret;

    @Value("${custom.ossRoleArn}")
    private String ossRoleArn;

    /**
     * 获取oss的sts凭证(用于前端文件上传) https://help.aliyun.com/document_detail/31935.html?spm=a2c4g.11186623.6.660.1Mauqd
     * https://help.aliyun.com/document_detail/64047.html?spm=a2c4g.11186623.6.922.5bab445fXNlHSf#%E5%88%86%E7%89%87%E4%B8%8A%E4%BC%A0
     */
    public STSToken getSTS() {
        String roleSessionName = "zjhx-001";
        // 构造角色类
        // 构造角色类
        String policy = "{\n" + "  \"Statement\": [\n" + "    {\n" + "      \"Action\": [\n" + "        \"oss:PutObject\",\n" + "        \"oss:GetObject\"\n" + "      ],\n"
                + "      \"Effect\": \"Allow\",\n" + "      \"Resource\": [\n" + "        \"acs:oss:*:" + ossAliyunUserid + ":" + bucketName + "/*\"\n" + "      ]\n" + "    }\n"
                + "  ],\n" + "  \"Version\": \"1\"\n" + "}";

        STSToken sts = null;
        try {
            IClientProfile profile = DefaultProfile.getProfile(ossRegionId, osskeyId, osskeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion("2015-04-01");
            request.setMethod(MethodType.POST);
            request.setProtocol(ProtocolType.HTTPS);
            request.setRoleArn(ossRoleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(policy);
            final AssumeRoleResponse response = client.getAcsResponse(request);
            sts = new STSToken(response.getCredentials(), ossEndpoint, bucketName);
            init(sts);
        } catch (ClientException e) {
            logger.error("assumeRole异常", e);
        }
        return sts;
    }

    public void init(STSToken stsToken) {
        logger.info(">>>init OSSClient");
        client = new OSSClient(stsToken.getEndpoint(), stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
    }

    private Pattern patternImg = Pattern.compile(".+(.jpeg|.jpg|.png|.gif|.bmp)$");// 图片判断
    private String styleWidth200 = "image/resize,m_lfit,w_200,limit_1/auto-orient,1/quality,q_60";
    private String styleWidth800 = "image/resize,m_lfit,w_800,limit_1/auto-orient,1/quality,q_60";

    public OssFile tran(String key) {
        if (StringUtils.isNotBlank(key)) {
            if (patternImg.matcher(key).matches()) {
                return new OssFile(key, getSignedHttpURL(key, null), getSignedHttpURL(key, styleWidth200));
            } else {
                return new OssFile(key, getSignedHttpURL(key, null), null);
            }
        }
        return null;
    }

    /**
     * 获取缩略图完整url地址
     */
    public String getUrlThumbnail(String key) {
        return getDomainHttpURL(key, styleZoomOut);
    }

    /**
     * 获取正常访问url地址
     */
    public String getUrl(String key) {
        if (StringUtils.isNotBlank(key)) {
            return getDomainHttpURL(key, patternImg.matcher(key).matches() ? styleZoomIn : null);
        }
        return null;
    }
    
    /**
     * 获取本地url访问地址
     */
    public String getUrlLocal(String downloadId) {
        return StringUtils.isNotBlank(downloadId) ? localVisitUrl + downloadId : null;
    }

    /**localVisitUrl
     * 获取通过域名访问的url 访问时使用http://xxxx.yyy.com/sample.jpg?x-oss-process=style/stylename
     * 如：?x-oss-process=style/fix_width_200
     */
    private String getDomainHttpURL(String key, String styleName) {
        String ret = null;
        if (StringUtils.isNotBlank(key) && !key.startsWith("http")) {
            if (StringUtils.isNotBlank(styleName)) {
                ret = String.format("%s%s?x-oss-process=style/%s", visitUrl, key, styleName);
            } else {
                ret = String.format("%s%s", visitUrl, key);
            }
        } else {
            ret = key;
        }
        return StringUtils.isBlank(ret) ? "" : ret;
    }

    public String getSignedZoomout(String key) {
        return getSignedHttpURL(key, styleWidth200);
    }

    public String getSignedZoomin(String key) {
        return getSignedHttpURL(key, styleWidth800);
    }

    /**
     * 获取通过域名访问的签名url
     *
     * @param styleDetail
     *            如：image/resize,h_200,limit_0/quality,q_100/watermark,text_5pm65pWR6YCa,type_d3F5LXplbmhlaQ,size_20,t_15,shadow_35,color_ffffff,g_center,y_10,x_10,rotate_45,fill_1
     */
    private String getSignedHttpURL(String key, String styleDetail) {
        String ret = null;
        if (client != null && key != null && !key.startsWith("http")) {
            Date expiration = new Date(new Date().getTime() + 3600 * 1000);// 默认1小时即可
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethod.GET);
            req.setExpiration(expiration);
            if (StringUtils.isNotBlank(styleDetail)) {
                req.setProcess(styleDetail);
            }
            URL signedUrl = client.generatePresignedUrl(req);
            ret = signedUrl.toString();

            // endpoint替换成域名
            int idxKey = ret.indexOf(key);
            if (idxKey > 0) {
                ret = visitUrl + ret.substring(idxKey);
            }
        } else {
            ret = key;
        }
        return StringUtils.isBlank(ret) ? "" : ret;
    }

    /**
     * <pre>
     * 服务端签名后直传
     * https://help.aliyun.com/document_detail/31926.html?spm=5176.doc31926.6.629.ZrS8Qd
     * https://help.aliyun.com/document_detail/28756.html?spm=5176.7739709.6.672.QinSAp
     * </pre>
     */
    public Map<String, Object> getSTS2() {
        STSToken stsToken = redisUtils.getSTSToken();
        OSSClient client = new OSSClient(stsToken.getEndpoint(), stsToken.getAccessKeyId(), stsToken.getAccessKeySecret(), stsToken.getSecurityToken());
        try {
            long expireTime = 30L;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000L;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem("content-length-range", 0L, 1024 * 1024 * 10);// 限制10M
            // policyConds.addConditionItem(MatchMode.StartWith, "key",
            // bucketName);// 结合dir，表示上传路径要startWith
            // key

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, Object> ret = new HashMap<>();
            ret.put("accessid", stsToken.getAccessKeyId());
            ret.put("policy", encodedPolicy);
            ret.put("signature", postSignature);

            // ret.put("dir", "");//
            // 指定上传目录，做到安全隔离(相对于bucketName的路径，即bucketName下的子目录)
            // ret.put("host", "https://" + bucketName + "." + endpoint);
            ret.put("host", visitUrl);
            ret.put("expire", String.valueOf(expireEndTime / 1000L));
            ret.put("SecurityToken", stsToken.getSecurityToken());// stsToken时用，前端formData添加'x-oss-security-token'
            return ret;
        } catch (Exception e) {
            logger.error("异常信息", e);
            return null;
        }
    }

    public String putObject(String key, InputStream inputStream) throws IOException {
        return putObject(bucketName, key, inputStream, null);
    }

    public String putObject(String key, InputStream inputStream, String mimetype) throws IOException {
        return putObject(bucketName, key, inputStream, mimetype);
    }

    /**
     * 文件上传
     * 
     * @return 文件oss访问url
     */
    public String putObject(String bucketName, String key, InputStream inputStream, String mimetype) throws IOException {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(inputStream.available());
        if (mimetype != null) {
            meta.setContentType(mimetype);
        }
        client.putObject(bucketName, key, inputStream, meta);
        return getUrl(key);
    }

    /**
     * 文件下载
     */
    public void getObject(String key, OutputStream outputStream) throws IOException {
        getObject(bucketName, key, outputStream);
    }

    public void getObject(String bucketName, String key, OutputStream outputStream) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);

        // 获取Object的输入流
        try (InputStream inputStream = object.getObjectContent()) {
            // 处理Object
            byte[] buffer = new byte[1024];
            int byteRead = 0;
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }
            outputStream.flush();
            outputStream.close();
        }
    }

}
