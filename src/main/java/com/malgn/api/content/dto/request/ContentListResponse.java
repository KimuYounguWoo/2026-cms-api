package com.malgn.api.content.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentListResponse {

    private final Long id;
    private final String title;
    private final String description;

    private final String createdBy;
    private final String lastModifiedBy;

    private final Long viewCnt;

    private final LocalDateTime createdAt;
    private final LocalDateTime lastModifiedAt;

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
