package com.ssafy.backend.model.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.backend.model.BookdetailDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service
public class NaverApiClient {
	private final RestTemplate restTemplate; 
	
	private final String CLIENT_ID = "kLkLrpYWuKyFYlpuk5EJ"; 
	private final String CLIENT_SECRET = "TesdkFLBLk"; 
	
	private final String OpenNaverBookUrl_getBooks = "https://openapi.naver.com/v1/search/book.json?query=keyword";
	
	public BookdetailDto requestBook(String keyword) { 
		final HttpHeaders headers = new HttpHeaders(); // 헤더에 key들을 담아준다. 
		headers.set("X-Naver-Client-Id", CLIENT_ID); 
		headers.set("X-Naver-Client-Secret", CLIENT_SECRET); 
		
		final HttpEntity<String> entity = new HttpEntity<>(headers); 
		
		return restTemplate.exchange(OpenNaverBookUrl_getBooks,HttpMethod.GET, entity, BookdetailDto.class, keyword).getBody(); 
		}
	}
