package com.flb.sample.widgets;

import com.flb.sample.common.Constant;
import com.tls.tls_sigature.tls_sigature;

import java.util.Random;

/**
 * author : fengzhangwei
 * date : 2019/6/27
 */
public class VideoUtils {


    public static String getUserSign(String name){
        tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(Constant.Video.SdkAppId, name, Constant.Video.PrivateKey, 24*3600*180);
        return result.urlSig;
    }

    public static String getUserId(){
        String retStr = "";
        String strTable = "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < 6; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

}
