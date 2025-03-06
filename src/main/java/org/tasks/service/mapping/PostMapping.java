package org.tasks.service.mapping;

import org.mapstruct.Mapper;
import org.tasks.dao.model.PostEntity;
import org.tasks.dto.PostDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapping {

    public PostDto toDto(PostEntity post);
    public List<PostDto> toDto(List<PostEntity> post);
    public PostEntity toModel(PostDto post);

}
