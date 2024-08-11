package com.scaler.teambuilder.dto.response;

import com.scaler.teambuilder.dto.ResponseStatus;
import com.scaler.teambuilder.entities.Participant;
import lombok.Data;

@Data
public class ParticipantResponseDTO {

    Participant participant;
    public ResponseStatus responseStatus;
}
