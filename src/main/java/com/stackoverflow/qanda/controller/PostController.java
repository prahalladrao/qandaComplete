package com.stackoverflow.qanda.controller;

import com.stackoverflow.qanda.model.Answer;
import com.stackoverflow.qanda.model.PageResponseModel;
import com.stackoverflow.qanda.model.Post;
import com.stackoverflow.qanda.model.User;
import com.stackoverflow.qanda.repository.PostRepo;
import com.stackoverflow.qanda.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class PostController {
    @Autowired
    private PostService postService;
    private Post post;
    @GetMapping
    public ResponseEntity<PageResponseModel> getAllPosts(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "1") int size)
    {
        //System.out.println(userName);
        return new ResponseEntity<PageResponseModel>(postService.getAllPosts(page,size),HttpStatus.OK);

    }
    @PostMapping
    public ResponseEntity<Post> postQuestion(@RequestBody Post post)
     {
         //System.out.println("posting...");
         post=postService.postQuestion(post);
         if(post==null)
             return new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
             return new ResponseEntity<Post>(HttpStatus.OK);
     }
    @PutMapping
    public ResponseEntity<Post> updateQuestion(@RequestBody Post post)
    {
        if(postService.updateQuestion(post))
            return new ResponseEntity<Post>(HttpStatus.OK);

            return new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Post> deleteQuestion(@PathVariable long postId)
    {
        if(postService.deleteQuestion(postId))
            return new ResponseEntity<Post>(HttpStatus.OK);

        return new ResponseEntity<Post>(HttpStatus.BAD_REQUEST);
    }
     @PostMapping("/answer")
     public ResponseEntity postAnswer(@RequestBody Post post)
     {
       if(postService.postAnswer(post))
           return new ResponseEntity(HttpStatus.OK);
         return new ResponseEntity(HttpStatus.BAD_REQUEST);
     }
    @PutMapping("/answer")
    public ResponseEntity updateAnswer(@RequestBody Post post)
    {
        if(postService.updateAnswer(post))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/answer")
    public ResponseEntity deleteAnswer(@RequestBody Post post)
    {
        if(postService.deleteAnswer(post))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/answer/comment")
    public ResponseEntity postAnswerComment(@RequestBody Post post)
    {
        if(postService.postAnswerComment(post))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/answer/comment")
    public ResponseEntity updateAnswerComment(@RequestBody Post post)
    {
        if(postService.updateAnswerComment(post))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/answer/comment")
    public ResponseEntity deleteAnswerComment(@RequestBody Post post)
    {
        if(postService.deleteAnswerComment(post))
            return new ResponseEntity(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public ResponseEntity getPostById(@PathVariable long id)
    {
        post=postService.getPostById(id);
        if(post!=null)
            return new ResponseEntity<Post>(post,HttpStatus.OK);
         return new ResponseEntity(post,HttpStatus.BAD_REQUEST);
    }
    //vote end points
    @PostMapping("/{postId}/{voteType}")
    public ResponseEntity updateQuestionVotes(@PathVariable long postId,@PathVariable String voteType)
    {
        if(postService.updateQuestionVotes(postId,voteType))
            return new ResponseEntity<Post>(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/{postId}/{answerId}/{voteType}")
    public ResponseEntity updateAnswerVotes(@PathVariable long postId,@PathVariable long answerId,@PathVariable String voteType)
    {
        if(postService.updateAnswerVotes(postId,answerId,voteType))
            return new ResponseEntity<Post>(HttpStatus.OK);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    //filtering end points
    @GetMapping("/newest")
    public List<Post> getNewestPosts()
    {
        return postService.getNewestPosts();
    }
    @GetMapping("/oldest")
    public List<Post> getOldestPosts()
    {
        return postService.getOldestPosts();
    }
    @GetMapping("/unanswered")
    public ResponseEntity<List<Post>> getUnansweredPosts()
    {
        List<Post> posts=postService.getUnanswered();
        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }
    //getting posts by tag
    @GetMapping("/tag")
    public ResponseEntity<PageResponseModel> getTaggedPosts(@RequestParam String tag,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "1") int size)
    {
        //System.out.println("controller.."+tag);

        return new ResponseEntity<PageResponseModel>(postService.getTaggedPosts(tag,page,size),HttpStatus.OK);
    }


}
