package com.goodsogood.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * @author xuliduo
 * @date 2018/6/9
 * @description class DESUtil des加密工具
 * js的des方法为：
 * <p>
 * * 调用CryptoJS进行des
 * * @param {string} key
 * * @param {string} message
 *
 * <p>
 * function des(key,message){
 * // console.log(key);
 * let keyHex=CryptoJS.enc.Utf8.parse(key);
 * let encrypted=CryptoJS.DES.encrypt(message,keyHex,{
 * mode:CryptoJS.mode.ECB,
 * padding:CryptoJS.pad.Pkcs7
 * });
 * return encrypted.toString();
 * }
 * </p>
 */
//@Log4j2
public class DESUtils {
    private static final String DES_ALGORITHM = "DES";
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";

    /**
     * DES加密
     *
     * @param plainData 原始字符串
     * @param secretKey 加密密钥
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encryption(String plainData, String secretKey) throws Exception {
        try {
            final Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

            //为了防止解密时报javax.crypto.IllegalBlockSizeException:Input length must
            // be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(buf);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            //log.error(e.getMessage(), e);
            throw e;
        } catch (IllegalBlockSizeException e) {
            //log.error(e.getMessage(), e);
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            //log.error(e.getMessage(), e);
            throw new Exception("BadPaddingException", e);
        }
    }


    /**
     * DES解密
     *
     * @param secretData 密码字符串
     * @param secretKey  解密密钥
     * @return 原始字符串
     * @throws Exception
     */
    public static String decryption(String secretData, String secretKey) throws Exception {

        try {
            final Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));
            byte[] buf = cipher.doFinal(Base64.getDecoder().decode(secretData.getBytes(CHARSET)));
            return new String(buf);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            //log.error(e.getMessage(), e);
            throw e;
        } catch (IllegalBlockSizeException e) {
            //log.error(e.getMessage(), e);
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            //log.error(e.getMessage(), e);
            throw new Exception("BadPaddingException", e);
        }
    }

    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     */
    private static SecretKey generateKey(String secretKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes(CHARSET));
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }
}
