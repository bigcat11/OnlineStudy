package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import com.netflix.loadbalancer.RetryRule;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> params, String phone) {

      //发送短信
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "xxxx", "xxxxx");
        IAcsClient client = new DefaultAcsClient(profile);

//设置固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "大猫爱学习在线学习网站");
        request.putQueryParameter("TemplateCode", "SMS_187271493");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(params));


        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            boolean success = commonResponse.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
