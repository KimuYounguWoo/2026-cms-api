package com.malgn.api.content.controller;

import com.malgn.api.content.dto.request.ContentCreateRequest;
import com.malgn.api.content.dto.request.ContentListResponse;
import com.malgn.api.content.dto.request.ContentUpdateRequest;
import com.malgn.api.content.dto.response.ContentViewResponse;
import com.malgn.api.content.service.ContentService;
import com.malgn.exception.ResponseCode;
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


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@NullMarked
public class ContentController {

    private final ContentService contentService;

    // 콘텐츠 추가
    @PostMapping("/user/content/create")
    public ResponseEntity<ResponseCode> contentCreate(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ContentCreateRequest contentCreateRequest
    ) {
        contentService.contentCreate(authorization.substring(7), contentCreateRequest);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 콘텐츠 상세 조회
    @GetMapping("/user/content/view/{id}")
    public ResponseEntity<ContentViewResponse> contentView(@PathVariable Long id) {
        ContentViewResponse response = contentService.contentView(id);
        return ResponseEntity.ok(response);
    }

    // 콘텐츠 수정
    @PatchMapping("/user/content/update/{id}")
    public ResponseEntity<ResponseCode> contentUpdate(
            @RequestHeader("Authorization") String authorization,
            @RequestBody ContentUpdateRequest contentUpdateRequest,
            @PathVariable Long id) {
        contentService.contentUpdate(authorization.substring(7), contentUpdateRequest, id);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 콘텐츠 삭제
    @DeleteMapping("/user/content/delete/{id}")
    public ResponseEntity<ResponseCode> contentDelete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        contentService.contentDelete(authorization.substring(7), id);
        return ResponseEntity.ok(ResponseCode.SUCCESS);
    }

    // 콘텐츠 목록 조회
    @GetMapping("/user/content/")
    public Page<ContentListResponse> getContents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return contentService.getContents(page+1, size);
    }

}
