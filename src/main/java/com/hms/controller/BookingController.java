package com.hms.controller;

import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.repository.BookingsRepository;
import com.hms.repository.PropertyRepository;
import com.hms.service.PDFService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private PDFService pdfService;
    private PropertyRepository propertyRepository;
    private BookingsRepository bookingsRepository;

    public BookingController(PDFService pdfService, PropertyRepository propertyRepository, BookingsRepository bookingsRepository) {
        this.pdfService = pdfService;
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
    }

    @PostMapping("/create-booking")
    public String createBookings(
           @RequestParam long propertyId,
           @RequestBody Bookings booking
    ){
        Property property = propertyRepository.findById(propertyId).get();
        Bookings savedBooking = bookingsRepository.save(booking);

        pdfService.generateBookingPdf("G:\\hms_bookings\\confirmation-order"+savedBooking.getId()+".pdf",property);
        return "Booking Created successfully.";
    }
}
