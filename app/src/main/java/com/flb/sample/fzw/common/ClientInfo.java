package com.flb.sample.fzw.common;

import com.flb.sample.fzw.model.ClientInfoBean;
import com.flb.sample.fzw.model.MobileInfoBean;
import com.flb.sample.fzw.model.PropertiesBean;
import com.flb.sample.fzw.model.ServiceInfoBean;

import java.io.Serializable;

public class ClientInfo implements Serializable {

    //公共参数
    public static ClientInfoBean getClientInfo() {
        ClientInfoBean clientInfoBean = new ClientInfoBean();
        clientInfoBean.setVersionId("1.0.0");
        clientInfoBean.setOsType("android");
        clientInfoBean.setOsVersion("android");
        clientInfoBean.setProductId("1001");
        clientInfoBean.setSoftLanguage("31");
        clientInfoBean.setCoopId("888888");
        return clientInfoBean;
    }

    //手机配置
    public static MobileInfoBean getMobileInfo() {
        MobileInfoBean mobileInfoBean = new MobileInfoBean();
        mobileInfoBean.setApn("WIFI");
        mobileInfoBean.setCellId("0");
        mobileInfoBean.setCellId("86");
        mobileInfoBean.setCpu("armeabi-v7a");
        mobileInfoBean.setCpuHardware("Qualcomm MSM 8226 (Flattened Device Tree)");
        mobileInfoBean.setHeight(1280);
        mobileInfoBean.setWidth(720);
        mobileInfoBean.setImei("865198024931848");
        mobileInfoBean.setImsi("460001341946235");
        mobileInfoBean.setLanguage("31");
        mobileInfoBean.setMcncCode("0");
        mobileInfoBean.setModel("HM NOTE 1LTE");
        mobileInfoBean.setBrand("Xiaomi");
        mobileInfoBean.setSmsCenter("0");
        mobileInfoBean.setIp("127.0.0.1");
        return mobileInfoBean;
    }

    //用户信息
    public static PropertiesBean getProperties() {
        String password = "";
        String sourceId = "0";
        String status = "1";
        String username = "adminaa";
        int userType = 0;
        int uuid = 10000000;
        PropertiesBean propertiesBean = new PropertiesBean();
        propertiesBean.setPassword(password);
        propertiesBean.setSourceId(sourceId);
        propertiesBean.setStatus(status);
        propertiesBean.setUsername(username);
        propertiesBean.setUserType(userType);
        propertiesBean.setUuid(uuid);
        return propertiesBean;
    }

    public static String protocol = "1.0";
    public static String protocolType = "request";

    //服务器信息
    public static ServiceInfoBean getServiceInfo() {

        String activityTim = "2019-01-24 12:24:21";
        String lastTim = "2019-05-16 17:53:06";
        String did = "19";
        ServiceInfoBean serviceInfoBean = new ServiceInfoBean();
        serviceInfoBean.setActiveTime(activityTim);
        serviceInfoBean.setLatestTime(lastTim);
        serviceInfoBean.setDid(did);
        return serviceInfoBean;
    }

}
