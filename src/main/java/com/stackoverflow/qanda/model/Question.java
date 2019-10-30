package com.stackoverflow.qanda.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Document(collection = "posts")
public class Question {
    @Transient
    public static final String seq_name="posts_seq";
    @Id
    private long questionId;
//    @TextIndexed(weight=2F)
//    @Field("questionText")
    private String questionText;
    private String questionBody;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    private String userName;
    private Set<String> setOfUsersUpVoted;
//    private long upvotes;
//
//    public long getUpvotes() {
//        return upvotes;
//    }
//
//    public void setUpvotes(long upvotes) {
//        this.upvotes = upvotes;
//    }
//
//    public long getDownvotes() {
//        return downvotes;
//    }
//
//    public void setDownvotes(long downvotes) {
//        this.downvotes = downvotes;
//    }
//
//    private long downvotes;

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

    private Set<String> setOfUsersDownVoted;
    private List<Comment> comments;
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

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionBody() {
        return questionBody;
    }

    public void setQuestionBody(String questionBody) {
        this.questionBody = questionBody;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
