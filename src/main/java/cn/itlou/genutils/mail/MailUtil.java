package cn.itlou.genutils.mail;

import freemarker.template.Configuration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮件工具类
 *
 * @author yuanyl
 * @date 2020/6/3 17:24
 **/
@Slf4j
@Component
public class MailUtil {

    @Value("${spring.mail.sender}")
    private String MAIL_SENDER;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration configuration;

    private MailUtil(){}

    public void sendSimpleMail(MailDto mailDto) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件发送人
            simpleMailMessage.setFrom(MAIL_SENDER);
            //邮件接收人
            simpleMailMessage.setTo(mailDto.getRecipient());
            //邮件主题
            simpleMailMessage.setSubject(mailDto.getSubject());
            //邮件内容
            simpleMailMessage.setText(mailDto.getContent());
            javaMailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("邮件发送失败", e.getMessage());
        }
    }

}
