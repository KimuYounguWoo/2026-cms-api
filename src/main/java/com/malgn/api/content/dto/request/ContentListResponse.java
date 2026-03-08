package com.malgn.api.content.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentListResponse {

    private Long id;
    private String title;
    private String description;

    private String createdBy;
    private String lastModifiedBy;

    private Long viewCnt;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder
    public ContentListResponse(
            Long id, String title, String description,
            String createdBy, String lastModifiedBy,
            LocalDateTime createdAt, LocalDateTime lastModifiedAt,
            Long viewCnt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.viewCnt = viewCnt;
    }

    public static ContentListResponse of(
            Long id, String title, String description,
            String createdBy, String lastModifiedBy,
            LocalDateTime createdAt, LocalDateTime lastModifiedAt,
            Long viewCnt
    ) {
        return ContentListResponse.builder()
                .id(id).title(title).description(description)
                .createdBy(createdBy).lastModifiedBy(lastModifiedBy)
                .createdAt(createdAt).lastModifiedAt(lastModifiedAt)
                .viewCnt(viewCnt)
                .build();
    }
}
