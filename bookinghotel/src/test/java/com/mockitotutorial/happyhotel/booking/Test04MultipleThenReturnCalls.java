package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test04MultipleThenReturnCalls {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

    }

    @Test
    void should_CountAvailablePlaces_When_CalledMultipleTimes() {
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)))
                .thenReturn(Collections.emptyList());
        int expectedFirstCall = 2;
        int expectedSecondCall = 0;


        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();

        assertAll(
                () -> assertEquals(expectedFirstCall, actualFirst),
                () -> assertEquals(expectedSecondCall, actualSecond)
        );

    }


}