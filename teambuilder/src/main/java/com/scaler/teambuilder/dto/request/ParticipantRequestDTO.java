package com.scaler.teambuilder.dto.request;

import lombok.Data;

@Data
public class ParticipantRequestDTO {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String domain;
    private double yoe;
}
