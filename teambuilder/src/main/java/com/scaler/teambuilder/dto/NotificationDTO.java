package com.scaler.teambuilder.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationDTO {

    private String to;
    private String from;
    private String body;
    private String subject;
}
