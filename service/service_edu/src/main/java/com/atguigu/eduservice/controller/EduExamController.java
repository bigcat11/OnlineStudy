package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;

import com.atguigu.eduservice.entity.EduExam;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduExamService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/eduservice/exam")
public class EduExamController {
    @Autowired
    private EduExamService examService;


    //4 条件查询带分页的方法
    @PostMapping("pageExamCondition/{current}/{limit}")
    public R pageExamCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) EduExam examQuery) {
        //创建page对象
        Page<EduExam> pageExam = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduExam> wrapper1 = new QueryWrapper<>();
        // 多条件组合查询
        String title = examQuery.getTitle();
//        String begin = examQuery.getBegin();
//        String end = examQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper1.like("title",title);
        }

//        if(!StringUtils.isEmpty(begin)) {
//            wrapper.ge("gmt_create",begin);
//        }
//        if(!StringUtils.isEmpty(end)) {
//            wrapper.le("gmt_create",end);
//        }

        //排序
        wrapper1.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
       examService.page(pageExam,wrapper1);

        long total = pageExam.getTotal();//总记录数
        List<EduExam> records = pageExam.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }



    //查询题目分页查询
    @GetMapping("getExamList/{current}/{limit}")
    public R getCourseList(@PathVariable long current,
                           @PathVariable long limit){

        Page<EduExam> pageCourse = new Page<>(current,limit);
        long total = pageCourse.getTotal();//总记录数
        List<EduExam> records = pageCourse.getRecords(); //数据list集合

        List<EduExam> list = examService.list(null);
        return R.ok().data("list",list).data("total",total).data("rows",records);

    }


    //添加课程基本信息的方法
    @PostMapping("addExamInfo")
    public R addExamInfo(@RequestBody EduExam eduExam) {
        //返回添加之后课程id，为了后面添加大纲使用
        examService.save(eduExam);
        return R.ok().message("添加成功");
    }

    //根据examm  id进行查询
    @GetMapping("getExam/{id}")
    public R getExam(@PathVariable String id) {
        EduExam eduExam = examService.getById(id);
        return R.ok().data("eduExam",eduExam);
    }

    //updateexam功能
    @PostMapping("updateExam")
    public R updateExam(@RequestBody EduExam eduExam) {
        boolean flag = examService.updateById(eduExam);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


//根据id删除
    @DeleteMapping("{ExamId}")
    public R deleteCourse(@PathVariable String ExamId){
      //  examService.removeexam(ExamId);
        examService.removeById(ExamId);
        return R.ok();
    }


}

