package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

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
    void should_InvokePayment_When_Prepaid() {
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 05), 2, false);
        lenient().when(paymentServiceMock.pay(any(),anyDouble())).thenReturn("1");

        bookingService.makeBooking(bookingRequest);

    }





}