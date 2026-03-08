package com.malgn.api.content.service;

import com.malgn.api.content.dto.request.ContentCreateRequest;
import com.malgn.api.content.dto.response.ContentListResponse;
import com.malgn.api.content.dto.request.ContentUpdateRequest;
import com.malgn.api.content.dto.response.ContentViewResponse;
import org.springframework.data.domain.Page;

public interface ContentService {
    void contentCreate(String authorization, ContentCreateRequest contentCreateRequest);
    void contentUpdate(String authorization, ContentUpdateRequest contentUpdateRequest, Long contentId);
    void contentDelete(String authorization, Long contentId);
    ContentViewResponse contentView(Long contentId);
    Page<ContentListResponse> getContents(int page, int size);
}
