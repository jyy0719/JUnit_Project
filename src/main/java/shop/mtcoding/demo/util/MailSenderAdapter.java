package shop.mtcoding.demo.util;

import org.springframework.stereotype.Component;

// 얘가 메일보내기 진짜. 메일기능 완료되면 주석 풀고 사용
// 동일하게 MailSender를 부모로 받아 사용하고 있어서 @component 주석 걸지 않으면 오류남 (MailSenderStub.java)
//@Component
public class MailSenderAdapter implements MailSender {

    private Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean send() {
        return mail.sendMail();
    }
}
