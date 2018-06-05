package ml.xiaiweoba.config;

import com.aliyun.oss.OSSClient;
import ml.xiaiweoba.tools.AliyunSDK;

import java.io.File;

/**
 * @program: aliyuanOSS
 * @description: 阿里云注册用户基本常量
 * @author: Mr.xweiba
 * @create: 2018-06-05 10:44
 **/

public class OSSClientConstants {
    public static void main(String[] args) {
        String endpoint = "";
        String accessKeyid = "";
        String accessKeySecret = "";
        String bucketName = "";

        String fileName = "testwww";
        String path = "D:\\\\1.png";
        AliyunSDK aliyunSDK = new AliyunSDK(accessKeyid, accessKeySecret, endpoint);
        // String s = aliyunSDK.updateFile(new File(path), bucketName);
        // System.out.println(s);
        aliyunSDK.deleteFile("1.png", bucketName);
        // OSSClient ossClient = new OSSClient(endpoint, accessKeyid, accessKeySecret);

        /*if(ossClient==null){
            System.out.println("kong");
        }
        String path = "D:\\1.png";

        ossClient.putObject(bucketName, fileName, new File(path));
        ossClient.shutdown();*/
    }
}
