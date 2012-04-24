package com.trc.service.email;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.trc.exception.EmailException;

@Service
public class VelocityEmailService {
  protected static final String templatePath = "templates/email/";
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private VelocityEngine velocityEngine;

  public void send(String template, final SimpleMailMessage message, final Map<Object, Object> model) throws EmailException {
    final String vmTemplate = templatePath + template + ".vm";
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      public void prepare(MimeMessage mimeMessage) throws Exception {
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setTo(message.getTo());
        messageHelper.setFrom(message.getFrom());
        messageHelper.setSubject(message.getSubject());
        String body = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vmTemplate, model);
        messageHelper.setText(body, true);
      }
    };
    mailSender.send(preparator);
    preparator = null;
  }

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }

}