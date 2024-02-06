package com.henrique.APIAgendaMed.models;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import jakarta.persistence.*;

import javax.print.Doc;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne()
    @JoinColumn(name = "specialization")
    private Specialization specialization;

    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "finish_time")
    private LocalTime finishTime;

    public Doctor() {
    }

    public Doctor(String name, Specialization specialization, LocalTime startTime, LocalTime finishTime) {
        this.name = name;
        this.specialization = specialization;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Doctor(DoctorDTO dto) {
        id = dto.id();
        name = dto.name();
        specialization = dto.specialization();
        startTime = dto.startTime();
        finishTime = dto.finishTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Doctor doctor = (Doctor) o;

        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
