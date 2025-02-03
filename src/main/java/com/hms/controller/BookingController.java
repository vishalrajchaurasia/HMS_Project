package com.hms.controller;

import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.entity.Room;
import com.hms.repository.BookingsRepository;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomRepository;
import com.hms.service.PDFService;
import com.hms.service.TwilioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private PDFService pdfService;
    private PropertyRepository propertyRepository;
    private BookingsRepository bookingsRepository;
    private RoomRepository roomRepository;
    private TwilioService twilioService;

    public BookingController(PDFService pdfService, PropertyRepository propertyRepository, BookingsRepository bookingsRepository, RoomRepository roomRepository, TwilioService twilioService) {
        this.pdfService = pdfService;
        this.propertyRepository = propertyRepository;
        this.bookingsRepository = bookingsRepository;
        this.roomRepository = roomRepository;
        this.twilioService = twilioService;
    }

    @PostMapping("/create-booking")
    public ResponseEntity<?> createBookings(
           @RequestParam long propertyId,
           @RequestParam String type,
           @RequestBody Bookings booking
    ){
        Property property = propertyRepository.findById(propertyId).get();
        List<Room> rooms = roomRepository.findByTypeAndProperty(booking.getFromDate(), booking.getToDate(),type,property);
        for(Room room : rooms){
            if(room.getCount()==0){
                return new ResponseEntity<>("No rooms for:"+room.getDate(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        for(Room room : rooms){
            double totalPrice = room.getPerNightPrice() * (double)( rooms.size()-1);
        }


//        //Room room = roomRepository.findByTypeAndProperty(type, property);
//        if(room.getCount()==0){
//            return "No available rooms ";
//        }
//
        Bookings savedBooking = bookingsRepository.save(booking);
        if(savedBooking!=null){
        for(Room room:rooms){
            if(savedBooking!=null) {
                room.setCount(room.getCount() - 1);
                roomRepository.save(room);
            }
            }
        }

        pdfService.generateBookingPdf("G:\\hms_bookings\\confirmation-order"+savedBooking.getId()+".pdf",property);
        //twilioService.sendSms("+919334661406","+15407798570","Booking confirmed. Ypur booking id is: "+booking.getId());
        return new ResponseEntity<>(rooms,HttpStatus.OK);
    }
}
