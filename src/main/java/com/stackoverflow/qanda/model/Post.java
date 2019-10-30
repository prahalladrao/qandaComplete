package com.stackoverflow.qanda.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;
@Document(collection = "posts")
public class Post {
    @Transient
    public static final String seq_name="posts_seq";

    @Id
    private long postId;
    private Question question;
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    private LocalDateTime dateCreated;
    private LocalDateTime dateLastupdated;

    private List<String> tags;


    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", question=" + question +
                ", answers=" + answers +
                ", dateCreated=" + dateCreated +
                ", dateLastupdated=" + dateLastupdated +
                ", tags=" + tags +
                '}';
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

    public long  getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }



}
