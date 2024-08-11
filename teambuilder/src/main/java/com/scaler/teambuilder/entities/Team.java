package com.scaler.teambuilder.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy="team")
    List<Participant> partcipant;

    @Override
    public String toString() {
        return "Team{id=" + id + ", partcipantCount=" + (partcipant != null ? partcipant.size() : 0) + "}";
    }

}
