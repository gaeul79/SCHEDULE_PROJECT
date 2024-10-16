package com.sparta.schedule_project.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 페이징 정보를 담는 DTO 클래스입니다.
 * Spring Data JPA의 Pageable 클래스를 기반으로 구현되었습니다.
 *
 * @since 2024-10-18
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private int page;
    private int size;
    private int totalPage;

    /**
     * Pageable 객체와 총 페이지 수를 기반으로 PageDto 객체를 생성합니다.
     *
     * @param page      Pageable 객체
     * @param totalPage 총 페이지 수
     * @since 2024-10-03
     */
    public PageDto(Pageable page, int totalPage) {
        this.page = page.getPageNumber() + 1;
        this.size = page.getPageSize();
        this.totalPage = totalPage;
    }

    /**
     * PageDto 객체를 Pageable 객체로 변환합니다.
     *
     * @return 변환된 Pageable 객체
     * @since 2024-10-03
     */
    public Pageable convertDtoToPageable() {
        if(page <= 0 || size <= 0) {
            return PageRequest.of(0, 10);
        }
        else  {
            return PageRequest.of(page - 1, size);
        }
    }
}
