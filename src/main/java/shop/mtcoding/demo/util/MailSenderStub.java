package shop.mtcoding.demo.util;

import org.springframework.stereotype.Component;

// 가짜. 메일보내기 기능 만들기 전이라면 ture를 반환하는 가짜 기능을 만들어서 사용한다.
// 진짜는 MailSenderAdapter 에서 구현하고 붙인다 .
@Component
public class MailSenderStub implements MailSender {
    @Override
    public boolean send() {
        // TODO Auto-generated method stub
        return true;
    }
}
