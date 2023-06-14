package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Inquiry;

public interface InquiryService {
	/* データベースにお問い合わせを詰めるインタフェース */
	void save(Inquiry inquiry);
	
	void update(Inquiry inquiry);
	
	/* お問い合わせリストを読み込むインタフェース　*/
	List<Inquiry> getAll();
}
