package com.stackoverflow.qanda.repository;

import com.stackoverflow.qanda.model.PageResponseModel;
import com.stackoverflow.qanda.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo
{

    Post postQuestion(Post post);

    PageResponseModel getAllPosts(int page, int size);

    boolean postAnswer(Post post);

    boolean postAnswerComment(Post post);

    Post getPostById(long id);

    boolean updateQuestionVotes(long questionId, String voteType);

    boolean updateAnswerVotes(long postId, long answerId, String voteType);

    List<Post> getNewestPosts();

    List<Post> getOldestPosts();

    List<Post> getUnanswered();

    boolean updateAnswerComment(Post post);

    boolean updateAnswer(Post post);

    boolean updateQuestion(Post post);

    boolean deleteQuestion(long postId);

    boolean deleteAnswer(Post post);

    PageResponseModel getTaggedPosts(String tag, int page, int size);

     boolean deleteAnswerComment(Post post);
}
