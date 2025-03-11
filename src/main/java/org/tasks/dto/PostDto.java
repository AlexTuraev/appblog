package org.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Integer countLike;
    private String tags;
    private byte[] image;

}
