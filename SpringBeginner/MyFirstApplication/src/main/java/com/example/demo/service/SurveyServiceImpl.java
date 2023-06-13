package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Survey;
import com.example.demo.dao.SurveyDao;

@Service
public class SurveyServiceImpl implements SurveyService{

	private final SurveyDao dao;
	
	@Autowired
	SurveyServiceImpl(SurveyDao dao){
		this.dao = dao;
	}
	
	@Override
	public int save(Survey survey) {
		return dao.insertSurvey(survey);
	}

	@Override
	public List<Survey> getAll() {
		return dao.getAll();
	}

	@Override
	public double getSatisfactionAvg() {
		// TODO Auto-generated method stub
		return 0;
	}
}