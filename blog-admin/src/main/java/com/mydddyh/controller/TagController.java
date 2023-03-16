package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.TagListDto;
import com.mydddyh.domain.vo.TagVo;
import com.mydddyh.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
@Api(tags = "标签")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "分页查询标签列表")
    @ApiOperation(value = "分页查询标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "tagListDto", value = "需要查询匹配的标签名和备注"),
    })
    public ResponseResult list(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        return tagService.pageListTag(pageNum, pageSize, tagListDto);
    }

    @GetMapping("/listAllTag")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "查询所有标签列表")
    @ApiOperation(value = "查询所有标签列表")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }

    @PostMapping()
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "新增标签")
    @ApiOperation(value = "新增标签")
    @ApiImplicitParam(name = "tagVo", value = "新增标签的标签名和备注")
    public ResponseResult addTag(@RequestBody TagVo tagVo) {
        return tagService.addTag(tagVo);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "删除标签")
    @ApiOperation(value = "删除标签")
    @ApiImplicitParam(name = "id", value = "需要删除的标签id")
    public ResponseResult removeTag(@PathVariable Integer id) {
        return tagService.removeTag(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "获取标签")
    @ApiOperation(value = "获取标签")
    @ApiImplicitParam(name = "id", value = "需要获取的标签id")
    public ResponseResult getTag(@PathVariable Integer id) {
        return tagService.getTag(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('content:tag:index')")
    @SystemLog(businessName = "修改标签")
    @ApiOperation(value = "修改标签")
    @ApiImplicitParam(name = "tagVo", value = "需要修改的标签id和信息")
    public ResponseResult updateTag(@RequestBody TagVo tagVo) {
        return tagService.updateTag(tagVo);
    }
}
