package com.flexsolution.chyzhov.kmb.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PageResponseDto {

    int page;
    int pageSize;
    int total;
    boolean hasMore;
    Object content;

    public PageResponseDto(int page, int pageSize, int total, int totalPages, Object content) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.content = content;
        hasMore = page < (totalPages - 1);
    }
}
