package shop.mtcoding.demo.web.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.demo.domain.Book;

@Getter
@Setter
public class BookSaveRequestDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
    @NotBlank
    @Size(min = 1, max = 20)
    private String author;

    public Book toEntity() {
        return Book.builder()
                .author(author)
                .title(title)
                .build();
    }
}
