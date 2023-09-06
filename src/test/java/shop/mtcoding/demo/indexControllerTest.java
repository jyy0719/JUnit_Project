package shop.mtcoding.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// static 클래스의 경우에는 이와같이 사용할 수 있다함.
// 그러나, 매번 import를 건드려서 static 넣고, .*넣고 귀찮으니, file->preference->setting->favorite static members 밑에 edit in setting.json 추가해서 사용가능
// edit in setting.json  >  "java.completion.favoriteStaticMembers": [..여기에 static 걸어놓을 클래스 추가..]
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc // Spring Boot 테스트를 위한 설정
@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // Spring Boot 테스트를 위한 설정
public class indexControllerTest {

    @Autowired
    private MockMvc mvc; // Spring 애플리케이션의 HTTP 요청을 시뮬레이션하고, 결과를 검증하는 데 사용

    @Test // JUnit 테스트 메서드
    void index_test() throws Exception {
        // MockMvc를 사용하여 "/index" 경로로 GET 요청. 웹 애플리케이션의 "/index" 엔드포인트를 호출하는 것을 시뮬레이트
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/index"));
        // 요청의 결과를 검증. HTTP 응답 상태 코드가 201(Created)인지 확인
        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
