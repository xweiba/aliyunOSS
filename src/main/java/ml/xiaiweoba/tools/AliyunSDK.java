package ml.xiaiweoba.tools;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @program: aliyuanOSS
 * @description: 阿里云SDK
 * @author: Mr.xweiba
 * @create: 2018-06-05 11:51
 **/

public class AliyunSDK {
    private OSSClient ossClient;
    private Boolean auFlag;
    private static Logger logger = LoggerFactory.getLogger(AliyunSDK.class);

    public AliyunSDK(String accessKeyId, String accessKeySecret, String endpoint){
        try {
            this.ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            auFlag = true;
        }catch (Exception e){
            e.printStackTrace();
            auFlag = false;
        }
    }

    public boolean createBucket(String bucketName){
        if(auFlag) {
            Bucket bucket = ossClient.createBucket(bucketName);
            return bucketName.equals(bucket.getName());
        }
        return false;
    }

    public boolean deleteBucket(String bucketName){
        if(auFlag){
            try {
                ossClient.deleteBucket(bucketName);
                return true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    // 上传
    public String updateFile(File file, String bucketName){
        String resultStr = null;

        try {
            InputStream is = new FileInputStream(file);
            String fileName = file.getName();
            Long fileSize = file.length();

            ObjectMetadata metadata = new ObjectMetadata();
            // 设置文件长度
            metadata.setContentLength(fileSize);
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("UTF-8");
            // metadata.setContentType();
            metadata.setContentDisposition("fileName/filesize" + fileName + "/" + fileSize + "byte");

            // 上传文件
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, is, metadata);

            // 解析结果
            resultStr = putObjectResult.getETag();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件流读取失败");
            resultStr = "文件流读取失败";
        }
        return resultStr;
    }

    // 下载
    public InputStream getFile(String fileName, String bucketName){
        OSSObject ossObject = ossClient.getObject(bucketName, fileName);
        return ossObject.getObjectContent();
    }

    // 删除
    public boolean deleteFile(String fileName, String bucketName){
        try {
            ossClient.deleteObject(bucketName, fileName);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            logger.error("删除错误");
        }
        return false;
    }

}
