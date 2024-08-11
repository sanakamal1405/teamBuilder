package com.scaler.teambuilder.services;


import com.scaler.teambuilder.strategies.IbuildTeam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamBuilderService {


    private IbuildTeam IbuildTeam;



    public TeamBuilderService(IbuildTeam buildTeam)
    {
        this.IbuildTeam=buildTeam;
    }


    public ResponseEntity<?> buildTeam(int size) {

        try{
        IbuildTeam.buildTeam(size);

        } catch (Exception e) {
            new ResponseEntity<>(new RuntimeException(e),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
