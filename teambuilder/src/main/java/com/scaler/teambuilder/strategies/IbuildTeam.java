package com.scaler.teambuilder.strategies;

import com.scaler.teambuilder.entities.Team;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IbuildTeam {

    ResponseEntity<List<Team>> buildTeam(int size);
}
