package com.malgn.domain.content.entity;

import com.malgn.domain.BaseEntity;
import com.malgn.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents")
public class Content extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "view_count", nullable = false)
    private Long viewCnt;

    @JoinColumn(name = "created_by", nullable = false)
    private String createdBy;

    @JoinColumn(name = "last_modified_by")
    private String lastModifiedBy;

    @Builder
    public Content(User user, String title, String description, Long viewCnt, String createdBy, String lastModifiedBy) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.viewCnt = viewCnt;
        this.createdBy = createdBy;
        this.lastModifiedBy = lastModifiedBy;
    }

    public static Content of(User user, String title, String description, Long viewCnt, String createdBy, String lastModifiedBy) {
        return Content.builder().user(user).title(title).description(description).viewCnt(viewCnt).createdBy(createdBy).lastModifiedBy(lastModifiedBy).build();
    }
}
