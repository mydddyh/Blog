package com.mydddyh.controller;

import com.mydddyh.annotation.SystemLog;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.LinkListDto;
import com.mydddyh.domain.dto.LinkStatusDto;
import com.mydddyh.domain.vo.LinkListVo;
import com.mydddyh.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
@Api(tags = "友链")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @PreAuthorize("@ps.hasPermission('content:link:list')")
    @SystemLog(businessName = "分页查询友链列表")
    @ApiOperation(value = "分页查询友链列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小"),
            @ApiImplicitParam(name = "linkListDto", value = "需要查询匹配的友链名称和状态"),
    })
    public ResponseResult pageListLink(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        return linkService.pageListLink(pageNum, pageSize, linkListDto);
    }

    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:link:add')")
    @SystemLog(businessName = "新增友链")
    @ApiOperation(value = "新增友链")
    @ApiImplicitParam(name = "linkListDto", value = "新增友链")
    public ResponseResult addLink(@RequestBody LinkListVo linkListDto){
        return linkService.addLink(linkListDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:link:query')")
    @SystemLog(businessName = "根据id获取友链")
    @ApiOperation(value = "根据id获取友链")
    @ApiImplicitParam(name = "id", value = "需要获取的友链id")
    public ResponseResult getLink(@PathVariable Integer id) {
        return linkService.getLink(id);
    }

    @PutMapping()
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    @SystemLog(businessName = "修改友链")
    @ApiOperation(value = "修改友链")
    @ApiImplicitParam(name = "linkListVo", value = "需要修改的友链id和信息")
    public ResponseResult updateLink(@RequestBody LinkListVo linkListVo) {
        return linkService.updateLink(linkListVo);
    }

    @PutMapping("/changeLinkStatus")
    @PreAuthorize("@ps.hasPermission('content:link:edit')")
    @SystemLog(businessName = "改变友链状态")
    @ApiOperation(value = "改变友链状态")
    @ApiImplicitParam(name = "linkStatusDto", value = "需要设置的友链状态")
    public ResponseResult changeStatus(@RequestBody LinkStatusDto linkStatusDto) {
        return linkService.changeStatus(linkStatusDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPermission('content:link:remove')")
    @SystemLog(businessName = "根据id删除友链")
    @ApiOperation(value = "根据id删除友链")
    @ApiImplicitParam(name = "id", value = "需要删除的友链id")
    public ResponseResult removeLink(@PathVariable String id) {
        return linkService.removeLink(id);
    }
}
