package com.scaler.Notification.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class NotificationDTO {


    private String to;
    private String from;
    private String body;
    private String subject;
}
