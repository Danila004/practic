package ru.vsu.practic.dto;

import java.time.LocalDateTime;

public class SlotResponse {
    private Long slotId;
    private LocalDateTime startDatetime;
    private String zoneName;
    private String instructorName;
    private Integer availableSpots;

    public SlotResponse(Long slotId, LocalDateTime startDatetime, String zoneName, String instructorName, Integer availableSpots) {
        this.slotId = slotId;
        this.startDatetime = startDatetime;
        this.zoneName = zoneName;
        this.instructorName = instructorName;
        this.availableSpots = availableSpots;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
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

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
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

    public Integer getAvailableSpots() {
        return availableSpots;
    }
}
