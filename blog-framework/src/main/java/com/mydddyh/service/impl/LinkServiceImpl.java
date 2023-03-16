package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.CategoryListDto;
import com.mydddyh.domain.dto.LinkListDto;
import com.mydddyh.domain.dto.LinkStatusDto;
import com.mydddyh.domain.entity.Category;
import com.mydddyh.domain.entity.Link;
import com.mydddyh.domain.vo.CategoryDetailVo;
import com.mydddyh.domain.vo.LinkListVo;
import com.mydddyh.domain.vo.LinkVo;
import com.mydddyh.domain.vo.PageVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.LinkMapper;
import com.mydddyh.service.LinkService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        // 查询审核通过的友链
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<Link>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成vo
        List<LinkVo> linkVoList = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVoList);
    }

    @Override
    public ResponseResult pageListLink(Integer pageNum, Integer pageSize, LinkListDto linkListDto) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<Link>();
        queryWrapper.like(StringUtils.hasText(linkListDto.getName()), Link::getName, ServiceUtils.addWildcard(linkListDto.getName()));
        queryWrapper.like(StringUtils.hasText(linkListDto.getStatus()), Link::getStatus, ServiceUtils.addWildcard(linkListDto.getStatus()));
        Page<Link> page = new Page<Link>(pageNum, pageSize);
        page(page, queryWrapper);
        List<LinkListVo> linkListVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkListVo.class);
        PageVo pageVo = new PageVo(linkListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(LinkListVo linkListDto) {
        linkListDto.setId(null);
        Link link = BeanCopyUtils.copyBean(linkListDto, Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Integer id) {
        Link link = getById(id);
        LinkListVo linkListVo = BeanCopyUtils.copyBean(link, LinkListVo.class);
        return ResponseResult.okResult(linkListVo);
    }

    @Override
    public ResponseResult updateLink(LinkListVo linkListVo) {
        Link link = BeanCopyUtils.copyBean(linkListVo, Link.class);
        if (!updateById(link)) {
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult changeStatus(LinkStatusDto linkStatusDto) {
        Link link = BeanCopyUtils.copyBean(linkStatusDto, Link.class);
        if (!updateById(link)) {
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeLink(String id) {
        // 剔除多行删除
        Integer linkId;
        try {
            linkId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new SystemException(AppHttpCodeEnum.DELETE_MULTI_ROWS);
        }
        // 删除分类
        if (!removeById(linkId)) {
            throw new SystemException(AppHttpCodeEnum.LINK_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

}

