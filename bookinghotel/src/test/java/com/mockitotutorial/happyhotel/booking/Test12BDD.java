package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test12BDD {

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
    void should_CountAvailablePlaces_When_OneRoomAvailable() {
        given(this.roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room 1", 2)));
        int expected = 2;

        int actual = bookingService.getAvailablePlaceCount();

        assertEquals(expected, actual);
    }

    @Test
    void should_InvokePayment_When_Prepaid() {
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2022, 01, 01),
                LocalDate.of(2022, 01, 05), 2, true);

        bookingService.makeBooking(bookingRequest);

        then(paymentServiceMock).should(times(1)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);
    }





}