package com.qm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.content.model.dto.CourseCategoryTreeDto;
import com.qm.content.model.po.CourseCategory;

import java.util.List;

// 课程分类 Mapper 接口
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    //使用递归查询分类
    List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
