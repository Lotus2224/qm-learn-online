package com.qm.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.content.model.dto.TeachplanDto;
import com.qm.content.model.po.Teachplan;

import java.util.List;

// 课程计划 Mapper 接口
public interface TeachplanMapper extends BaseMapper<Teachplan> {
    //课程计划查询
    List<TeachplanDto> selectTreeNodes(Long courseId);
}
