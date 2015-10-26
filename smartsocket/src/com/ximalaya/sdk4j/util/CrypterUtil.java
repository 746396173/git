package com.ximalaya.sdk4j.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class CrypterUtil {
	
	private static final String AES = "AES";
	private static final int AES_KEY_SIZE = 128;
	
    /**
     * 利用AES算法生成随机key
     * 
     * @return
     * @throws Exception
     */
    public static String getRandKey() {
        KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance(AES);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("AES algorithm not supported");
		}
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(AES_KEY_SIZE, secureRandom); 
        SecretKey secretKey = keyGenerator.generateKey(); 
        return BASE64Encoder.encode(secretKey.getEncoded());
    }
    
	/*
	 * -------------------------------------------------------------------------------
	 * HMAC SHA1摘要算法，该算法特点是：
	 * <li>对于相似度较高的字符串得到的签名值差异比较大</li>
	 * <li>常用作签名算法</li>
	 * -------------------------------------------------------------------------------
	 */
	
	private static final String HMAC_SHA1 = "HmacSHA1";
	
	public static byte[] hmacSHA1(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		if(data == null || key == null) {
			return null;
		}
		
		SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		return mac.doFinal(data);
	}
    
	/**
	 * HMAC SHA1签名或摘要算法
	 * @param data 待摘要的字节数据
	 * @param key  使用的key
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 */
	public static String hmacSHA1ToStr(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException  {
		//return DigestUtils.md5Hex(hmacSHA1(data, key));
		return  new String(Hex.encodeHex(DigestUtils.md5(hmacSHA1(data, key))));
	}
	
	/**
	 * HMAC SHA1签名或摘要算法
	 * @param data
	 * @param key
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	public static String hmacSHA1ToStr(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException {
		if(data == null || key == null) {
			return null;
		}
		return hmacSHA1ToStr(data.getBytes(), key.getBytes());
	}

}
