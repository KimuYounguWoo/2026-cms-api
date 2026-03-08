package com.malgn.api.content.controller;

import com.malgn.api.content.dto.request.ContentCreateRequest;
import com.malgn.api.content.dto.request.ContentListResponse;
import com.malgn.api.content.dto.request.ContentUpdateRequest;
import com.malgn.api.content.dto.response.ContentViewResponse;
import com.malgn.api.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.malgn.exception.CustomResponseCode.SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
@NullMarked
public class ContentController {

    private final ContentService contentService;

    // 콘텐츠 추가
    @PostMapping("/create")
    public ResponseEntity<String> contentCreate(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ContentCreateRequest contentCreateRequest
    ) {
        contentService.contentCreate(authorization.substring(7), contentCreateRequest);
        return ResponseEntity.ok(SUCCESS.getMessage());
    }

    // 콘텐츠 상세 조회
    @GetMapping("/view/{id}")
    public ResponseEntity<ContentViewResponse> contentView(@PathVariable Long id) {
        ContentViewResponse response = contentService.contentView(id);
        return ResponseEntity.ok(response);
    }

    // 콘텐츠 수정
    @PatchMapping("/update/{id}")
    public ResponseEntity<String> contentUpdate(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ContentUpdateRequest contentUpdateRequest,
            @PathVariable Long id) {
        contentService.contentUpdate(authorization.substring(7), contentUpdateRequest, id);
        return ResponseEntity.ok(SUCCESS.getMessage());
    }

    // 콘텐츠 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> contentDelete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        contentService.contentDelete(authorization.substring(7), id);
        return ResponseEntity.ok(SUCCESS.getMessage());
    }

    @GetMapping("/")
    public Page<ContentListResponse> getContents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return contentService.getContents(page+1, size);
    }

}
