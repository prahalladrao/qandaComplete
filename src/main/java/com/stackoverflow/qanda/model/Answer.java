package com.stackoverflow.qanda.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "posts")
public class Answer {
    @Transient
    public static final String seq_name="posts_seq";
    @Id
    private long answerId;
    private String answerBody;
    private List<Comment> comments;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getSetOfUsersUpVoted() {
        return setOfUsersUpVoted;
    }

    public void setSetOfUsersUpVoted(Set<String> setOfUsersUpVoted) {
        this.setOfUsersUpVoted = setOfUsersUpVoted;
    }

    public Set<String> getSetOfUsersDownVoted() {
        return setOfUsersDownVoted;
    }

    public void setSetOfUsersDownVoted(Set<String> setOfUsersDownVoted) {
        this.setOfUsersDownVoted = setOfUsersDownVoted;
    }

    private Set<String> setOfUsersUpVoted;
    private Set<String> setOfUsersDownVoted;
    private long votes;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastupdated;

    public long getVotes() {
        return votes;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

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
        return "Answer{" +
                "answerId=" + answerId +
                ", answerBody='" + answerBody + '\'' +
                ", comments=" + comments +
                '}';
    }



    public String getAnswerBody() {
        return answerBody;
    }

    public long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(long answerId) {
        this.answerId = answerId;
    }

    public void setAnswerBody(String answerBody) {
        this.answerBody = answerBody;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
