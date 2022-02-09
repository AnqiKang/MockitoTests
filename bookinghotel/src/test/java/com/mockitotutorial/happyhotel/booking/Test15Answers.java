package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Test15Answers {

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
    void should_CalculateCorrectPrice() {
        try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {

            BookingRequest bookingRequest = new BookingRequest(
                    "1", LocalDate.of(2022, 01, 01),
                    LocalDate.of(2022, 01, 05), 2, false);
            double expected = 400.0 * 0.8;
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer(inv -> (double) inv.getArgument(0) * 0.8);

            double actual = bookingService.calculatePriceEuro(bookingRequest);

            assertEquals(expected, actual);

        }

    }


}