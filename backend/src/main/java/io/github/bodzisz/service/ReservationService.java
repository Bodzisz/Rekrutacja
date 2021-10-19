package io.github.bodzisz.service;

import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.ReservationRepository;
import io.github.bodzisz.repository.TableRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    TableRepository tableRepository;
    JavaMailSender mailSender;
    TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    String sentFrom;

    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository,
                              JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public List<Reservation> getAllReservations(LocalDateTime date) {
        if(date == null) {
            return reservationRepository.findAll();
        }
        return reservationRepository.findReservationByDateBetween(date.toLocalDate().atStartOfDay(),
                date.plusDays(1).toLocalDate().atStartOfDay());
    }

    public Reservation saveReservation(Reservation reservation) {
        Table table = tableRepository.findById(reservation.getSeatNumber())
                .orElseThrow(() -> new IllegalArgumentException("Table with given seat number does not exist"));

        if(!table.isAvailable(reservation.getDate(), reservation.getDuration())) {
            throw new IllegalArgumentException("Table is not available at the selected time");
        }

        reservation = reservationRepository.save(reservation);

        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("reservation", reservation);


        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message; // true = multipart
        try {
            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("Restaurant table reservation");
            message.setFrom(sentFrom);
            message.setTo(reservation.getEmail());

            final String htmlContent = this.templateEngine.process("email-reservation.html", ctx);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return reservation;
    }
}
