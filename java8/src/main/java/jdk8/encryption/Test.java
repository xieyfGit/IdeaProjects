package jdk8.encryption;

import static jdk8.encryption.digest.MessageDigestUtil.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        String mess = "周一下午一点，环球时代中心B幢7楼";

        //Base64编码、解码
//        String data = encryptBse64(mess.getBytes());
//        System.out.println("编码后："+data);
//        System.out.println("解码后："+decryptBase64(data));

        //字符串MD5值获取
//        System.out.println(encryptMD5(mess.getBytes()));

        //文件MD5值获取
//        String path = "C:\\Users\\xyf\\Downloads\\ideaIU-2017.2.5.exe";
//        FileInputStream inputStream = new FileInputStream(new File(path));
//        System.out.println(getMD5OfFile(inputStream));

        //计算字符串的SHA值
//        System.out.println(getSHA(mess.getBytes(), "SHA-256"));
//        System.out.println("8e2e4c5a839ef798ba942d82bb42a8f7d34a8fe745ebf7d79dbc5c8805c60e0a".length());

        //计算字符串的MAC值
        byte[] hmacKey  = generateKey("HmacMD5");
        System.out.println(Arrays.toString(hmacKey));
        System.out.println(getHMAC(mess.getBytes(),hmacKey, "HmacMD5"));
    }
}
