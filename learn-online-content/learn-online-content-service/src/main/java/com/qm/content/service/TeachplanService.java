package com.qm.content.service;

import com.qm.content.model.dto.BindTeachplanMediaDto;
import com.qm.content.model.dto.SaveTeachplanDto;
import com.qm.content.model.dto.TeachplanDto;
import com.qm.content.model.po.Teachplan;

import java.util.List;

// 课程计划管理相关接口
public interface TeachplanService {
    /**
     * 根据课程id查询课程计划
     * @param courseId 课程计划
     * @return
     */
    List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 新增/修改/保存课程计划
     * @param saveTeachplanDto
     */
    void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * 根据课程计划id 删除对应的课程
     * @param teachplanId 课程计划id
     */
    void deleteTeachplan(Long teachplanId);

    /**
     * 课程计划排序
     * @param moveType movedown表示上移，moveup表示下移
     * @param teachplanId 课程机械化id
     */
    void orderByTeachplan(String moveType, Long teachplanId);

    Teachplan getTeachplan(Long teachplanId);

    // 教学计划绑定媒资信息
    void associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    /** 解绑教学计划与媒资信息
     * @param teachPlanId       教学计划id
     * @param mediaId           媒资信息id
     */
    void unassociationMedia(Long teachPlanId, String mediaId);
}
