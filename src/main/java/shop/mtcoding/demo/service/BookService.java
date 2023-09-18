package shop.mtcoding.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
    // @Transactional은 모든 예외에 대해 롤백하지 않는다
    // RuntimeException과 Error에 대해서만 롤백하고, Exception에 대해서는 롤백하지 않는다.즉 unchecked
    // exception이 발생하면 롤백하고, checked exception이 발생하면 커밋
    @Transactional(rollbackOn = RuntimeException.class)
    public BookResponseDto registerBook(BookSaveRequestDto requestDto) {
        // 매개변수로는 BookSaveRequestDto를 받아서 레포지토리 인자로 Book을 리턴하는 toEntity() 매서드를 사용하고,
        // save 이후 받는 Book객체는 다시 service단의 registerBook을 호출한 클래스로 리턴하는데
        // 영속화된 Book객체를 리턴할 수 없어서, Book의 응답을 BookResponseDto가 받아서 데이터만 리턴한다.
        Book bookPS = bookRepository.save(requestDto.toEntity());

        // BookResponseDto의 toDto() 매서드를 static으로 만들어 사용해도 되고, 아래 방법으로 사용해도 된다.
        return new BookResponseDto().toDto(bookPS);
    }

    // 책목록
    public List<BookResponseDto> getBookList() {
        return bookRepository.findAll().stream()
                .map(new BookResponseDto()::toDto) // (==동일한 코드) .map((bookPS)->new BookResponseDto().toDto(bookPS))
                .collect(Collectors.toList());
    }

}
