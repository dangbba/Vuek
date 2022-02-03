package com.ssafy.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequestMapping("/api/v1/search/")
@RestController
@RequiredArgsConstructor
public class BookSearchController {

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
						.build()
				)
				.exchangeToMono(response -> {
					return response.bodyToMono(String.class);
				});
		return mono.block();
	}

}


