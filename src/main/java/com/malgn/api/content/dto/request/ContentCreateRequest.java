package com.malgn.api.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ContentCreateRequest {

    @NotNull
    private final String title;

    @NotNull
    private final String description;

    @Builder
    public ContentCreateRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public static ContentCreateRequest of(String title, String description) {
        return ContentCreateRequest.builder().title(title).description(description).build();
    }
}
