package com.shiyu.question_service.repository;

import com.shiyu.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // becase category is part of hte table, it;s one of the column, data jpa is smart enough to retrieve data by category/
    List<Question> findByCategory(String category);

    // you need to write the SQL query here, its from data jap
    @Query(value = "SELECT q.id FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);
}
