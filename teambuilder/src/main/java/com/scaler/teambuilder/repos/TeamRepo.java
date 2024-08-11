package com.scaler.teambuilder.repos;

import com.scaler.teambuilder.entities.Participant;
import com.scaler.teambuilder.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TeamRepo extends JpaRepository<Team, Integer> {
}
