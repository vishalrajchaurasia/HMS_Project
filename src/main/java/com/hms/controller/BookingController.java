package com.hms.controller;

import com.hms.service.PDFService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private PDFService pdfService;

    @PostMapping("/create-booking")
    public String createBookings( ){
        pdfService.generateBookingPdf("G:\\hms_bookings\\test.pdf");
        return "Booking Created successfully.";
    }
}
