package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test10TestArgumentCaptors {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleArgumentCaptor;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);
        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
        this.doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);
    }

    @Test
    void should_PayCorrect_When_InputOK() {
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 05), 2, true);

        bookingService.makeBooking(bookingRequest);

        verify(paymentServiceMock).pay(eq(bookingRequest), doubleArgumentCaptor.capture());
        double capturedArgument = doubleArgumentCaptor.getValue();

        System.out.println(capturedArgument);
        assertEquals(400.0, capturedArgument);
    }

    @Test
    void should_PayCorrect_When_MultipleCalls() {
        BookingRequest bookingRequest1 = new BookingRequest(
                "1", LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 05), 2, true);
        BookingRequest bookingRequest2 = new BookingRequest(
                "1", LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 02), 2, true);
        List<Double> expectedValues = Arrays.asList(400.0, 100.0);

        bookingService.makeBooking(bookingRequest1);
        bookingService.makeBooking(bookingRequest2);

        verify(paymentServiceMock, times(2)).pay(any(), doubleArgumentCaptor.capture());
        List<Double> capturedArguments = doubleArgumentCaptor.getAllValues();

        assertEquals(expectedValues, capturedArguments);
    }


}