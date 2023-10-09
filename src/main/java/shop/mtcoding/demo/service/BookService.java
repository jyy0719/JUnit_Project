package shop.mtcoding.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.demo.domain.Book;
import shop.mtcoding.demo.domain.BookRepository;
import shop.mtcoding.demo.util.MailSender;
import shop.mtcoding.demo.web.dto.request.BookSaveRequestDto;
import shop.mtcoding.demo.web.dto.response.BookResponseDto;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

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
        if (bookPS != null) {
            if (!mailSender.send()) {
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        // BookResponseDto의 toDto() 매서드를 static으로 만들어 사용해도 되고, 아래 방법으로 사용해도 된다.
        return new BookResponseDto().toDto(bookPS);
    }

    // 책목록
    public List<BookResponseDto> getBookList() {
        return bookRepository.findAll().stream()
                // 이거 test코드로 틀린 것 검증하여 아래로 바꿈 .map(new BookResponseDto()::toDto) // (==동일한 코드)
                // .map((bookPS)->new BookResponseDto().toDto(bookPS))
                .map((book) -> new BookResponseDto().toDto(book))
                .collect(Collectors.toList());

    }

    // 책한건보기
    public BookResponseDto getBook(long id) {
        Optional<Book> op = bookRepository.findById(1L);
        return new BookResponseDto().toDto(op.orElseGet(Book::new));
    }

    // 책삭제하기
    @Transactional(rollbackOn = RuntimeException.class)
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    // 책수정하기
    public BookResponseDto updateBook(long id, BookSaveRequestDto requestDto) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book bookOP = optionalBook.get();
            return bookOP.update(requestDto.getTitle(), requestDto.getAuthor());
        } else {
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다.");
        }
    }

}
