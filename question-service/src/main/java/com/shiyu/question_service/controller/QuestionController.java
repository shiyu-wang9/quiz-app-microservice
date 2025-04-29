package com.shiyu.question_service.controller;

import com.shiyu.question_service.model.Question;
import com.shiyu.question_service.model.QuestionWrapper;
import com.shiyu.question_service.model.Response;
import com.shiyu.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {


    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    //{category} means it's a variable in the URL - path variable
    @GetMapping("category/{cate}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cate")  String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    //find a way to generate the quiz, now the quiz service doesn't have acces to the question database
    // question service will provide question to te quiz
    //1. generate quiz
    //2.getQuestions (questionID)
    //3. calculate score

    //1.generate
    // quiz service request the quesiton service and pass 2 values
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
        (@RequestParam String categoryName, @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }

    //2.get questions
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    //3.getScore
    // students send answeres (request) to the quiz service, quiz service send it to the question service,
    // question service send the score back?
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }

}
