package jdk8.encryption.base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * 除本实例sun提供的base64编码以外，apache提供的Commons Codec和Bouncy Castle也支持
 */
public class Base64Util {

    public static String encryptBse64(byte[] bytes){
       return new BASE64Encoder().encode(bytes);
    }

    public static String decryptBase64(String data) throws IOException {
        return new String(new BASE64Decoder().decodeBuffer(data));
    }

}
