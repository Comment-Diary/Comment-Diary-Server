package com.commentdiary.src.comment.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Comments {

    @OneToMany(mappedBy = "member")
    private final List<Comment> comments = new ArrayList<>();

    public int totalCount() {
        return comments.size();
    }

    public int likeCount() {
        return (int) comments.stream().filter(c -> c.getIsLike()).count();
    }

}
