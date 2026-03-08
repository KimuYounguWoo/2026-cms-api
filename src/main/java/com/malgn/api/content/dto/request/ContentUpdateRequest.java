package com.malgn.api.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ContentUpdateRequest {
    @NotBlank
    private final String title;

    @NotBlank
    private final String description;


    @Builder
    public ContentUpdateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static ContentUpdateRequest of(String title, String description) {
        return ContentUpdateRequest.builder().title(title).description(description).build();
    }
}
