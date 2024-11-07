package com.medihelp.presupuesto.service;

import com.medihelp.presupuesto.domain.Presupuesto;
import com.medihelp.presupuesto.domain.User;
import com.medihelp.presupuesto.service.dto.PresupuestoDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import tech.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails asynchronously.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    public MailService(
        JHipsterProperties jHipsterProperties,
        JavaMailSender javaMailSender,
        MessageSource messageSource,
        SpringTemplateEngine templateEngine
    ) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        this.sendEmailSync(to, subject, content, isMultipart, isHtml);
    }

    private void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        LOG.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            LOG.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            LOG.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    private void sendEmailSyncWithAttachment(String to, String subject, String content, boolean isMultipart, boolean isHtml, File pdfFile) {
        LOG.debug(
            "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart,
            isHtml,
            to,
            subject,
            content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            FileSystemResource file = new FileSystemResource(pdfFile);
            message.addAttachment(Optional.ofNullable(file.getFilename()).orElse("SolicitudPresupuesto_" + LocalDateTime.now()), file);
            javaMailSender.send(mimeMessage);
            LOG.debug("Sent email to User '{}'", to);
        } catch (MailException | MessagingException e) {
            LOG.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        this.sendEmailFromTemplateSync(user, templateName, titleKey);
    }

    private void sendEmailFromTemplateSync(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            LOG.debug("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmailSync(user.getEmail(), subject, content, false, true);
    }

    private void sendChangeStatusNotificationSync(PresupuestoDTO presupuesto, String templateName, String titleKey) {
        if (presupuesto.getCorreoResponsable() == null) {
            LOG.debug("Email doesn't exist for user '{}'", presupuesto.getNombreResponsable());
            return;
        }
        Locale locale = Locale.forLanguageTag("es");
        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("consecutivo", presupuesto.getId());
        context.setVariable("estado", presupuesto.getEstado().toString());
        context.setVariable("name", presupuesto.getNombreResponsable());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmailSync(presupuesto.getCorreoResponsable(), subject, content, false, true);
    }

    private void sendReturnStatusNotificationSync(PresupuestoDTO presupuesto, String templateName, String titleKey) {
        if (presupuesto.getCorreoResponsable() == null) {
            LOG.debug("Email doesn't exist for user '{}'", presupuesto.getNombreResponsable());
            return;
        }
        Locale locale = Locale.forLanguageTag("es");
        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("consecutivo", presupuesto.getId());
        context.setVariable("usuario", presupuesto.getNombreResponsable());
        context.setVariable("observacion", presupuesto.getObservaciones());
        context.setVariable("linkFormulario", jHipsterProperties.getMail().getBaseUrl() + "/presupuesto-form/" + presupuesto.getId());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmailSync(presupuesto.getCorreoResponsable(), subject, content, false, true);
    }

    private void sendRequestCreatedNotificationSync(PresupuestoDTO presupuesto, String templateName, String titleKey) {
        if (presupuesto.getCorreoResponsable() == null) {
            LOG.debug("Email doesn't exist for user '{}'", presupuesto.getNombreResponsable());
            return;
        }
        Locale locale = Locale.forLanguageTag("es");
        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("consecutivo", presupuesto.getId());
        context.setVariable("name", presupuesto.getNombreResponsable());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmailSync(presupuesto.getCorreoResponsable(), subject, content, false, true);
    }

    private void sendRequestDoneNotificationSync(PresupuestoDTO presupuesto, String templateName, String titleKey, File attachment) {
        if (presupuesto.getCorreoResponsable() == null) {
            LOG.debug("Email doesn't exist for user '{}'", presupuesto.getNombreResponsable());
            return;
        }
        Locale locale = Locale.forLanguageTag("es");
        Context context = new Context(locale);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        context.setVariable("consecutivo", presupuesto.getId());
        context.setVariable("name", presupuesto.getNombreResponsable());
        context.setVariable("presupuesto-link", jHipsterProperties.getMail().getBaseUrl() + "/presupuesto-form/" + presupuesto.getId());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        this.sendEmailSyncWithAttachment(presupuesto.getCorreoResponsable(), subject, content, true, true, attachment);
    }

    @Async
    public void sendActivationEmail(User user) {
        LOG.debug("Sending activation email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        LOG.debug("Sending creation email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        LOG.debug("Sending password reset email to '{}'", user.getEmail());
        this.sendEmailFromTemplateSync(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendChangeStatusNotification(PresupuestoDTO presupuesto) {
        LOG.debug("Sending status change notification email to '{}'", presupuesto.getCorreoResponsable());
        this.sendChangeStatusNotificationSync(presupuesto, "mail/statusChangedEmail", "email.notification.title");
    }

    @Async
    public void sendRequestCreatedNotification(PresupuestoDTO presupuesto) {
        LOG.debug("Sending request created notification email to '{}'", presupuesto.getCorreoResponsable());
        this.sendRequestCreatedNotificationSync(presupuesto, "mail/requestCreatedEmail", "email.creation.title");
    }

    @Async
    public void sendRequestDoneEmail(PresupuestoDTO presupuesto, File attachment) {
        LOG.debug("Sending request done notificacion email to '{}'", presupuesto.getCorreoResponsable());
        this.sendRequestDoneNotificationSync(presupuesto, "mail/requestDoneEmail", "email.finalized.title", attachment);
    }

    @Async
    public void sendReturnRequestEmail(PresupuestoDTO presupuesto) {
        LOG.debug("Sending return request notification email to '{}'", presupuesto.getCorreoResponsable());
        this.sendReturnStatusNotificationSync(presupuesto, "mail/returnNotificationEmail", "email.return.title");
    }
}
