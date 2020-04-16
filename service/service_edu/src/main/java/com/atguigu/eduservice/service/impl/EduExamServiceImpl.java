package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduExam;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduExamMapper;
import com.atguigu.eduservice.service.EduExamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-04-15
 */
@Service
public class EduExamServiceImpl extends ServiceImpl<EduExamMapper, EduExam> implements EduExamService {

    @Override
    public void removeexam(String examId) {
        QueryWrapper<EduExam> wrapper=new QueryWrapper<>();
        EduExam eduExam = baseMapper.selectById(examId);
        wrapper.eq("subject_id",eduExam.getSubjectId());
        baseMapper.delete(wrapper);


    }

    @Override
    public EduExam getExamById(String id) {
        EduExam eduExam = baseMapper.selectById(id);
        return eduExam;
    }

    @Override
    public Map<String, Object> getExamFrontList(Page<EduExam> pageParam) {
        QueryWrapper<EduExam> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        baseMapper.selectPage(pageParam,queryWrapper);

        List<EduExam> records=pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);


        return map;
    }
}
