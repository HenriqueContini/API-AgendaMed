package com.henrique.APIAgendaMed.models;

import java.time.LocalTime;

public class Doctor {
    private String id;
    private String name;
    private Specialization specialization;
    private LocalTime startTime;
    private LocalTime finishTime;
}
