package shop.mtcoding.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import shop.mtcoding.demo.domain.Book;
import shop.mtcoding.demo.domain.BookRepository;
import shop.mtcoding.demo.web.dto.BookResponseDto;
import shop.mtcoding.demo.web.dto.BookSaveRequestDto;

@Service
@NoArgsConstructor
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // 책등록
    public BookResponseDto registerBook(BookSaveRequestDto requestDto) {
        // 매개변수로는 BookSaveRequestDto를 받아서 레포지토리 인자로 Book을 리턴하는 toEntity() 매서드를 사용하고,
        // save 이후 받는 Book객체는 다시 service단의 registerBook을 호출한 클래스로 리턴하는데
        // 영속화된 Book객체를 리턴할 수 없어서, Book의 응답을 BookResponseDto가 받아서 데이터만 리턴한다.
        Book bookPS = bookRepository.save(requestDto.toEntity());

        // BookResponseDto의 toDto() 매서드를 static으로 만들어 사용해도 되고, 아래 방법으로 사용해도 된다.
        return new BookResponseDto().toDto(bookPS);
    }
}
