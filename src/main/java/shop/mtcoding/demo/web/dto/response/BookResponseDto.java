package shop.mtcoding.demo.web.dto.response;

import lombok.Getter;
import shop.mtcoding.demo.domain.Book;

@Getter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;

    // toDto를 만드는 이유, 응답으로 @Entity 객체를 반환하면 영속화 되어있는 엔티티 객체의 값 변화가 일어날 수 있다.
    // 그래서 절대로, repository 외 service 단에서 영속화 된(@Entity) 엔티티 객체(ex- Book)를 리턴해서는 안된다.
    public BookResponseDto toDto(Book bookPS) {
        this.id = bookPS.getId();
        this.title = bookPS.getTitle();
        this.author = bookPS.getAuthor();
        return this;
    }
}
