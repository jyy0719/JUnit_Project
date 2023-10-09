package shop.mtcoding.demo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.demo.service.BookService;
import shop.mtcoding.demo.web.dto.request.BookSaveRequestDto;
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
        List<BookResponseDto> books = bookService.getBookList();
        CommonResponseDto<?> commonResponseDto = CommonResponseDto.builder()
                .code(1)
                .msg("정상 처리되었습니다.")
                .body(books)
                .build();
        return new ResponseEntity<>(commonResponseDto, HttpStatus.OK); // 201 = insert 처리
    }

    // 책 한건
    public void getBookOne() {

    }

    // 책 삭제
    public void deleteBook() {

    }

    // 책 수정
    public void updateBook() {

    }
}
