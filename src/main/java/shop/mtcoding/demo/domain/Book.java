package shop.mtcoding.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.demo.web.dto.response.BookResponseDto;

@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 20, nullable = false)
    private String author;

    @Builder
    public Book(long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // junit service layer 테스트 시 업데이트가 되었는 지 확인하기 위해 리턴타입 넣음, 테스트가 아니면 void 처리
    public BookResponseDto update(String title, String author) {
        this.title = title;
        this.author = author;

        return new BookResponseDto().toDto(this);
    }

}
