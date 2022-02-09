package com.ssafy.api.controller;

import com.ssafy.api.service.BookSearchService;
import com.ssafy.db.entity.BookDetail;
import lombok.var;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

import java.awt.print.Book;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import static com.sun.el.util.MessageFactory.get;


@RequestMapping("/api/v1/search/")
@RestController
@RequiredArgsConstructor
public class BookSearchController {
	public static final Logger logger = LoggerFactory.getLogger(BookSearchController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final String ALREADY_EXISTS = "already_exists";

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
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(mono.block());
			System.out.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mono.block();
	}

	@ApiOperation(value = "주목 할만한 신간 리스트", response = String.class)
	@GetMapping("/newSpecial")
	public String newSpecial() {
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
				).exchangeToMono(response -> {
					return response.bodyToMono(String.class);
				});
		System.out.println("mono.block(): " + mono.block() + "/" + mono.block().getClass());
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(mono.block());
			JSONArray targetArray = (JSONArray) obj.get("item");
			int list_cnt = targetArray.size();

			for (int i = 0; i < list_cnt; i++) {
				JSONObject singleBookData = (JSONObject) targetArray.get(i);
				BookDetail singleBook = new BookDetail();
				int price = Integer.parseInt(String.valueOf(singleBookData.get("priceStandard")));
				singleBook.setTitle(singleBookData.get("title").toString());
				singleBook.setTitleUrl(singleBookData.get("cover").toString());
				singleBook.setAuthor(singleBookData.get("author").toString());
				singleBook.setOverview(singleBookData.get("description").toString());
				singleBook.setPrice(price);
				singleBook.setPublisher(singleBookData.get("publisher").toString());
				singleBook.setPublishDate(singleBookData.get("pubDate").toString());
				singleBook.setSailStatus(singleBookData.get("stockStatus").toString());
				singleBook.setIsbn(singleBookData.get("isbn13").toString());
				createBookDetail(singleBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		try {
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(mono.block());
			JSONArray targetArray = (JSONArray) obj.get("item");
			int list_cnt = targetArray.size();

			for (int i = 0; i < list_cnt; i++) {
				JSONObject singleBookData = (JSONObject) targetArray.get(i);
				BookDetail singleBook = new BookDetail();
				int price = Integer.parseInt(String.valueOf(singleBookData.get("priceStandard")));
				singleBook.setTitle(singleBookData.get("title").toString());
				singleBook.setTitleUrl(singleBookData.get("cover").toString());
				singleBook.setAuthor(singleBookData.get("author").toString());
				singleBook.setOverview(singleBookData.get("description").toString());
				singleBook.setPrice(price);
				singleBook.setPublisher(singleBookData.get("publisher").toString());
				singleBook.setPublishDate(singleBookData.get("pubDate").toString());
				singleBook.setSailStatus(singleBookData.get("stockStatus").toString());
				singleBook.setIsbn(singleBookData.get("isbn13").toString());
				createBookDetail(singleBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mono.block();
	}

	@ApiOperation(value = "bookDetail 저장", response = String.class)
	@PostMapping("/create")
	public ResponseEntity<String> createBookDetail(@RequestBody BookDetail bookdetail) throws Exception {
		if (isExistBookDetail(bookdetail.getIsbn()) == false) {
			try {
				bookSearchService.createBookDetail(bookdetail);
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(FAIL, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			return new ResponseEntity<String>(ALREADY_EXISTS, HttpStatus.ALREADY_REPORTED);
		}
	}
	@ApiOperation(value = "ISBN 값을 이용하여 데이터베이스에 책이 저장되어있는지 검사", response = boolean.class)
	@GetMapping("/{isbn}")
	public Boolean isExistBookDetail(@PathVariable String isbn) throws Exception {
		return bookSearchService.isExistBookDetail(isbn);
	}
}


