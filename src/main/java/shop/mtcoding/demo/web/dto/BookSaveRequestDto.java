package shop.mtcoding.demo.web.dto;

import lombok.Setter;
import shop.mtcoding.demo.domain.Book;

@Setter
public class BookSaveRequestDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .author(author)
                .title(title)
                .build();
    }
}
