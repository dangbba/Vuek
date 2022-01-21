package com.ssafy.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/naver/")
@RequiredArgsConstructor
public class BookSearchController {
	
	@GetMapping("/search")
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

}
