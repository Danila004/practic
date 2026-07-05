package ru.vsu.practic.dto;

public class CancelBookingRequest {
    private boolean confirmLateCancellation = false;

    public boolean isConfirmLateCancellation() {
        return confirmLateCancellation;
    }

    public void setConfirmLateCancellation(boolean confirmLateCancellation) {
        this.confirmLateCancellation = confirmLateCancellation;
    }
}
