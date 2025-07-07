package com.joy.web.blog.presentation.mapper;

import com.joy.config.JoyMapperConfig;
import com.joy.config.mapper.EntityMapper;
import com.joy.web.blog.application.dto.BlogDto.BlogPostMvcRequest;
import com.joy.web.blog.domain.entity.BlogPost;
import org.mapstruct.Mapper;

@Mapper(config = JoyMapperConfig.class)
public interface BlogPostEntityMapper extends EntityMapper<BlogPostMvcRequest, BlogPost> {
}
