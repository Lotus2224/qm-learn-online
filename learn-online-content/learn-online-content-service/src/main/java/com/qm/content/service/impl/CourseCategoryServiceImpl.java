package com.qm.content.service.impl;


import com.qm.content.mapper.CourseCategoryMapper;
import com.qm.content.model.dto.CourseCategoryTreeDto;
import com.qm.content.model.po.CourseCategory;
import com.qm.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 14:49
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        //调用mapper递归查询出分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        //定义一个list作为最终返回的list
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();
        //找到每个节点的子节点，最终封装成List<CourseCategoryTreeDto>
        //先将list转成map，key就是结点的id，value就是CourseCategoryTreeDto对象，目的就是为了方便从map获取结点,filter(item->!id.equals(item.getId()))把根结点排除
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(CourseCategory::getId, value -> value, (key1, key2) -> key2));
        //从头遍历 List<CourseCategoryTreeDto> ，一边遍历一边找子节点放在父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .forEach(item -> {
                    if (item.getParentid().equals(id)) {
                        courseCategoryList.add(item);
                    }
                    //找到节点的父节点
                    CourseCategoryTreeDto courseCategoryParent = mapTemp.get(item.getParentid());
                    if (courseCategoryParent != null) {
                        if (courseCategoryParent.getChildrenTreeNodes() == null) {
                            //如果该父节点的ChildrenTreeNodes属性为空要new一个集合，因为要向该集合中放它的子节点
                            courseCategoryParent.setChildrenTreeNodes(new ArrayList<>());
                        }
                        //到每个节点的子节点放在父节点的childrenTreeNodes属性中
                        courseCategoryParent.getChildrenTreeNodes().add(item);
                    }
                });
        return courseCategoryList;
    }

    //    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes2(String id) {
        // 获取所有的子节点
        List<CourseCategoryTreeDto> categoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 定义一个List，作为最终返回的数据
        List<CourseCategoryTreeDto> result = new ArrayList<>();

        // 为了方便找子节点的父节点，这里定义一个HashMap，key是节点的id，value是节点本身
        HashMap<String, CourseCategoryTreeDto> nodeMap = new HashMap<>();

        // 将数据封装到List中，只包括根节点的下属节点（1-1、1-2 ···），这里遍历所有节点
        categoryTreeDtos.stream().forEach(item -> {
            // 这里寻找父节点的直接下属节点（1-1、1-2 ···）
            if (item.getParentid().equals(id)) {
                nodeMap.put(item.getId(), item);
                result.add(item);
            }
            // 获取每个子节点的父节点
            String parentid = item.getParentid();
            CourseCategoryTreeDto parentNode = nodeMap.get(parentid);
            // 判断HashMap中是否存在该父节点（按理说必定存在，以防万一）
            if (parentNode != null) {
                // 为父节点设置子节点（将1-1-1设为1-1的子节点）
                List<CourseCategoryTreeDto> childrenTreeNodes = parentNode.getChildrenTreeNodes();
                // 如果子节点暂时为null，则初始化一下父节点的子节点（给个空集合就行）
                if (childrenTreeNodes == null) {
                    parentNode.setChildrenTreeNodes(new ArrayList<>());
                }
                // 将子节点设置给父节点
                parentNode.getChildrenTreeNodes().add(item);
            }
        });
        // 返回根节点的直接下属节点（1-1、1-2 ···）
        return result;
    }

    //    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes3(String id) {
        // 定义返回值res
        List<CourseCategoryTreeDto> res = new ArrayList<>();
        // 通过mapper拿到的数据库数据（MySQL递归） 查到的数据没有
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);
        // 定义一个HashMap，key就是结点id，value就是结点本身
        HashMap<String, CourseCategoryTreeDto> map = new HashMap<>();
        // 找到每个父结点（1-1，1-2，1-3）的子结点（1-1-1，1-1-2，1-1-3），封装成List<CourseCategoryTreeDto> res
        courseCategoryTreeDtos.forEach(item -> {
            // 将找到的父级结点（1-1，1-2，1-3）放入map和res中
            if (item.getParentid().equals(id)) {
                map.put(item.getId(), item);
                res.add(item);
            }
            // 现在剩下的就是子结点
            String parentId = item.getParentid();
            CourseCategoryTreeDto parentNode = map.get(parentId);
            if (parentNode != null) { // 排除根结点
                List<CourseCategoryTreeDto> childrenTreeNodes = parentNode.getChildrenTreeNodes();
                if (childrenTreeNodes == null) {
                    parentNode.setChildrenTreeNodes(new ArrayList<>());
                }
                parentNode.getChildrenTreeNodes().add(item);
            }
        });
        return res;
    }
}