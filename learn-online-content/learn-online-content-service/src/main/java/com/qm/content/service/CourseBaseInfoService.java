package com.qm.content.service;


import com.qm.base.model.PageParams;
import com.qm.base.model.PageResult;
import com.qm.content.model.dto.AddCourseDto;
import com.qm.content.model.dto.CourseBaseInfoDto;
import com.qm.content.model.dto.EditCourseDto;
import com.qm.content.model.dto.QueryCourseParamsDto;
import com.qm.content.model.po.CourseBase;

// 课程信息管理接口
public interface CourseBaseInfoService {

    /**
     * 课程分页查询
     * @param pageParams 分页查询参数
     * @param queryCourseParams 查询条件
     * @return 查询结果
     */
    PageResult<CourseBase> queryCourseBaseList(Long companyId, PageParams pageParams, QueryCourseParamsDto queryCourseParams);

    /**
     * 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 根据课程id查询课程信息
     * @param courseId 课程id
     * @return 课程详细信息
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);

    /**
     * 修改课程
     * @param companyId 机构id
     * @param editCourseDto 修改课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);

    /**
     * 删除课程
     * @param companyId 机构id
     * @param courseId 课程id
     */
    void deleteCourseBase(Long companyId, Long courseId);
}
