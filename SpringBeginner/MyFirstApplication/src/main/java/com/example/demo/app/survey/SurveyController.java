package com.example.demo.app.survey;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyServiceImpl;

//サーベイコントローラ
@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	@Autowired
	SurveyServiceImpl surveyService;
	
	// モデルに該当属性を追加して、画面のインデックスに返す
	public String index(Model model) {
		List<Survey> list = surveyService.getAll();
		double average = surveyService.getSatisfactionAvg();
		
		model.addAttribute("surveyList", list);
		model.addAttribute("average", average);
		model.addAttribute("title", "Survey Index");
		
		return "survey/index";
	}
	
	@GetMapping("/form")
	public String form(SurveyForm surveyForm, Model model, @ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Servey Form");
		return "survey/form";
	}
	
	@PostMapping("/form")
	public String form(SurveyForm surveyForm, Model model) {
		model.addAttribute("title", "Servey Form");
		return "survey/form";
	}
	
	//　登録した後、コンファームページに遷移する
	@PostMapping("/confirm")
	public String confirm(
			@Valid @ModelAttribute SurveyForm surveyForm,
	        BindingResult result,
	        Model model) {
		model.addAttribute("surveyForm", surveyForm);
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form");
			return "survey/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "survey/confirm";
	}
	
	/* 入力した値にバリデーションを実装して、
	 * エラーが発生する場合、サーベイフォームに返す、
	 * そうでない場合は新しいサーベイオブジェクトを登録して、データベースに格納して、コンプリート画面に遷移する */
	@PostMapping("/complete")
	public String complete(
			@Valid @ModelAttribute SurveyForm surveyForm,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Survey Form");
			return "survey/form";
		}
		
		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());
		
		surveyService.save(survey);
		
		redirectAttributes.addFlashAttribute("complete", "Completed!");
		return "redirect:/survey/form?complete";
	}
	
}