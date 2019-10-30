package com.stackoverflow.qanda.service;


import com.stackoverflow.qanda.model.PageResponseModel;
import com.stackoverflow.qanda.model.Post;
import com.stackoverflow.qanda.model.User;
import com.stackoverflow.qanda.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Override
    public Post postQuestion(Post post) {
       return postRepo.postQuestion(post);
    }

    @Override
    public PageResponseModel getAllPosts(int page, int size) {
        return postRepo.getAllPosts(page,size);
    }

    @Override
    public boolean postAnswer(Post post) {
        return postRepo.postAnswer(post);
    }

    @Override
    public boolean postAnswerComment(Post post) {
        return postRepo.postAnswerComment(post);
    }

    @Override
    public Post getPostById(long id) {
        return postRepo.getPostById(id);
    }

    @Override
    public boolean updateQuestionVotes(long postId, String voteType) {
        return postRepo.updateQuestionVotes(postId,voteType);
    }

    @Override
    public boolean updateAnswerVotes(long postId, long answerId, String voteType) {
        return postRepo.updateAnswerVotes(postId, answerId, voteType);
    }

    @Override
    public List<Post> getNewestPosts() {
        return postRepo.getNewestPosts();
    }

    @Override
    public List<Post> getOldestPosts() {
        return postRepo.getOldestPosts();
    }

    @Override
    public List<Post> getUnanswered() {
        return postRepo.getUnanswered();
    }

    @Override
    public boolean updateAnswerComment(Post post) {
        return postRepo.updateAnswerComment(post);
    }

    @Override
    public boolean updateAnswer(Post post) {
        return postRepo.updateAnswer(post);
    }

    @Override
    public boolean updateQuestion(Post post) {
        return postRepo.updateQuestion(post);
    }

    @Override
    public boolean deleteQuestion(long postId) {
        return postRepo.deleteQuestion(postId);
    }

    @Override
    public boolean deleteAnswer(Post post)
    {
        return postRepo.deleteAnswer(post);
    }

    @Override
    public PageResponseModel getTaggedPosts(String tag, int page, int size) {
        return postRepo.getTaggedPosts(tag,page,size);
    }

    @Override
    public boolean deleteAnswerComment(Post post) {
        return postRepo.deleteAnswerComment(post);
    }


}
