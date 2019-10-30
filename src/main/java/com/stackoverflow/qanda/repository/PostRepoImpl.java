package com.stackoverflow.qanda.repository;


import com.stackoverflow.qanda.model.*;
import com.stackoverflow.qanda.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository
public class PostRepoImpl implements PostRepo {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private SequenceGeneratorService sequenceGenerator;
    @Autowired
    PostCrudRepo postCrudRepo;
    @Autowired
    private CrudRepository crudRepository;
    private Post postInDb;
    private Question questionInDb;
    private Answer answerInDb;
    private Answer answer;
    private Comment comment;
    private MongoOperations mongoOperations;
    @Autowired
    private JwtUser jwtUser;
    @Autowired
    private LocalDateTime date;

    public Post postQuestion(Post post)
    {

        post.setPostId(sequenceGenerator.generateSequence(Question.seq_name));
        //System.out.println(post.getQuestion());
        //System.out.println(jwtUser.getUserName());
        post.getQuestion().setUserName(jwtUser.getUserName());
        post.getQuestion().setQuestionId(sequenceGenerator.generateSequence(Question.seq_name));
        //date=new Date(System.currentTimeMillis());
        post.getQuestion().setDateCreated(date);
        post.getQuestion().setDateLastupdated(date);
        post.setDateCreated(date);
        post.setDateLastupdated(date);
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("tags",3F)
                .build();
        mongoTemplate.indexOps(Post.class).ensureIndex(textIndex);
        // return mongoTemplate.findOne(query, Post.class);
        return mongoTemplate.save(post);
    }
    @Override
    public boolean updateQuestion(Post post) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        if(postInDb!=null)
        {
            Question questionToBeUpdated=post.getQuestion();
            if(questionToBeUpdated==null)
                return false;
            else
            {
                Question questionInDb=postInDb.getQuestion();
                //date=new Date(System.currentTimeMillis());
                questionInDb.setDateLastupdated(date);
                if(questionToBeUpdated.getQuestionText()!=null)
                questionInDb.setQuestionText(questionToBeUpdated.getQuestionText());
                if(questionToBeUpdated.getQuestionBody()!=null)
                questionInDb.setQuestionBody(questionToBeUpdated.getQuestionBody());
                postInDb.setDateLastupdated(date);

            }
            postInDb=mongoTemplate.save(postInDb);
            if(postInDb!=null)
                return true;
            return false;
        }
        else
        {
            post.setPostId(sequenceGenerator.generateSequence(Question.seq_name));
            Question question=post.getQuestion();
            question.setQuestionId(sequenceGenerator.generateSequence(Question.seq_name));
            //date=new Date(System.currentTimeMillis());
            question.setDateCreated(date);
            question.setDateLastupdated(date);
            post.setDateLastupdated(date);
            postInDb=mongoTemplate.save(post);
            if(postInDb==null)
                return false;
            return true;
        }
    }

    @Override
    public boolean deleteQuestion(long postId) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(postId)),Post.class);
        if(postInDb==null)
            return false;
        else
        {
           if(mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(postId)),Post.class)==null)
           return false;
           return true;
        }
    }

    @Override
    public boolean deleteAnswer(Post post) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        if(postInDb==null)
            return false;
        else
        {
            long answerId=post.getAnswers().get(0).getAnswerId();
            Answer answerToBeDeleted=null;
            List<Answer> answers=postInDb.getAnswers();
            for(Answer answer: answers)
            {
                if(answer.getAnswerId()==answerId)
                {
                     answerToBeDeleted=answer;

                    System.out.println("answer removed");

                }

            }
            if(answerToBeDeleted==null)
                return false;
            else
                postInDb.getAnswers().remove(answerToBeDeleted);
            postInDb=mongoTemplate.save(postInDb);
            if(postInDb.getAnswers().contains(answerToBeDeleted))
                return false;

            return true;
        }
    }


    public PageResponseModel getTaggedPosts(String tag, int page, int size) {

            TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(tag);
            Query query = TextQuery.queryText(criteria);
            int numberOfPosts=mongoTemplate.find(query,Post.class,"posts").size();
            query=TextQuery.queryText(criteria).with(PageRequest.of(page,size));
            return new PageResponseModel(numberOfPosts,postCrudRepo.findAllBy(criteria,PageRequest.of(page,size)));
            //return postCrudRepo.findAllBy(criteria);
            //System.out.println(crudRepository.findAllBy(criteria).size());
            //return null;


    }

    @Override
    public boolean deleteAnswerComment(Post post) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        if(postInDb==null)
            return false;
        else
        {
            Answer answerToBeEffected=null;
            long answerId=post.getAnswers().get(0).getAnswerId();
            long commentId=post.getAnswers().get(0).getComments().get(0).getCommentId();
            Comment commentToBeDeleted=null;
            List<Answer> answers=postInDb.getAnswers();
            int answerPosition=-1,commentPosition=-1;
            for(int ansPos=0;ansPos<answers.size();ansPos++)
            {
                if(answers.get(ansPos).getAnswerId()==answerId)
                {
                    answerToBeEffected=answers.get(ansPos);
                    answerPosition=ansPos;
                    for(int comPos=0;comPos<answerToBeEffected.getComments().size();comPos++)
                    {
                        if (commentId == answerToBeEffected.getComments().get(comPos).getCommentId()) {
                            commentToBeDeleted = answerToBeEffected.getComments().get(comPos);
                            commentPosition=comPos;
                            if (!answerToBeEffected.getComments().remove(commentToBeDeleted))
                                return false;
                        }
                    }

                    System.out.println("comment removed");

                }

            }
            if(commentToBeDeleted==null)
                return false;
            postInDb=mongoTemplate.save(postInDb);
            if(postInDb.getAnswers().get(answerPosition).getComments().contains(commentToBeDeleted))
                return false;

            return true;
        }
    }

    @Override
    public PageResponseModel getAllPosts(int page, int size)
    {
        int numberOfPosts=mongoTemplate.findAll(Post.class,"posts").size();
        return new PageResponseModel(numberOfPosts,postCrudRepo.findAll(PageRequest.of(page,size)));
    }




    @Override
    public boolean postAnswer(Post post) {
        postInDb =mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);

        //date=new Date(System.currentTimeMillis());
        if(postInDb==null)
            throw new NullPointerException("No post with given post id");
        if(post.getAnswers()!=null)
        {
            answer=post.getAnswers().get(0);
            answer.setUserName(jwtUser.getUserName());
            answer.setDateCreated(date);
            answer.setDateLastupdated(date);

        }
        if(answer!=null)
            answer.setAnswerId(sequenceGenerator.generateSequence(Answer.seq_name));
        if(postInDb.getAnswers()==null)
            postInDb.setAnswers(new ArrayList<Answer>());
        postInDb.getAnswers().add(answer);
        postInDb.setDateLastupdated(date);
        postInDb=mongoTemplate.save(postInDb);
        //System.out.println(postInDb.getAnswers().get(0));
//        if(postInDb.getAnswers().get(postInDb.getAnswers().size()-1).equals(post.getAnswers().get(0)))
        if(postInDb!=null)
        return true;
        return false;
    }
    

    @Override
    public boolean postAnswerComment(Post post) {
        //System.out.println(post);
        postInDb =mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        //System.out.println(postInDb);
        List<Answer> answers=postInDb.getAnswers();
        Comment comment=null;
        for(Answer answer:answers)
        {
                    if (answer.getAnswerId()==post.getAnswers().get(0).getAnswerId())
                    {
                        //date=new Date(System.currentTimeMillis());
                        comment=post.getAnswers().get(0).getComments().get(0);
                        comment.setCommentId(sequenceGenerator.generateSequence(Comment.seq_name));
                        comment.setUserName(jwtUser.getUserName());
                        comment.setDateCreated(date);
                        comment.setDateLastupdated(date);
                        if(answer.getComments()==null)
                            answer.setComments(new ArrayList<Comment>());
                        answer.getComments().add(comment);
                    }
        }
        if(comment==null)
            return false;
        postInDb=mongoTemplate.save(postInDb);
        if(postInDb!=null)
            return true;
        return false;
    }

    @Override
    public Post getPostById(long id) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(id)),Post.class);
        return postInDb;
    }

    @Override
    public boolean updateQuestionVotes(long postId, String voteType) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(postId)),Post.class);
        questionInDb=postInDb.getQuestion();
        String userName=jwtUser.getUserName();
        if(postInDb!=null)
        {
          if((voteType!=null)&&voteType.equals("upvote"))
          {

              if(questionInDb.getSetOfUsersUpVoted()==null)
                  questionInDb.setSetOfUsersUpVoted(new HashSet<String>());
              if(questionInDb.getSetOfUsersUpVoted().contains(userName))//if the username is already present in upvoted list, then he is undoing the upvote
              {
                  questionInDb.setVotes( questionInDb.getVotes()-1);
                  questionInDb.getSetOfUsersUpVoted().remove(userName);
              }
              else
              {
                  questionInDb.setVotes( questionInDb.getVotes()+1);
                  if(questionInDb.getSetOfUsersDownVoted()!=null&&questionInDb.getSetOfUsersDownVoted().contains(userName))
                  {
                      questionInDb.setVotes( questionInDb.getVotes()+1);
                      if(!questionInDb.getSetOfUsersDownVoted().remove(userName))
                          return false;
                  }
                  if(!questionInDb.getSetOfUsersUpVoted().add(userName))
                      return false;
              }

          }
          else if((voteType!=null)&&voteType.equals("downvote"))
          {


              if(questionInDb.getSetOfUsersDownVoted()==null)
                  questionInDb.setSetOfUsersDownVoted(new HashSet<String>());
              if(questionInDb.getSetOfUsersDownVoted().contains(userName))
              {
                  questionInDb.setVotes( questionInDb.getVotes()+1);
                  questionInDb.getSetOfUsersDownVoted().remove(userName);

              }
              else
              {
                  questionInDb.setVotes( questionInDb.getVotes()-1);
                  if (questionInDb.getSetOfUsersUpVoted() != null && questionInDb.getSetOfUsersUpVoted().contains(userName))
                  {
                      questionInDb.setVotes( questionInDb.getVotes()-1);
                      if (!questionInDb.getSetOfUsersUpVoted().remove(userName))
                          return false;
                  }
                  if (!questionInDb.getSetOfUsersDownVoted().add(userName))
                      return false;
              }
          }
          else
              return false;
          mongoTemplate.save(postInDb);
          return true;
        }

        return false;
    }

    @Override
    public boolean updateAnswerVotes(long postId, long answerId, String voteType) {

        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(postId)),Post.class);
        if(postInDb!=null)
        {

                List<Answer> answers = postInDb.getAnswers();
                if(answers.isEmpty())
                    return false;
                String userName=jwtUser.getUserName();
                for(Answer answer: answers)
                {
                    if(answer.getAnswerId()==answerId)
                    {
                        if((voteType!=null)&&voteType.equals("upvote"))
                        {

                            if(answer.getSetOfUsersUpVoted()==null)
                                answer.setSetOfUsersUpVoted(new HashSet<String>());
                            if(answer.getSetOfUsersUpVoted().contains(userName))//if the username is already present in upvoted list, then he is undoing the upvote
                            {
                                answer.setVotes( answer.getVotes()-1);
                                answer.getSetOfUsersUpVoted().remove(userName);
                            }
                            else
                            {
                                answer.setVotes(answer.getVotes()+1);
                                if (answer.getSetOfUsersDownVoted() != null && answer.getSetOfUsersDownVoted().contains(userName))
                                {
                                    answer.setVotes(answer.getVotes()+1);
                                    if (!answer.getSetOfUsersDownVoted().remove(userName))
                                        return false;
                                }
                                if (!answer.getSetOfUsersUpVoted().add(userName))
                                    return false;
                            }
                        }
                        else if((voteType!=null)&&voteType.equals("downvote"))
                        {

                            if(answer.getSetOfUsersDownVoted()==null)
                                answer.setSetOfUsersDownVoted(new HashSet<String>());
                            if(answer.getSetOfUsersDownVoted().contains(userName))
                            {
                                answer.setVotes( answer.getVotes()+1);
                                answer.getSetOfUsersDownVoted().remove(userName);

                            }
                            else
                            {
                                answer.setVotes(answer.getVotes()-1);
                                if(answer.getSetOfUsersUpVoted()!=null&&answer.getSetOfUsersUpVoted().contains(userName))
                                {
                                    answer.setVotes(answer.getVotes()-1);
                                    if(!answer.getSetOfUsersUpVoted().remove(userName))
                                        return false;
                                }
                                if(!answer.getSetOfUsersDownVoted().add(userName))
                                return false;
                            }
                        }
                        else
                            return false;
                    }
                }
                postInDb=mongoTemplate.save(postInDb);
                if(postInDb!=null)
                    return true;
                return false;
        }
        return false;
    }

    @Override
    public List<Post> getNewestPosts() {
        Sort sort=Sort.by("dateCreated").descending();
        return postCrudRepo.findAll(sort);
    }

    @Override
    public List<Post> getOldestPosts() {
        Sort sort=Sort.by("dateCreated").ascending();
        return postCrudRepo.findAll(sort);
    }

    @Override
    public List<Post> getUnanswered()
    {
//       QPost.ans
        Query query=new Query();
        query.addCriteria(Criteria.where("answers").is(null));
        List<Post> posts=mongoTemplate.find(query,Post.class);
        Query query2=new Query();
        query2.addCriteria(Criteria.where("answers").size(0));
        List<Post> posts1=mongoTemplate.find(query2,Post.class);
        posts.addAll(posts1);
        return posts;
    }

    @Override
    public boolean updateAnswerComment(Post post)
    {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        if(postInDb!=null)
        {

            List<Answer> answers = postInDb.getAnswers();
            if(answers.isEmpty())
                return false;
            Answer answerToBeUpdated=post.getAnswers().get(0);
            if(answerToBeUpdated==null)
                return false;
            for(Answer answer: answers)
            {
                if(answer.getAnswerId()==answerToBeUpdated.getAnswerId())
                {
                   for(Comment comment:answer.getComments())
                   {
                      if(comment.getCommentId()==answerToBeUpdated.getComments().get(0).getCommentId())
                      {
                          //date=new Date(System.currentTimeMillis());
                          comment.setDateLastupdated(date);
                          comment.setCommentBody(answerToBeUpdated.getComments().get(0).getCommentBody());
                          postInDb.setDateLastupdated(date);
                      }
                   }
                }
            }
            postInDb=mongoTemplate.save(postInDb);
            if(postInDb!=null)
                return true;
            return false;
        }
        return false;
    }

    @Override
    public boolean updateAnswer(Post post) {
        postInDb=mongoTemplate.findOne(new Query().addCriteria(Criteria.where("_id").is(post.getPostId())),Post.class);
        if(postInDb!=null)
        {

            List<Answer> answers = postInDb.getAnswers();
            if(answers.isEmpty())
                return false;
            Answer answerToBeUpdated=post.getAnswers().get(0);
            if(answerToBeUpdated==null)
                return false;
            for(Answer answer: answers)
            {
                if(answer.getAnswerId()==answerToBeUpdated.getAnswerId())
                {

                            //date=new Date(System.currentTimeMillis());
                            answer.setDateLastupdated(date);
                            answer.setAnswerBody(answerToBeUpdated.getAnswerBody());
                            postInDb.setDateLastupdated(date);

                }
            }
            postInDb=mongoTemplate.save(postInDb);
            if(postInDb!=null)
                return true;
            return false;
        }
        return false;
    }


}
