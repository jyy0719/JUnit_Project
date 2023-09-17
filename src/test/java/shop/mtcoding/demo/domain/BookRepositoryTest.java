package shop.mtcoding.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest // db와 관련된 컴포넌트만 메모리에 로딩, 컨트롤러,서비스는 메모리에 로딩되지 않음.
public class BookRepositoryTestjava {

    @Autowired
    private BookRepository bookRepository;

    // 책 목록
    @Test
    @BeforeEach
    public void 데이터준비() {
        Book book = Book.builder()
                .title("공룡의 시대")
                .author("박지용")
                .build();

        Book bookRp = bookRepository.save(book);
        bookRp.getId();
    }

    // 책 등록
    @Test
    // @Order(1)
    public void 책등록하기_test() {
        // given (데이터준비)
        System.out.println("책 등록하기");
        Book book = Book.builder()
                .author("stella")
                .title("산장의 100년 역사")
                .build();
        // when (테스트실행)
        Book bookPS = bookRepository.save(book);
        // then (검증)
        assertEquals(book.getAuthor(), bookPS.getAuthor()); // success
        // assertEquals("stellaa", bookPS.getAuthor()); // fail
        assertEquals(book.getTitle(), bookPS.getTitle()); // success
    }

    // 책 목록보기
    @Test
    // @Order(2)
    public void 책목록보기_test() {
        // given
        // -- @BeforeEach 매서드로 상단에서 test 실행 전 데이터 넣고 있음.
        // when
        List<Book> book = bookRepository.findAll();
        // then
        assertThat(book.size()).isEqualTo(0);
    }

    // 책 한건보기
    @Test
    // @Order(3)
    public void 책한건보기_test() {
        // given
        // -- @BeforeEach 매서드로 상단에서 test 실행 전 데이터 넣고 있음.
        // when
        Book book = bookRepository.findById(1L).get();
        // then
        assertEquals("공룡의 시대 ", book.getTitle());
        assertEquals("박지용", book.getAuthor());

    }

    // 책 수정하기
    @Test
    @Sql("classpath:db/tableInit.sql") // id를 넣어 실행하는 테스트 일 땐 붙여주는 것이 좋음
    public void 책수정하기_test() {
        // given
        // -- @BeforeEach 매서드로 상단에서 test 실행 전 데이터 넣고 있음.
        Long id_ = 1L;
        String title = "맛동산의 비밀";
        String author = "유정남";
        Book updateBook = new Book(id_, title, author);

        // when
        Book book = bookRepository.save(updateBook);

        // then
        assertEquals(id_, book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    // 책 삭제하기
    @Test
    @Sql("classpath:db/tableInit.sql") // id를 넣어 실행하는 테스트 일 땐 붙여주는 것이 좋음
    public void 책삭제하기_test() {
        // given
        // -- @BeforeEach 매서드로 상단에서 test 실행 전 데이터 넣고 있음.
        Long id = 1L;
        // when
        // bookRepository.deleteById(id);
        // then
        Optional<Book> bookPs = bookRepository.findById(id);
        // 방법 1 :
        // if(bookPs.isPresent()) {...}
        // 방법 2 :
        assertFalse(bookPs.isPresent()); // bookPs.isPresent()가 true면 assertFalse는 false ,
                                         // bookPs.isPresent()가 false면 assertFalse는 true

    }
}
