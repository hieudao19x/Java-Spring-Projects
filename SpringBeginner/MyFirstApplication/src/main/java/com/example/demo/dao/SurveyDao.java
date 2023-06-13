package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.entity.Survey;

@Repository
public interface SurveyDao {

	int insertSurvey(Survey survey);
	
	List<Survey> getAll();
}