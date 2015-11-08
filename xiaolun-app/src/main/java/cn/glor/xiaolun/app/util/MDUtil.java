package cn.glor.xiaolun.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by caosh on 2015/10/28.
 */
public class MDUtil {

    public static String md5(String input) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            byte[] digest = messageDigest.digest();

            for (int i = 0; i < digest.length; i++) {
                String hex = Integer.toHexString(digest[i] < 0 ? digest[i] + 256 : digest[i]);
                sb.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
