package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.AppointmentDTO;
import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.exceptions.DateException;
import com.henrique.APIAgendaMed.exceptions.NotFoundException;
import com.henrique.APIAgendaMed.models.Appointment;
import com.henrique.APIAgendaMed.models.Doctor;
import com.henrique.APIAgendaMed.models.User;
import com.henrique.APIAgendaMed.models.enums.Status;
import com.henrique.APIAgendaMed.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository repository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;

    public List<AppointmentDTO> findAll() {
        List<Appointment> list = repository.findAll();
        List<AppointmentDTO> listDTO = new ArrayList<>();

        for (Appointment a : list) {
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public AppointmentDTO findById(String id) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public List<AppointmentDTO> findByDoctorId(String id) {
        DoctorDTO doctor = doctorService.findById(id);

        List<Appointment> list = repository.findByDoctorId(doctor.id());
        List<AppointmentDTO> listDTO = new ArrayList<>();

        for (Appointment a : list) {
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public List<AppointmentDTO> findByPatientId(String id) {
        UserDTO user = userService.findById(id);

        List<Appointment> list = repository.findByPatientId(user.id());
        List<AppointmentDTO> listDTO = new ArrayList<>();

        for (Appointment a : list) {
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public AppointmentDTO create(AppointmentDTO data) {
        DoctorDTO doctorDTO = doctorService.findById(data.doctorId());
        UserDTO userDTO = userService.findById(data.patientId());

        Doctor doctor = new Doctor(doctorDTO.id(), doctorDTO.name(), doctorDTO.specialization(), doctorDTO.startTime(), doctorDTO.finishTime());
        User patient = new User(userDTO.id(), userDTO.name(), userDTO.createdAt());

        checkDateTime(data.date(), doctor);

        Appointment appointment = new Appointment(null, data.date(), doctor, patient, Status.BOOKED);
        appointment = repository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    private void checkDateTime(LocalDateTime date, Doctor doctor) {
        if (date.isBefore(LocalDateTime.now())) throw new DateException("Date must be in the future");

        LocalTime time = LocalTime.of(date.getHour(), date.getMinute());

        if (time.isBefore(doctor.getStartTime()) || time.isAfter(doctor.getFinishTime()))
            throw new DateException("The doctor's opening hours are from " + doctor.getStartTime() + " to " + doctor.getFinishTime());
    }
}
