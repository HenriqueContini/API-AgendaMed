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

import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    public AppointmentDTO findById(Long id) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public AppointmentDTO create(AppointmentDTO data) {
        checkDate(data.date());
        DoctorDTO doctorDTO = doctorService.findById(data.doctorId());
        UserDTO userDTO = userService.findById(data.patientId());

        Appointment appointment = new Appointment(null, data.date(), new Doctor(doctorDTO), new User(userDTO), Status.BOOKED);
        appointment = repository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void checkDate(Date date) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        if (dateTime.isBefore(LocalDateTime.now())) throw new DateException("Date must be in the future!");
    }
}
