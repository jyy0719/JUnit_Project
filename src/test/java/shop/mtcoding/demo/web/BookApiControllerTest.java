package shop.mtcoding.demo.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import shop.mtcoding.demo.service.BookService;
import shop.mtcoding.demo.web.dto.request.BookSaveRequestDto;

// 통합테스트 (controller, service, repository)
// controller 를 단위 테스트 하고 싶으면 Mock 사용해서 하면 된다.
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //
public class BookApiControllerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate testRestTemp;

    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    // 외부 api에서 들어오는 데이터를 가정해야한다.
    @Test
    public void saveBook_test() throws Exception {
        // given
        // saveBook로 들어오는 타입은 json 형태로 들어온다.
        // 그러나 controller에서 @RequestBody 어노테이션을 통해 json타입 -> BookSaveRequestDto타입으로
        // 변환해준다.
        // 그럼 지금 테스트에서는 json형태의 가짜데이터를 만들어 controller의 saveBook 매서드로 보내야지
        BookSaveRequestDto bookSaveRequestDto = new BookSaveRequestDto();
        bookSaveRequestDto.setAuthor("홍길동 자서전");
        bookSaveRequestDto.setTitle("스프링1강");
        String body = objectMapper.writeValueAsString(bookSaveRequestDto);

        // when
        // 해더와 바디를 하나의 오브젝트로 만들기
        HttpEntity<String> request = new HttpEntity<String>(body, headers);
        ResponseEntity<String> response = testRestTemp.exchange(
                "/api/v1/book", // Host
                HttpMethod.POST, // Request Method
                request, // RequestBody
                String.class); // return Object
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());

        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        assertThat(title).isEqualTo("스프링1강");
        assertThat(author).isEqualTo("홍길동 자서전");
    }
}
