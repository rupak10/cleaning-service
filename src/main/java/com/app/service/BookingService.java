package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.BookingDTO;
import com.app.dto.ListVo;
import com.app.model.Booking;
import com.app.model.User;
import com.app.repository.BookingRepository;
import com.app.repository.UserRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ListVo> getCleaningTypeList() {
        List<ListVo> list = new ArrayList<>();
        list.add(new ListVo("", "--select one--"));
        list.add(new ListVo("Normal Cleaning", "Normal Cleaning"));
        list.add(new ListVo("Deep Cleaning", "Deep Cleaning"));
        list.add(new ListVo("Kitchen Cleaning", "Kitchen Cleaning"));
        return list;
    }

    public List<ListVo> getServiceTypeList() {
        List<ListVo> list = new ArrayList<>();
        list.add(new ListVo("", "--select one--"));
        list.add(new ListVo("One Time", "One Time"));
        list.add(new ListVo("Two Weeks", "Two Weeks"));
        list.add(new ListVo("Monthly Cleaning", "Monthly Cleaning"));
        return list;
    }

    public AppResponse addBooking(BookingDTO bookingDTO, User loggedInUser) {
        try {
            if(!RequestValidator.isBookingAddRequestValid(bookingDTO)){
                return new AppResponse(false, "Invalid value provided !");
            }

            Optional<User> cleanerOptional = userRepository.findById(Long.valueOf(bookingDTO.getCleaner()));
            if(cleanerOptional.isEmpty()){
                return new AppResponse(false, "Cleaner not found !");
            }

            Booking booking = new Booking();
            booking.setCleaner(cleanerOptional.get());
            booking.setHour(Double.parseDouble(bookingDTO.getHour()));
            booking.setCleaningType(bookingDTO.getCleaningType());
            booking.setCleaningDate(CommonUtil.getDateFromString(bookingDTO.getCleaningDate()));
            booking.setTotalPrice(Double.parseDouble(bookingDTO.getTotalPrice()));

            booking.setBookedBy(loggedInUser.getUserId());
            booking.setBookedAt(new Timestamp(System.currentTimeMillis()));

            booking.setStatus("Pending"); //initially pending. Cleaner will accept later

            bookingRepository.save(booking);

            return new AppResponse(true, "Booking Added Successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
            return new AppResponse(false, "Failed to add booking !");
        }
    }


    public List<BookingDTO> getBookingList(User loggedInuser) {
        try {
            List<Booking> bookings = bookingRepository.findByBookedBy(loggedInuser.getUserId());
            return prepareBookingList(bookings);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<BookingDTO> prepareBookingList(List<Booking> bookings) {
        if(bookings == null || bookings.isEmpty()) {
            return new ArrayList<>();
        }
        List<BookingDTO> bookingList = new ArrayList<>();
        for(Booking booking : bookings) {
            BookingDTO bookingDTO = new BookingDTO();
            bookingDTO.setId(String.valueOf(booking.getId()));
            bookingDTO.setCleaner(CommonUtil.getFormattedName(booking.getCleaner()));
            bookingDTO.setHour(String.valueOf(booking.getHour()));
            bookingDTO.setTotalPrice(String.valueOf(booking.getTotalPrice()));
            bookingDTO.setCleaningType(booking.getCleaningType());
            bookingDTO.setCleaningDate(String.valueOf(booking.getCleaningDate()));
            bookingList.add(bookingDTO);
        }
        return bookingList;
    }
}
