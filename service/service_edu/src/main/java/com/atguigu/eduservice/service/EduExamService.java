package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduExam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2020-04-15
 */
public interface EduExamService extends IService<EduExam> {

    void removeexam(String examId);

    EduExam getExamById(String id);

    Map<String, Object> getExamFrontList(Page<EduExam> page1);
}
