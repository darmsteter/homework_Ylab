package com.coworking_service.model;

import java.time.LocalDate;

public class Booking {
    private String userLogin;
    private int workplaceID;
    private LocalDate bookingDate;
    private String[] slots;

    public Booking(String userLogin, int workplaceID, LocalDate bookingDate, String[] slots) {
        this.userLogin = userLogin;
        this.workplaceID = workplaceID;
        this.bookingDate = bookingDate;
        this.slots = slots;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public int getWorkplaceID() {
        return workplaceID;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public String[] getSlots() {
        return slots;
    }
}
