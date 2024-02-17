package com.henrique.APIAgendaMed.services;

import com.henrique.APIAgendaMed.dto.AppointmentDTO;
import com.henrique.APIAgendaMed.dto.AvailabilityDTO;
import com.henrique.APIAgendaMed.dto.DoctorDTO;
import com.henrique.APIAgendaMed.dto.UserDTO;
import com.henrique.APIAgendaMed.exceptions.BadRequestException;
import com.henrique.APIAgendaMed.exceptions.ConflictException;
import com.henrique.APIAgendaMed.exceptions.DateException;
import com.henrique.APIAgendaMed.exceptions.NotFoundException;
import com.henrique.APIAgendaMed.models.Appointment;
import com.henrique.APIAgendaMed.models.Doctor;
import com.henrique.APIAgendaMed.models.User;
import com.henrique.APIAgendaMed.models.enums.Status;
import com.henrique.APIAgendaMed.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {
    private static final int duration = 30;
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
            updateStatus(a);
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public AppointmentDTO findById(String id) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        updateStatus(appointment);
        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public List<AppointmentDTO> findByDoctorId(String id) {
        DoctorDTO doctor = doctorService.findById(id);

        List<Appointment> list = repository.findByDoctorId(doctor.id());
        List<AppointmentDTO> listDTO = new ArrayList<>();

        for (Appointment a : list) {
            updateStatus(a);
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public AvailabilityDTO getAvailability(String id, LocalDate date) {
        DoctorDTO doctor = doctorService.findById(id);
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) throw new DateException("Doctors don't work on Sundays");

        return new AvailabilityDTO(date, listAvailability(doctor, date));
    }

    public List<AppointmentDTO> findByPatientId(String id) {
        UserDTO user = userService.findById(id);

        List<Appointment> list = repository.findByPatientId(user.id());
        List<AppointmentDTO> listDTO = new ArrayList<>();

        for (Appointment a : list) {
            updateStatus(a);
            listDTO.add(new AppointmentDTO(a.getId(), a.getDate(), a.getDoctor().getId(), a.getPatient().getId(), a.getStatus()));
        }

        return listDTO;
    }

    public AppointmentDTO create(AppointmentDTO data) {
        DoctorDTO doctorDTO = doctorService.findById(data.doctorId());
        UserDTO userDTO = userService.findById(data.patientId());

        checkDateTime(data.date(), doctorDTO);
        checkIfPatientHasAnotherAppointment(userDTO.id(), data.date());

        Doctor doctor = new Doctor(doctorDTO.id(), doctorDTO.name(), doctorDTO.specialization(), doctorDTO.startTime(), doctorDTO.finishTime());
        User patient = new User(userDTO.id(), userDTO.name(), userDTO.createdAt());

        Appointment appointment = new Appointment(null, data.date(), doctor, patient, Status.BOOKED);
        appointment = repository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public AppointmentDTO updateDate(String id, LocalDateTime date) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));

        if (appointment.getStatus() != Status.BOOKED) throw new BadRequestException("It's not possible to update the appointment as it has already been canceled");

        Doctor doctor = appointment.getDoctor();

        checkDateTime(date, new DoctorDTO(doctor.getId(), doctor.getName(), doctor.getSpecialization(), doctor.getStartTime(), doctor.getFinishTime()));
        checkIfPatientHasAnotherAppointment(appointment.getPatient().getId(), date);

        appointment.setDate(date);
        repository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public AppointmentDTO cancel(String id) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new NotFoundException("Appointment not found"));
        appointment.setStatus(Status.CANCELLED);
        repository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getDate(), appointment.getDoctor().getId(), appointment.getPatient().getId(), appointment.getStatus());
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    private void checkDateTime(LocalDateTime date, DoctorDTO doctor) {
        if (date.isBefore(LocalDateTime.now())) throw new DateException("Date must be in the future");
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) throw new DateException("Doctors don't work on Sundays");

        LocalTime time = LocalTime.of(date.getHour(), date.getMinute());

        if (time.isBefore(doctor.startTime()) || time.isAfter(doctor.finishTime().minusMinutes(duration)))
            throw new DateException("The doctor's opening hours are from " + doctor.startTime() + " to " + doctor.finishTime().minusMinutes(duration));

        if (!listAvailability(doctor, LocalDate.from(date)).contains(time))
            throw new DateException("The doctor already has an appointment scheduled for this time");
    }

    private void checkIfPatientHasAnotherAppointment(String id, LocalDateTime dateTime) {
        if (repository.findByPatientIdAndDate(id, dateTime) != null) throw new ConflictException("An appointment already exists for the patient at the selected time.");
    }

    private List<LocalTime> listAvailability(DoctorDTO doctor, LocalDate date) {
        List<Appointment> appointmentList = repository.findByDoctorIdAndDateBetween(doctor.id(), LocalDateTime.of(date, LocalTime.MIN), LocalDateTime.of(date, LocalTime.MAX));
        List<LocalTime> appointmentTimeList = appointmentList.stream().map(x -> LocalTime.of(x.getDate().getHour(), x.getDate().getMinute())).toList();

        LocalTime time = doctor.startTime();
        List<LocalTime> timeList = new ArrayList<>();

        while (time.isBefore(doctor.finishTime())) {
            if (!appointmentTimeList.contains(time)) {
                timeList.add(time);
            }
            time = time.plusMinutes(duration);
        }

        return timeList;
    }

    private void updateStatus (Appointment appointment) {
        if (appointment.getStatus() == Status.BOOKED && appointment.getDate().isBefore(LocalDateTime.now())) {
            appointment.setStatus(Status.COMPLETED);
            repository.save(appointment);
        }
    }
}
