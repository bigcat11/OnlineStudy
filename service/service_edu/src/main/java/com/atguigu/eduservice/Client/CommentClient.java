package com.atguigu.eduservice.Client;


import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


//@FeignClient(name="service-ucenter",fallback = CommentClientImp.class)
@Component
@FeignClient(name="service-ucenter",fallback = CommentClientImp.class)
public interface CommentClient {
    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMemberOrder getInfo(@PathVariable String id);

}


