package me.fourground.raisal.util;

import android.util.Base64;

import me.fourground.raisal.common.Const;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by YoungSoo Kim on 2016-08-01.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class AESUtil {

    public static final String TRANS_IFORMATION = "AES/CBC/PKCS5Padding";
    public static final String KEY_SPEC = "AES";
    public static final String CHARSET = "UTF-8";

    public static String decrypt(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANS_IFORMATION);
        byte[] keyBytes = new byte[16];
        byte[] b = Const.AES_KEY.getBytes(CHARSET);
        int len = b.length;
        if (len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, KEY_SPEC);
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
        return new String(results, CHARSET);

    }
}
