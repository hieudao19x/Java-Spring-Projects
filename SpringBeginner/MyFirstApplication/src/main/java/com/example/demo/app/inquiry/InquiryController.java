package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryNotFoundException;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	private final InquiryService inquiryService;
	
	@Autowired
	public InquiryController (InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	// モデルに該当属性を追加して、画面のインデックスに返す
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("Jamie");
//		inquiry.setEmail("sample4@example.com");
//		inquiry.setContents("Hello.");
//		
//		inquiryService.update(inquiry);
		
//		try {
//			inquiryService.update(inquiry);
//		} catch (InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "Inquiry Index");
		
		return "inquiry/index_boot";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, 
			Model model, 
			@ModelAttribute("complete") String complete) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "Inquiry Form");
		return "inquiry/form";
	}
	
	//　登録した後、コンファームページに遷移する
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		model.addAttribute("inquiryForm", inquiryForm);
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		model.addAttribute("title", "Confirm Page");
		return "inquiry/confirm";
	}
	
	//　入力した値をバリデーションして、エラーが発生する場合、フォームに返す、そうでない場合はお問い合わせを登録する
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "InquiryForm");
			return "inquiry/form";
		}
		
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
		
		redirectAttributes.addFlashAttribute("complete","Registered!");
		return "redirect:/inquiry/form";
	}
	
//	@ExceptionHandler(InquiryNotFoundException.class)
//	public String handleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
}
