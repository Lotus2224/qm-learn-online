package com.qm.content.service;


import com.qm.base.model.PageParams;
import com.qm.base.model.PageResult;
import com.qm.content.model.dto.AddCourseDto;
import com.qm.content.model.dto.CourseBaseInfoDto;
import com.qm.content.model.dto.QueryCourseParamsDto;
import com.qm.content.model.po.CourseBase;

/**
 * @author Mr.M
 * @version 1.0
 * @description 课程信息管理接口
 * @date 2023/2/12 10:14
 */
public interface CourseBaseInfoService {

    /**
     * 课程分页查询
     * @param pageParams 分页查询参数
     * @param courseParamsDto 查询条件
     * @return 查询结果
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto);

    /**
     * 新增课程
     * @param companyId 机构id
     * @param addCourseDto 课程信息
     * @return 课程详细信息
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

}
