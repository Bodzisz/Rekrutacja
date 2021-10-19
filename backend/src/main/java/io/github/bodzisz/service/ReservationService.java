package io.github.bodzisz.service;

import io.github.bodzisz.model.Cancellation;
import io.github.bodzisz.model.Reservation;
import io.github.bodzisz.model.Table;
import io.github.bodzisz.repository.CancellationRepository;
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

@Service
public class ReservationService {

    ReservationRepository reservationRepository;
    TableRepository tableRepository;
    CancellationRepository cancellationRepository;

    JavaMailSender mailSender;
    TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    String sentFrom;

    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository,
                              JavaMailSender mailSender, TemplateEngine templateEngine,
                              CancellationRepository cancellationRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.cancellationRepository = cancellationRepository;
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

        if(table.getMinNumberOfSeats() > reservation.getNumberOfSeats() ||
                table.getMaxNumberOfSeats() < reservation.getNumberOfSeats()) {
            throw new IllegalArgumentException("Your number of seats is not compatible with this table");
        }

        reservation = reservationRepository.save(reservation);

        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("reservation", reservation);


        this.sendMail(ctx, "email-reservation.html", reservation.getEmail(), "Reservation confirmed");

        return reservation;
    }

    public void requestReservationCancellation(int id) {
        Reservation reservation =  reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with given id does not exist"));

        if(!reservation.canBeCancelled()) {
            throw new IllegalStateException("Reservation has to be cancelled at least 2 hours before reservation time");
        }

        Cancellation cancellation = cancellationRepository.save(
                new Cancellation(reservation.getId(), verificationCodeFactory.generateCode()));

        final Context ctx = new Context(Locale.ENGLISH);
        ctx.setVariable("reservation", reservation);
        ctx.setVariable("verificationCode", cancellation.getCode());

        this.sendMail(ctx, "email-reservation-cancel.html", reservation.getEmail(), "Reservation cancellation");
    }

    public void deleteReservation(int id, String code) {
        Cancellation cancellation = cancellationRepository.getCancellationById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cancellation with given id does not exist"));
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation with given id does not exist"));

        if(!reservation.canBeCancelled()) {
            throw new IllegalStateException("Reservation can be cancelled at least 2 hours before reservation time");
        }

        if(cancellation.getCode().equals(code)) {
            final Context ctx = new Context(Locale.ENGLISH);
            ctx.setVariable("id", id);
            ctx.setVariable("name", reservation.getFullName());

            this.sendMail(ctx, "email-reservation-deleted", reservation.getEmail(), "Reservation delete");

            reservationRepository.deleteById(id);
        }
        else {
            throw new IllegalStateException("Wrong verification code");
        }
    }

    public void sendMail(Context ctx, String template, String sentTo, String subject) {
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message;
        try {
            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(subject);
            message.setFrom(sentFrom);
            message.setTo(sentTo);

            final String htmlContent = this.templateEngine.process(template, ctx);
            message.setText(htmlContent, true);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}