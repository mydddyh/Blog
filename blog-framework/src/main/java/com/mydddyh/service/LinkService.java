package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.LinkListDto;
import com.mydddyh.domain.dto.LinkStatusDto;
import com.mydddyh.domain.entity.Link;
import com.mydddyh.domain.vo.LinkListVo;

/**
 * 友链(Link)表服务接口
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult pageListLink(Integer pageNum, Integer pageSize, LinkListDto linkListDto);

    ResponseResult addLink(LinkListVo linkListDto);

    ResponseResult getLink(Integer id);

    ResponseResult updateLink(LinkListVo linkListVo);

    ResponseResult changeStatus(LinkStatusDto linkStatusDto);

    ResponseResult removeLink(String id);
}

