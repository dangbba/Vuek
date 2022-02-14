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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

>>>>>>> 786f0cbbf95f415330cb0d5dfa0fac5fbdf2fed1
import static com.sun.el.util.MessageFactory.get;
>>>>>>> 013eeb3aa19da1a754f4f5078443c931b4d6c3b4


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
						.queryParam("display", "100")
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

			JSONArray targetArray = (JSONArray) obj.get("items");
			int list_cnt = targetArray.size();
			for (int i = 0; i < list_cnt; i++) {
				JSONObject singleBookData = (JSONObject) targetArray.get(i);
				BookDetail singleBook = new BookDetail();
				int price = Integer.parseInt(String.valueOf(singleBookData.get("price")));
				singleBook.setTitle(singleBookData.get("title").toString());
				singleBook.setTitleUrl(singleBookData.get("image").toString());
				singleBook.setAuthor(singleBookData.get("author").toString());

				int descriptionNum = singleBookData.get("description").toString().length();
				if (descriptionNum > 200) {
					singleBook.setOverview(singleBookData.get("description").toString().substring(0, 200));
				} else {
					singleBook.setOverview(singleBookData.get("description").toString());
				}

				singleBook.setPrice(price);
				singleBook.setPublisher(singleBookData.get("publisher").toString());
				singleBook.setPublishDate(singleBookData.get("pubdate").toString());
				singleBook.setSailStatus(null);
				singleBook.setIsbn(singleBookData.get("isbn").toString().substring(11));
				createBookDetail(singleBook);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mono.block();
	}

	public double randomNum(int min, int max){
<<<<<<< HEAD
		double randNum = Math.floor(Math.random()*(100) + 1);
=======
<<<<<<< HEAD
		var randNum = Math.floor(Math.random()*(max-min+1)) + min;
=======
		double randNum = Math.floor(Math.random()*(100) + 1);
>>>>>>> 786f0cbbf95f415330cb0d5dfa0fac5fbdf2fed1
>>>>>>> 013eeb3aa19da1a754f4f5078443c931b4d6c3b4
		return randNum;
	}
	@ApiOperation(value = "주목 할만한 신간 리스트", response = String.class)
	@GetMapping("/newSpecial")
	public String newSpecial() {
		double randomNumber = randomNum(1, 100);
		String randNum = String.valueOf(randomNumber);
		Mono<String> mono = WebClient.builder().baseUrl("http://www.aladin.co.kr")
				.build().get()
				.uri(builder -> builder.path("/ttb/api/ItemList.aspx")
						.queryParam("ttbkey", "ttbjesonk31355001")
						.queryParam("QueryType", "ItemNewSpecial")
						.queryParam("SearchTarget", "Book")
						.queryParam("Version", "20131101")
						.queryParam("isbn", "isbn13")
						.queryParam("output", "js")
						.queryParam("Cover", "Big")
						.queryParam("MaxResults", "100")
<<<<<<< HEAD
						.queryParam("Start", randNum)
=======
<<<<<<< HEAD
						.queryParam("Start", "randomNum(1, 100)")
=======
						.queryParam("Start", randNum)
>>>>>>> 786f0cbbf95f415330cb0d5dfa0fac5fbdf2fed1
>>>>>>> 013eeb3aa19da1a754f4f5078443c931b4d6c3b4
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
						.queryParam("Cover", "Big")
						.queryParam("MaxResults", "100")
						.queryParam("Start", "randomNum(1, 100)")
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
	public Integer createBookDetail(@RequestBody BookDetail bookdetail) throws Exception {
		if (isExistBookDetail(bookdetail.getIsbn()) == 0) {
			try {
				bookSearchService.createBookDetail(bookdetail);
				return bookdetail.getId();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}
	}
	@ApiOperation(value = "ISBN 값을 이용하여 데이터베이스에 책이 저장되어있는지 검사 및 pk값 반환", response = boolean.class)
	@GetMapping("/{isbn}")
	public Integer isExistBookDetail(@PathVariable String isbn) throws Exception {
		return bookSearchService.isExistBookDetail(isbn);
	}
}


