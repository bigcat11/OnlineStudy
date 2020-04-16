package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduExam;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduExamService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("eduservice/examfront")
//@CrossOrigin
public class ExamFrontController {

    @Autowired
    private EduExamService examService;
    @Autowired
    private EduCourseService courseService;

    @PostMapping("getExamFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,
                                 @PathVariable long limit){

        Page<EduExam> page1=new Page<>(page,limit);
        Map<String,Object> map= examService.getExamFrontList(page1);
        return R.ok().data(map);
    }

    @GetMapping("getExamFrontInfo/{examId}")
    public R getTeacherFrontInfo(@PathVariable String examId){
        EduExam examServiceById = examService.getById(examId);
        String subjectId = examServiceById.getSubjectId();

        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("subject_id",subjectId);
        EduCourse eduCourse = courseService.getOne(queryWrapper);
        return R.ok().data("exam",examServiceById).data("courseList",eduCourse);
    }
}
