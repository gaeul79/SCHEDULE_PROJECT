package com.sparta.schedule_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    @NotBlank(message = "페이지는 공백일 수 없습니다.")
    private int page;

    @NotBlank(message = "사이즈는 공백일 수 없습니다.")
    @Min(value = 1)
    private int size;

    @Min(value = 1, message = "총 페이지는 1이상이어야 합니다.")
    private int totalPage;

    public PageDto(Pageable page, int totalPage) {
        this.page = page.getPageNumber() + 1;
        this.size = page.getPageSize();
        this.totalPage = totalPage;
    }

    public Pageable convertDtoToPageable() {
        return PageRequest.of(page - 1, size);
    }
}
