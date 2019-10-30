package com.stackoverflow.qanda.model;


import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Document(collection="posts")
public class Comment {
    @Transient
    public static final String seq_name="posts_seq";
    private long commentId;
    private String commentBody;
    private LocalDateTime dateCreated;
    private LocalDateTime  dateLastupdated;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getSetOfUsersVoted() {
        return setOfUsersVoted;
    }

    public void setSetOfUsersVoted(Set<String> setOfUsersVoted) {
        this.setOfUsersVoted = setOfUsersVoted;
    }

    private Set<String> setOfUsersVoted;

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateLastupdated() {
        return dateLastupdated;
    }

    public void setDateLastupdated(LocalDateTime dateLastupdated) {
        this.dateLastupdated = dateLastupdated;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentBody='" + commentBody + '\'' +
                '}';
    }

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
