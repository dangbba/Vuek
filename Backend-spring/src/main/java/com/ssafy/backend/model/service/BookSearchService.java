package com.ssafy.backend.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.backend.model.BookdetailDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service
public class BookSearchService {
	private final NaverApiClient naverApiClient;
	
	@Transactional(readOnly = true) 
	public BookdetailDto findByKeyword(String keyword) { 
		return naverApiClient.requestBook(keyword); 
		}
}
