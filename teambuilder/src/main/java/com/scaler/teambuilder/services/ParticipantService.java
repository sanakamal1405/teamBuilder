package com.scaler.teambuilder.services;

import com.scaler.teambuilder.dto.request.ParticipantRequestDTO;
import com.scaler.teambuilder.entities.Participant;
import com.scaler.teambuilder.repos.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepo participantRepository;

    public Participant addParticipantToDB(ParticipantRequestDTO participantRequestDTO) {

        Participant participant = new Participant();
        participant.setName(participantRequestDTO.getName());
        participant.setEmail(participantRequestDTO.getEmail());
        participant.setPhone(participantRequestDTO.getPhone());
        participant.setDomain(participantRequestDTO.getDomain());
        participant.setYoe(participantRequestDTO.getYoe());
        return participantRepository.save(participant);
    }
}
