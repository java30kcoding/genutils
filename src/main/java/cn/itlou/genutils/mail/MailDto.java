package cn.itlou.genutils.mail;

import lombok.Data;

/**
 * 邮件DTO
 *
 * @author yuanyl
 * @date 2020/6/3 17:24
 **/
@Data
public class MailDto {

    //邮件接收人
    private String recipient;
    //邮件主题
    private String subject;
    //邮件内容
    private String content;

}
