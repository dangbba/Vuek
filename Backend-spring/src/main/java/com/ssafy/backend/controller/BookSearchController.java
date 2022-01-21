package com.ssafy.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.backend.model.BookdetailDto;
import com.ssafy.backend.model.service.BookSearchService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@RestController
public class BookSearchController {
	private final BookSearchService booksearchService; 
	
	@GetMapping("/api/v1/book/{keyword}") 
	public BookdetailDto get(@PathVariable String keyword){ 
		return booksearchService.findByKeyword(keyword); 
		}
}
