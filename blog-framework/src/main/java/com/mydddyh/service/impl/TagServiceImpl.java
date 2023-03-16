package com.mydddyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydddyh.constants.SystemConstants;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.TagListDto;
import com.mydddyh.domain.entity.Tag;
import com.mydddyh.domain.vo.PageVo;
import com.mydddyh.domain.vo.TagVo;
import com.mydddyh.enums.AppHttpCodeEnum;
import com.mydddyh.exception.SystemException;
import com.mydddyh.mapper.TagMapper;
import com.mydddyh.service.TagService;
import com.mydddyh.utils.BeanCopyUtils;
import com.mydddyh.utils.ServiceUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 标签(Tag)表服务实现类
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Override
    public ResponseResult pageListTag(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        // 分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<Tag>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, ServiceUtils.addWildcard(tagListDto.getName()));
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, ServiceUtils.addWildcard(tagListDto.getRemark()));
        Page<Tag> page = new Page<Tag>(pageNum, pageSize);
        page(page, queryWrapper);
        // 封装
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);
        PageVo pageVo = new PageVo(tagVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(TagVo tagVo) {
        tagVo.setId(null);
        // 检查标签名称
        if (Objects.isNull(tagVo.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NULL);
        }
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<Tag>();
        queryWrapper.eq(Tag::getName, tagVo.getName());
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_EXIST);
        }
        // 添加Tag
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult removeTag(Integer id) {
        if (!removeById(id)) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTag(Integer id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagVo tagVo) {
        // 检查id
        if (Objects.isNull(tagVo.getId())) {
            throw new SystemException(AppHttpCodeEnum.TAG_ID_NULL);
        }
        // 检查name
        if (Objects.isNull(tagVo.getName())) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_NULL);
        }
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<Tag>();
        queryWrapper.eq(Tag::getName, tagVo.getName());
        queryWrapper.ne(Tag::getId, tagVo.getId());
        if (count(queryWrapper) > 0) {
            throw new SystemException(AppHttpCodeEnum.TAG_NAME_EXIST);
        }
        // 更新Tag
        Tag tag = BeanCopyUtils.copyBean(tagVo, Tag.class);
        if (!updateById(tag)) {
            throw new SystemException(AppHttpCodeEnum.TAG_NOT_EXIST);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<Tag>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> tags = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(tags, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}

