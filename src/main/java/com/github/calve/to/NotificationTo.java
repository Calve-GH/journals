package com.github.calve.to;

import com.github.calve.util.Journals;

import java.io.Serializable;
import java.util.Map;

import static com.github.calve.util.Journals.*;

public class NotificationTo implements Serializable {
    private int requests;
    private int complaints;
    private int generics;
    private int info;
    private int applications;

    public NotificationTo() {
    }

    public NotificationTo(Map<Journals, Integer> map) {
        this.requests = map.getOrDefault(REQUESTS, 0);
        this.complaints = map.getOrDefault(COMPLAINTS, 0);
        this.generics = map.getOrDefault(GENERICS, 0);
        this.info = map.getOrDefault(INFO, 0);
        this.applications = map.getOrDefault(APPLICATIONS, 0);
    }

    public NotificationTo(int requests, int complaints, int generics, int info, int applications) {
        this.requests = requests;
        this.complaints = complaints;
        this.generics = generics;
        this.info = info;
        this.applications = applications;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public int getComplaints() {
        return complaints;
    }

    public void setComplaints(int complaints) {
        this.complaints = complaints;
    }

    public int getGenerics() {
        return generics;
    }

    public void setGenerics(int generics) {
        this.generics = generics;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public int getApplications() {
        return applications;
    }

    public void setApplications(int applications) {
        this.applications = applications;
    }
}
