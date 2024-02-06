package com.henrique.APIAgendaMed.models;

import com.henrique.APIAgendaMed.models.enums.Status;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;

    @ManyToOne()
    @JoinColumn(name = "doctor")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient")
    private User patient;

    private Status status;

    public Appointment() {
    }

    public Appointment(Long id, Date date, Doctor doctor, User patient, Status status) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appointment that = (Appointment) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
