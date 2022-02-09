package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test11Annotations {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Spy
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleArgumentCaptor;


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