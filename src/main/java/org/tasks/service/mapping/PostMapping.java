package org.tasks.service.mapping;

import org.mapstruct.Mapper;
import org.tasks.dao.model.PostEntity;
import org.tasks.dto.PostDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapping {

    PostDto toDto(PostEntity post);
    List<PostDto> toDto(List<PostEntity> post);
    PostEntity toModel(PostDto post);

}
