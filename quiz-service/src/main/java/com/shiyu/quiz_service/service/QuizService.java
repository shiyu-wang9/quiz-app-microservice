package com.shiyu.quiz_service.service;

import com.shiyu.quiz_service.feign.QuizInterface;
import com.shiyu.quiz_service.model.QuestionWrapper;
import com.shiyu.quiz_service.model.Quiz;
import com.shiyu.quiz_service.model.Response;
import com.shiyu.quiz_service.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        //we need to call the 'generate' url in question service - we will use RestTemplate (part of Spring)
        //http://localhost:8080/question/generate
        // in production, we don't know the url, we need Feign Client (feign almost same http, it provides a declarative way
        // of requesting other service, so you don't have to hardcode the url); and Service discovery - Eureka Server,
        // --> it will solve a problem of a ip address, we can search the service by its name?

        //1. we can see the questions service in eureka server now,

        List<Integer> questions = quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    //we wnat to fetch the quiz obejct from the database
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizRepository.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        ResponseEntity<Integer> score = quizInterface.getScore(responses);
        return score;
    }
}
