package shop.mtcoding.demo.web.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookListResponseDto {
    private List<BookResponseDto> items;

    @Builder
    public BookListResponseDto(List<BookResponseDto> items) {
        this.items = items;
    }

}
