package com.ssafy.api.controller;

import com.ssafy.api.service.BookSearchService;
import com.ssafy.db.entity.BookDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequestMapping("/api/v1/search/")
@RestController
@RequiredArgsConstructor
public class BookSearchController {
	public static final Logger logger = LoggerFactory.getLogger(BookSearchController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BookSearchService bookSearchService;

	@ApiOperation(value = "네이버 api를 이용한 검색기능", response = String.class)
	@GetMapping("/naver")
	public String search(@RequestParam String query) {
		Mono<String> mono = WebClient.builder().baseUrl("https://openapi.naver.com")
				.build().get()
				.uri(builder -> builder.path("/v1/search/book.json")
						.queryParam("query", query)
						.build()
				)
				.header("X-Naver-Client-Id", "kLkLrpYWuKyFYlpuk5EJ")
				.header("X-Naver-Client-Secret", "TesdkFLBLk")
				.exchangeToMono(response -> {
					return response.bodyToMono(String.class);
				});
		return mono.block();
	}

	@ApiOperation(value = "주목 할만한 신간 리스트", response = String.class)
	@GetMapping("/newspecial")
	public String newspecial() {
		Mono<String> mono = WebClient.builder().baseUrl("http://www.aladin.co.kr")
				.build().get()
				.uri(builder -> builder.path("/ttb/api/ItemList.aspx")
						.queryParam("ttbkey", "ttbjesonk31355001")
						.queryParam("QueryType", "ItemNewSpecial")
						.queryParam("SearchTarget", "Book")
						.queryParam("Version", "20131101")
						.queryParam("isbn", "isbn13")
						.queryParam("output", "js")
						.build()
				)
				.exchangeToMono(response -> {
					return response.bodyToMono(String.class);
				});
		return mono.block();
	}
	@ApiOperation(value = "베스트셀러", response = String.class)
	@GetMapping("/bestseller")
	public String bestseller() {
		Mono<String> mono = WebClient.builder().baseUrl("http://www.aladin.co.kr")
				.build().get()
				.uri(builder -> builder.path("/ttb/api/ItemList.aspx")
						.queryParam("ttbkey", "ttbjesonk31355001")
						.queryParam("QueryType", "Bestseller")
						.queryParam("SearchTarget", "Book")
						.queryParam("Version", "20131101")
						.queryParam("output", "js")
						.queryParam("isbn", "isbn13")
						.build()
				)
				.exchangeToMono(response -> {
					return response.bodyToMono(String.class);
				});
		return mono.block();
	}

	@ApiOperation(value = "bookDetail 저장", response = String.class)
	@PostMapping("/create")
	public ResponseEntity<String> createBookDetail(@RequestBody BookDetail bookdetail) throws Exception {
		logger.debug("bookDetailCreate 호출");
		try {
			bookSearchService.createBookDetail(bookdetail);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(FAIL, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}


