package org.tasks.test.service.mapper;

import org.tasks.dao.model.CommentEntity;
import org.tasks.dto.CommentDto;
import org.tasks.service.mapping.CommentMapper;

import java.util.ArrayList;
import java.util.List;

public class CommentTestMapper implements CommentMapper {

    @Override
    public CommentDto toDto(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setId( entity.getId() );
        commentDto.setContent( entity.getContent() );
        commentDto.setPostId( entity.getPostId() );

        return commentDto;
    }

    @Override
    public List<CommentDto> toDto(List<CommentEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( entities.size() );
        for ( CommentEntity commentEntity : entities ) {
            list.add( toDto( commentEntity ) );
        }

        return list;
    }

    @Override
    public CommentEntity toEntity(CommentDto dto) {
        if ( dto == null ) {
            return null;
        }

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setId( dto.getId() );
        commentEntity.setContent( dto.getContent() );
        commentEntity.setPostId( dto.getPostId() );

        return commentEntity;
    }
}
