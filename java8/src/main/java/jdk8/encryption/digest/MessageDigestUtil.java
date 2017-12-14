package jdk8.encryption.digest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要的目的是保证数据的完整性，
 * MD5之前还有MD2，但是已经废弃了
 * SHA利用单向散列进行摘要，所谓单向散列是不可逆的，任意长度数据得到相同长度散列值，但是数据长度有限制2^128，也即是说：A->Hash 但是不能由Hash->A
 *  真破解：2005年2月，王小云教授真破解。即数据A->hash值B 找到数据C->hash值B
 *  假破解：利用数据库数据进行逆推，核心在数据库数据的强大，避免办法是提升数据的复杂度
 * MD算法和SHA算法摘要值容易被修改(同时替换文件和M摘要值)，HMAC(等价于MAC)应运而生
 * HMAC：融合了前两者优势，并用密钥对摘要值进行加密
 */
public class MessageDigestUtil {

    //HmacMD5,HmacSHA,HmacSHA256,HmacSHA384,HmacSHA512 其中数字表示摘要的二进制长度，一般以16进制返回，故除以4
    public static String getHMAC(byte[] data,byte[] hmacKey,String encryption) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(hmacKey,encryption);
        Mac mac = Mac.getInstance(encryption);
        mac.init(secretKey);
        return fromBytesToHex(mac.doFinal(data));
    }

    //生成密钥
    public static byte[] generateKey(String encryption) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(encryption);
        SecretKey key =generator.generateKey();
        return key.getEncoded();

    }

    //SHA-1(160位二进制),SHA-256,SHA-384,SHA-512 其中数字表示摘要的二进制长度，一般以16进制返回，故除以4
    public static String getSHA(byte[] data,String encryption) throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance(encryption);
        sha.update(data);
        return fromBytesToHex(sha.digest());
    }

    //计算文件的md5值
    public static String getMD5OfFile(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        DigestInputStream di = new DigestInputStream(inputStream, MessageDigest.getInstance("MD5"));
        byte[] buffer = new byte[1024];
        int read = di.read(buffer, 0, 1024);
        while (read != -1) {
            read = di.read(buffer, 0, 1024);
        }
        MessageDigest md = di.getMessageDigest();
        return fromBytesToHex(md.digest());
    }

    //计算字符串的MD5值
    public static String encryptMD5(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return fromBytesToHex(md5.digest());
    }

    //字节数组转十六进制
    public static String fromBytesToHex(byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (Integer.toHexString(0XFF & data[i]).length() == 1) {
                builder.append("0").append(Integer.toHexString(0XFF & data[i]));
            } else {
                builder.append(Integer.toHexString(0XFF & data[i]));
            }
        }
        return builder.toString();
    }
}
