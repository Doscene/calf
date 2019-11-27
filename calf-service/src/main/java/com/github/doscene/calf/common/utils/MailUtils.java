package com.github.doscene.calf.common.utils;

/**
 * <h1>com.github.doscene.calf.common</h1>
 *
 * @author lds <a href="github.com/doscene">github.com/doscene</a>
 */
public class MailUtils {
    public static final String from = "doscene@outlook.com";

    /*private static JavaMailSender javaMailSender = SpringUtils.getBean(JavaMailSender.class);


    public static void sendSimpleMessage(FrmMail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setText(mail.getText());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setFrom(from);
        javaMailSender.send(mailMessage);
    }*/
}
