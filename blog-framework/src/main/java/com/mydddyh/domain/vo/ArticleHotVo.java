package com.mydddyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleHotVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
}
