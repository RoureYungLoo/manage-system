package com.luruoyang.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import org.springframework.web.multipart.MultipartFile;


public class OssUtils2 {
  public static String upload(MultipartFile multipartFile) throws Exception {
    // Endpoint以华东1（杭州）为例，填写为https://oss-cn-hangzhou.aliyuncs.com，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    String bucketName = "luruoyang";
    // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
    String region = "cn-hangzhou";

    // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量
    EnvironmentVariableCredentialsProvider credentialsProvider =
        CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

    // 创建OSSClient实例。
    // 当OSSClient实例不再使用时，调用shutdown方法以释放资源。
    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    // 显式声明使用 V4 签名算法
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
    OSS ossClient = OSSClientBuilder.create()
        .endpoint(endpoint)
        .credentialsProvider(credentialsProvider)
        .region(region)
        .build();

    String objectName;
    try {

      // 2. 上传文件
      objectName = multipartFile.getOriginalFilename();

      objectName = getDir(objectName) + objectName;

      ossClient.putObject(bucketName, objectName, multipartFile.getInputStream());

    } catch (Exception e) {
      throw e;
    } finally {
      ossClient.shutdown();
    }
    https:
//https://<BucketName>.<Endpoint>/<ObjectName>
    return String.format("https://%s.%s/%s", bucketName, "oss-cn-hangzhou.aliyuncs.com", objectName);
  }

  private static String getDir(String filename) {
    int filenameHash = filename.hashCode();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      int subDir = filenameHash & 15;
      sb.append(subDir).append("/");
      filenameHash >>= 4;
    }
    return sb.toString();
  }
}