package com.malgn.api.content.dto.response;

import com.malgn.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentViewResponse {

    private String title;
    private String description;

    private String createdBy;
    private String lastModifiedBy;

    private Long viewCnt;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Builder
    public ContentViewResponse(
            String title, String description, String createdBy, String lastModifiedBy,
            LocalDateTime createdAt, LocalDateTime lastModifiedAt,
            Long viewCnt) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.viewCnt = viewCnt;
    }

    public static ContentViewResponse of(
            String title, String description, String createdBy, String lastModifiedBy,
            LocalDateTime createdAt, LocalDateTime lastModifiedAt,
            Long viewCnt
    ) {
        return ContentViewResponse.builder()
                .title(title).description(description)
                .createdBy(createdBy).lastModifiedBy(lastModifiedBy)
                .createdAt(createdAt).lastModifiedAt(lastModifiedAt)
                .viewCnt(viewCnt).build();
    }


}
