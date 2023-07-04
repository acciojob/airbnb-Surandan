package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HotelManagementRepository {
     Map<String, Hotel> hotelMap = new HashMap<>();  // hotelName : Hotel
     Map<Integer, User> userMap = new HashMap<>();    // aadharNo :  User
     Map<String, Booking> bookingMap = new HashMap<>(); // bookingId : Booking
    Map<String, Integer> noOfBookings = new HashMap<>(); // aadharNo : NoOfBookings

    public String addHotel(Hotel hotel) {
        if(hotelMap.containsKey(hotel.getHotelName())) return "FAILURE";
        hotelMap.put(hotel.getHotelName(),hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        userMap.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        int currFac =0;
        String currHotel = "zzzzz";

        for(String str : hotelMap.keySet()) {
            if(hotelMap.get(str).getFacilities().size() >= currFac) {
                if(hotelMap.get(str).getFacilities().size() == currFac) {
                    if(str.compareTo(currHotel) < 0) {
                        currFac = hotelMap.get(str).getFacilities().size();
                        currHotel  = str;
                    }
                }
                else {
                    currFac = hotelMap.get(str).getFacilities().size();
                    currHotel = str;
                }

            }
        }
        if(currFac == 0) return "";
        return currHotel;
    }

    public int getBookings(Integer aadharCard) {
        return noOfBookings.getOrDefault(aadharCard,0);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        List<Facility> currFac = hotelMap.get(hotelName).getFacilities();
        for (Facility facility : newFacilities) {
            if(!currFac.contains(facility)) currFac.add(facility);
        }
        hotelMap.get(hotelName).setFacilities(currFac);
        return  hotelMap.get(hotelName);
    }

    public int bookARoom(String bookingId, Booking booking) {
        bookingMap.put(bookingId,booking);
        int currAmount = 0;
        Hotel hotel = hotelMap.get(booking.getHotelName());
        if(booking.getNoOfRooms() > hotel.getAvailableRooms()) return -1;
        else {
            currAmount = booking.getNoOfRooms() * hotel.getPricePerNight();
            hotel.setAvailableRooms(hotel.getAvailableRooms()-booking.getNoOfRooms());
        }
        return currAmount;
    }
}
