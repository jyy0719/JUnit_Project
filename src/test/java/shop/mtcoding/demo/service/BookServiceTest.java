package shop.mtcoding.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import org.hibernate.mapping.Array;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import shop.mtcoding.demo.domain.BookRepository;
import shop.mtcoding.demo.domain.Book;
import shop.mtcoding.demo.util.MailSender;
import shop.mtcoding.demo.web.dto.request.BookSaveRequestDto;
import shop.mtcoding.demo.web.dto.response.BookListResponseDto;
import shop.mtcoding.demo.web.dto.response.BookResponseDto;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_test() {
        // given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setAuthor("이해인");
        dto.setTitle("나니아연대기러기");

        // stub(가설)
        // 아무데이터나 넣었다고 치고, 리턴은 위에 셋팅한 dto를 toEntity로 변환된 데이터를 해라
        // BookService 내에 registerBook 매소드에서 사용될 bookRepository.save() 매서드를 가설로 정의
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookResponseDto bookResponseDto = bookService.registerBook(dto);

        // then
        assertThat(dto.getTitle()).isEqualTo(bookResponseDto.getTitle());
        assertThat(dto.getAuthor()).isEqualTo(bookResponseDto.getAuthor());
    }

    @Test
    public void 책목록보기_test() {
        // given(파라미터로 들어올 데이터, 이 테스트에서는 없음)

        // stub(가설)
        List<Book> books = new ArrayList();
        books.add(new Book(1L, "Spring 기본", "메타코딩"));
        books.add(new Book(1L, "JPA 기본", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);

        // when
        BookListResponseDto bookListResponseDto = bookService.getBookList();

        // print

        // then
        assertThat(bookListResponseDto.getItems().get(0).getTitle()).isEqualTo("Spring 기본");
        assertThat(bookListResponseDto.getItems().get(0).getAuthor()).isEqualTo("메타코딩");
        assertThat(bookListResponseDto.getItems().get(1).getTitle()).isEqualTo("JPA 기본");
        assertThat(bookListResponseDto.getItems().get(1).getAuthor()).isEqualTo("겟인데어");

    }

    @Test
    public void 책한건보기_test() {
        // given

        // stub
        Book book = new Book(1L, "title의 타이틀", "파스쿠찌");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // when
        BookResponseDto dto = bookService.getBook(1L);

        // then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getTitle()).isEqualTo("title의 타이틀");
        assertThat(dto.getAuthor()).isEqualTo("파스쿠찌");

    }

    @Test
    public void 책수정하기_test() {
        // given
        BookSaveRequestDto dto = new BookSaveRequestDto();
        dto.setAuthor("title의 타이틀2");
        dto.setAuthor("팻스쿠치");
        // stub
        Book book = new Book(1L, "title의 타이틀", "파스쿠찌");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // when
        BookResponseDto dto2 = bookService.updateBook(1L, dto);

        // then
        assertThat(dto2.getId()).isEqualTo(1L);
        assertThat(dto2.getTitle()).isEqualTo(dto.getTitle());
        assertThat(dto2.getAuthor()).isEqualTo(dto.getAuthor());
    }

}
