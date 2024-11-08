package com.sparta.schedule_project.dto.response;

import lombok.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> data;
    private int page;
    private int size;
    private int totalPage;

    public static <T> PageResponseDto<T> of(List<T> data, Pageable pageable, int totalPage) {
        return PageResponseDto.<T>builder()
                .data(data)
                .page(pageable.getPageNumber() + 1)
                .size(pageable.getPageSize())
                .totalPage(totalPage)
                .build();
    }
}
