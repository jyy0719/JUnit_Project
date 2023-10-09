package shop.mtcoding.demo.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.demo.service.BookService;
import shop.mtcoding.demo.web.dto.request.BookSaveRequestDto;
import shop.mtcoding.demo.web.dto.response.BookListResponseDto;
import shop.mtcoding.demo.web.dto.response.BookResponseDto;
import shop.mtcoding.demo.web.dto.response.CommonResponseDto;

@RestController
@RequiredArgsConstructor
public class BookApiController {
    private final BookService bookService;

    // 책 등록
    // key=value&key=value 가 기본
    // {"key":value,"key":value} .. json 형식으로 받기 위해서는 @RequestBody 어노테이션 걸어줌
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                System.out.println("getField >> " + fieldError.getField());
                System.out.println("getDefaultMessage >> " + fieldError.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookResponseDto bookResponseDto = bookService.registerBook(requestDto);
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .body(bookResponseDto)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.CREATED); // 201 = insert 처리
    }

    // 책 목록
    @GetMapping("/api/v1/books")
    public ResponseEntity<?> getBookList() {
        // 리턴을 list로 하는 것을 권장하지 않음.
        // 책 한건 가져올 때는 {} 오브젝트인데, 다 건 가져올 때 list이면 화면단에서 파싱하기가 번거로움
        BookListResponseDto items = bookService.getBookList();
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .body(items)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK); // 201 = insert 처리
    }

    // 책 한건
    // http://localhost:8080/api/v1/book=1
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResponseDto bookResponseDto = bookService.getBook(id);
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .body(bookResponseDto)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }

    // 책 삭제
    // http://localhost:8080/api/v1/book/1
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }

    // 책 수정
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveRequestDto requestDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookResponseDto bookResponseDto = bookService.updateBook(id, requestDto);
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .body(bookResponseDto)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK);
    }
}
