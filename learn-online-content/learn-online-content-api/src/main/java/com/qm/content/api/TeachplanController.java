package com.qm.content.api;


import com.qm.content.model.dto.BindTeachplanMediaDto;
import com.qm.content.model.dto.SaveTeachplanDto;
import com.qm.content.model.dto.TeachplanDto;
import com.qm.content.model.po.Teachplan;
import com.qm.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 课程计划管理相关的接口
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
@RestController
public class TeachplanController {

    @Autowired
    TeachplanService teachplanService;

    //查询课程计划  GET /teachplan/22/tree-nodes
    @ApiOperation("查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId) {
        return teachplanService.findTeachplanTree(courseId);
    }

    @ApiOperation("课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan(@RequestBody SaveTeachplanDto teachplan) {
        teachplanService.saveTeachplan(teachplan);
    }

    /**
     * 根据课程计划id 删除对应的课程
     * @param teachplanId 课程计划id
     */
    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachplan/{teachplanId}")
    public void deleteTeachplan(@PathVariable Long teachplanId) {
        teachplanService.deleteTeachplan(teachplanId);
    }

    /**
     * 课程计划排序
     * @param moveType movedown表示上移，moveup表示下移
     * @param teachplanId 课程机械化id
     */
    @ApiOperation("课程计划排序")
    @PostMapping("/teachplan/{moveType}/{teachplanId}")
    public void orderByTeachplan(@PathVariable String moveType, @PathVariable Long teachplanId) {
        teachplanService.orderByTeachplan(moveType, teachplanId);
    }

    @ApiOperation("课程计划与媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto) {
        teachplanService.associationMedia(bindTeachplanMediaDto);
    }

    @ApiOperation("课程计划解除媒资信息绑定")
    @DeleteMapping("/teachplan/association/media/{teachPlanId}/{mediaId}")
    public void unassociationMedia(@PathVariable Long teachPlanId, @PathVariable String mediaId) {
        teachplanService.unassociationMedia(teachPlanId, mediaId);
    }

    @ApiOperation("课程计划查询")
    @PostMapping("/teachplan/{teachplanId}")
    public Teachplan getTeachplan(@PathVariable Long teachplanId) {
        return teachplanService.getTeachplan(teachplanId);
    }
}
