package com.scaler.teambuilder.repos;

import com.scaler.teambuilder.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepo extends JpaRepository<Participant, Integer> {
}




