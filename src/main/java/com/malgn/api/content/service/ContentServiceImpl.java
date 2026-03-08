package com.malgn.api.content.service;

import com.malgn.api.content.dto.request.ContentCreateRequest;
import com.malgn.api.content.dto.request.ContentListResponse;
import com.malgn.api.content.dto.request.ContentUpdateRequest;
import com.malgn.api.content.dto.response.ContentViewResponse;
import com.malgn.configure.jwt.JwtUtil;
import com.malgn.configure.redis.RedisUtil;
import com.malgn.domain.content.entity.Content;
import com.malgn.domain.content.repository.ContentRepository;
import com.malgn.domain.user.entity.User;
import com.malgn.domain.user.entity.UserRole;
import com.malgn.domain.user.repository.UserRepository;
import com.malgn.exception.CustomException;
import com.malgn.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class ContentServiceImpl implements ContentService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ContentRepository contentRepository;
    private final RedisUtil redisUtil;

    @Override
    public void contentCreate(String authorization, ContentCreateRequest contentCreateRequest) {

        User user = getUser(authorization); // 생산자
        String creatorName = getUser(authorization).getUsername();
        String title = contentCreateRequest.getTitle();
        String description = contentCreateRequest.getDescription();

        Content content = Content.of(
                user,
                title,
                description,
                0L,
                creatorName,
                null
        );
        contentRepository.save(content);
    }

    @Override
    public void contentUpdate(String authorization, ContentUpdateRequest contentUpdateRequest, Long contentId) {

        Content content = getContent(contentId);
        User modifier = getUser(authorization);

        if ( !(modifier.getRole() == UserRole.ADMIN || modifier.getId().equals(content.getUser().getId())) ) { // admin or creator
            throw new CustomException(ResponseCode.NOT_CREATED_USER);
        }

        String modifierName = modifier.getUsername();
        String title = contentUpdateRequest.getTitle();
        String description = contentUpdateRequest.getDescription();

        content.setLastModifiedBy(modifierName);
        content.setTitle(title);
        content.setDescription(description);

        contentRepository.save(content);

    }

    @Override
    public void contentDelete(String authorization, Long contentId) {

        Content content = getContent(contentId);
        User modifier = getUser(authorization);

        if ( !(modifier.getRole() == UserRole.ADMIN || modifier.getId().equals(content.getUser().getId())) ) { // admin or creator
            throw new CustomException(ResponseCode.NOT_CREATED_USER);
        }

        deleteViewCount(contentId);

        contentRepository.delete(content);
    }

    @Override
    public ContentViewResponse contentView(Long contentId) {
        Content content = getContent(contentId);

        increaseViewCnt(contentId);
        return ContentViewResponse.of(
                content.getTitle(),
                content.getDescription(),
                content.getCreatedBy(),
                content.getLastModifiedBy(),
                content.getCreatedAt(),
                content.getLastModifiedAt(),
                content.getViewCnt());
    }

    @Override
    public Page<ContentListResponse> getContents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));

        Page<Content> contentPage = contentRepository.findAll(pageable);

        return contentPage.map(content ->
                ContentListResponse.of(
                        content.getId(),
                        content.getTitle(),
                        content.getDescription(),
                        content.getCreatedBy(),
                        content.getLastModifiedBy(),
                        content.getCreatedAt(),
                        content.getLastModifiedAt(),
                        content.getViewCnt()
                )
        );
    }
    private User getUser(String authorization) {
        log.info(authorization);
        Long userId = jwtUtil.getUserId(authorization);
        return userRepository.findById(userId).orElseThrow( () -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
    }

    private Content getContent(Long contentId) {
        return contentRepository.findById(contentId).orElseThrow( () -> new CustomException(ResponseCode.CONTENT_NOT_FOUND));
    }

    private void increaseViewCnt(Long contentId) {
        redisUtil.increaseValue("contentViewCnt:" + contentId);
    }

    public void deleteViewCount(Long contentId) {
        redisUtil.deleteValue("contentViewCnt:" + contentId);
    }


}





