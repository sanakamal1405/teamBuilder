package com.scaler.teambuilder.controller;


import com.scaler.teambuilder.dto.ResponseStatus;
import com.scaler.teambuilder.dto.request.ParticipantRequestDTO;
import com.scaler.teambuilder.dto.response.ParticipantResponseDTO;
import com.scaler.teambuilder.entities.Participant;
import com.scaler.teambuilder.repos.ParticipantRepo;
import com.scaler.teambuilder.services.ParticipantService;
import com.scaler.teambuilder.services.TeamBuilderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/teambuilder")
public class TeamBuilderController {

    public ParticipantRepo participantRepo;

    public TeamBuilderService teamBuilerservice;

    public ParticipantService  participantService;

    public TeamBuilderController(ParticipantRepo participantRepo, TeamBuilderService service, ParticipantService participantService) {

        this.participantRepo=participantRepo;
        this.teamBuilerservice=service;
        this.participantService=participantService;
    }


//    @PostMapping("/addParticipant")
//    public ResponseEntity<?> addParticipant(@RequestBody Participant p)
//    {
//
//            try{
//                participantRepo.save(p);
//            } catch (Exception e) {
//                return new ResponseEntity<>(new RuntimeException(e),HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @PostMapping("/participant")
    public ResponseEntity<ParticipantResponseDTO> addParticipant(@RequestBody ParticipantRequestDTO participantRequestDTO) {
        ParticipantResponseDTO participantResponseDTO = new ParticipantResponseDTO();
        try {
            Participant participantRes = participantService.addParticipantToDB(participantRequestDTO);

            participantResponseDTO.setParticipant(participantRes);
            participantResponseDTO.setResponseStatus(com.scaler.teambuilder.dto.ResponseStatus.SUCCESS);

        return new ResponseEntity<>(participantResponseDTO, HttpStatus.OK);
    }
        catch (Exception e) {
            participantResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            return new ResponseEntity<>(participantResponseDTO, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/buildTeam/{size}")
    public ResponseEntity<?> buildTeam(@PathVariable("size") int size)
    {

        return teamBuilerservice.buildTeam(size);
    }
}
