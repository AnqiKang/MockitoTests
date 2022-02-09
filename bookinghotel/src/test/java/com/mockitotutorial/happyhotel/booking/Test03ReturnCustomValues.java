package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test03ReturnCustomValues {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        int actual = bookingService.getAvailablePlaceCount();

        assertEquals(expected, actual);
    }

    @Test
    void should_CountAvailablePlaces_When_MultipleRoomAvailable() {
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(new ArrayList<>(Arrays.asList(
                        new Room("Room1", 2),
                        new Room("Room2", 4)
                )));
        int expected = 6;

        int actual = bookingService.getAvailablePlaceCount();

        assertEquals(expected, actual);

    }
}