package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.Client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-03-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

        @Autowired
       private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrappervideo=new QueryWrapper<>();
        wrappervideo.eq("course_id",courseId);
        wrappervideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrappervideo);

        List<String> videoIds=new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if(StringUtils.isEmpty(videoSourceId)){

                videoIds.add(videoSourceId);
            }

        }
        if(videoIds.size()>0){

            vodClient.deleteBatch(videoIds);
        }

        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();

        queryWrapper.eq("course_id",courseId);

        baseMapper.delete(queryWrapper);
    }


}
