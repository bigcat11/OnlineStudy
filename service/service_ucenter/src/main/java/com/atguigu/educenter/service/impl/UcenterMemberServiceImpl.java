package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-04-07
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(UcenterMember ucenterMember) {
        String moblie=ucenterMember.getMobile();
        String password=ucenterMember.getPassword();

        if(StringUtils.isEmpty(moblie) ||StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }

        //判断手机号
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",moblie);
        UcenterMember mobliemember = baseMapper.selectOne(wrapper);
        if(mobliemember==null){
            throw new GuliException(20001,"手机号不正确");
        }
//把密码进行MD5加密和数据库比较
        if(!MD5.encrypt(password).equals(mobliemember.getPassword())){
            throw new GuliException(20001,"密码不对");
        }

        //判断是否禁止登陆
        if(mobliemember.getIsDisabled()){
            throw new GuliException(20001,"帐号禁止登陆");
        }

        //生成jwt密令
        String jwtToken = JwtUtils.getJwtToken(mobliemember.getId(), mobliemember.getNickname());


        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        String code=registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if(StringUtils.isEmpty(code) ||StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)
                ||StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"登录失败");
        }
//判断redis中的验证码石佛正确
        String rediscode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(rediscode)){
            throw new GuliException(20001,"验证码不对");
        }

        //判断手机浩是否重复
        QueryWrapper<UcenterMember> registerVoQueryWrapper=new QueryWrapper<>();
        registerVoQueryWrapper.eq("mobile",mobile);
        Integer count=baseMapper.selectCount(registerVoQueryWrapper);
        if(count>0){
            throw new GuliException(20001,"注册失败");
        }

        UcenterMember member=new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }
}
