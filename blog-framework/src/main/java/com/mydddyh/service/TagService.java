package com.mydddyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mydddyh.domain.ResponseResult;
import com.mydddyh.domain.dto.TagListDto;
import com.mydddyh.domain.entity.Tag;
import com.mydddyh.domain.vo.TagVo;

/**
 * 标签(Tag)表服务接口
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageListTag(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagVo tagVo);

    ResponseResult removeTag(Integer id);

    ResponseResult getTag(Integer id);

    ResponseResult updateTag(TagVo tagVo);

    ResponseResult listAllTag();
}

