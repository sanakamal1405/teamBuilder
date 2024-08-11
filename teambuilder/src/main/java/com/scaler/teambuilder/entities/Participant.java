package com.scaler.teambuilder.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Participant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;
    String phone;
    String domain;
    @ManyToOne
    @JoinColumn(name="team_id")
    @JsonIgnore
    Team team;
    double yoe;

    @Override
    public String toString() {
        return "Participant{id=" + id + ", name='" + name + "', email='" + email + "', phone='" + phone + "', domain='" + domain + "', yoe=" + yoe + "}";
    }

}
