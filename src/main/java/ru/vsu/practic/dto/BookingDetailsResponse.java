package ru.vsu.practic.dto;

import java.time.LocalDateTime;

public class BookingDetailsResponse {
    private Long bookingId;
    private String status;
    private LocalDateTime startDatetime;
    private String zoneName;
    private String instructorName;

    public BookingDetailsResponse(Long bookingId, String status, LocalDateTime startDatetime, String zoneName, String instructorName, String equipmentOption, String cancellationReason) {
        this.bookingId = bookingId;
        this.status = status;
        this.startDatetime = startDatetime;
        this.zoneName = zoneName;
        this.instructorName = instructorName;
        this.equipmentOption = equipmentOption;
        this.cancellationReason = cancellationReason;
    }

    public BookingDetailsResponse() {
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setEquipmentOption(String equipmentOption) {
        this.equipmentOption = equipmentOption;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    private String equipmentOption;

    public Long getBookingId() {
        return bookingId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public String getEquipmentOption() {
        return equipmentOption;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    private String cancellationReason;
}
