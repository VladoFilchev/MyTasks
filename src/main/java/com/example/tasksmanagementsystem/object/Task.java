package com.example.tasksmanagementsystem.object;

import com.example.tasksmanagementsystem.enums.TaskStatus;

import java.time.LocalDate;

public class Task {
    private int ID;
    private TaskStatus status;
    private String taskName;
    private String overview;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task(int ID, String taskName,TaskStatus status, String overview, LocalDate startDate, LocalDate endDate) {
        this.ID=ID;
        this.status = status;
        this.taskName = taskName;
        this.overview = overview;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
