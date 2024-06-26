package com.qm.content.api;

import com.qm.base.model.PageParams;
import com.qm.base.model.PageResult;
import com.qm.content.model.dto.AddCourseDto;
import com.qm.content.model.dto.CourseBaseInfoDto;
import com.qm.content.model.dto.EditCourseDto;
import com.qm.content.model.dto.QueryCourseParamsDto;
import com.qm.content.model.po.CourseBase;
import com.qm.content.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

// 课程基本信息相关接口
@Api(value = "课程信息管理接口", tags = "课程信息管理接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto) {
        Long companyId = 1232141425L;
        return courseBaseInfoService.queryCourseBaseList(companyId, pageParams, queryCourseParamsDto);
    }

    @ApiOperation("新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated/*(ValidationGroups.Insert.class)*/ AddCourseDto addCourseDto) { // @Validated规定使用JSR-303校验
        Long companyId = 1232141425L;
        return courseBaseInfoService.createCourseBase(companyId, addCourseDto);
    }

    @ApiOperation("根据课程id查询接口")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId) {
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated/*(ValidationGroups.Update.class)*/ EditCourseDto editCourseDto) {
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourseBase(@PathVariable Long courseId) {
        Long companyId = 1232141425L;
        courseBaseInfoService.deleteCourseBase(companyId, courseId);
    }
}