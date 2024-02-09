package com.henrique.APIAgendaMed.models;

import com.henrique.APIAgendaMed.dto.DoctorDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalTime;
import java.util.Objects;

@Document(collection = "doctor")
public class Doctor {
    @Id
    private String id;
    private String name;

    @DocumentReference
    private Specialization specialization;

    @Field("start_time")
    private LocalTime startTime;
    @Field("finish_time")
    private LocalTime finishTime;

    public Doctor() {
    }

    public Doctor(String id, String name, Specialization specialization, LocalTime startTime, LocalTime finishTime) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public Doctor(DoctorDTO doctorDTO) {
        this.id = doctorDTO.id();
        this.name = doctorDTO.name();
        this.specialization = doctorDTO.specialization();
        this.startTime = doctorDTO.startTime();
        this.finishTime = doctorDTO.finishTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
