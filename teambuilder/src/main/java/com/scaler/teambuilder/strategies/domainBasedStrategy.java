package com.scaler.teambuilder.strategies;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.teambuilder.dto.NotificationDTO;
import com.scaler.teambuilder.entities.Participant;
import com.scaler.teambuilder.entities.Team;
import com.scaler.teambuilder.repos.ParticipantRepo;
import com.scaler.teambuilder.repos.TeamRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Primary

public class domainBasedStrategy implements IbuildTeam {


    private ParticipantRepo participantRepo;

    private TeamRepo teamRepo;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;


    public domainBasedStrategy (ParticipantRepo repo, TeamRepo teamRepo, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper){

        this.participantRepo=repo;
        this.teamRepo=teamRepo;
        this.kafkaTemplate=kafkaTemplate;
        this.objectMapper=objectMapper;
    }

    @Override
    public void buildTeam(int size) {

        try {
            List<Participant> list = participantRepo.findAll();

            int teamsCount = list.size() / size;

            if (list.size() % size != 0)
                teamsCount++;

            Map<String, List<Participant>> map = list.stream()
                    .collect(Collectors.groupingBy(participant -> participant.getDomain()));

            List<Participant> team = new ArrayList<>();
            int index = 0;

            //Team Building Algorithm : One fom each type till group size reached


            while (teamsCount != 0) {
                for (Map.Entry<String, List<Participant>> m : map.entrySet()) {
                    List<Participant> l = m.getValue();
                    if (teamsCount == 1 && team.size() == list.size() % size && list.size() % size != 0) {
                        teamsCount = 0;

                        Team t1 = new Team();
                        t1.setPartcipant(team);
                        Team t2 = teamRepo.save(t1);

                        for (Participant p : team) {
                            p.setTeam(t2);
                            participantRepo.save(p);
                            NotificationDTO notificationDTO = NotificationDTO.builder()
                                    .to(p.getEmail())
                                    .from("kamalsana5041@gmail.com")
                                    .body("Hey " + p.getName() + "! you have been added to Team " + t2.getId() + "🌟")
                                    .subject("Team Assignment Alert !")
                                    .build();


                            kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(notificationDTO));
                        }

                        break;
                    }
                    if (l.size() <= index) {

                        System.out.println(l.size() + "  " + index);

                        continue;

                    } else {
                        team.add(l.get(index));
                        if (team.size() == size) {


                            Team t1 = new Team();
                            t1.setPartcipant(team);
                            Team t2 = teamRepo.save(t1);

                            for (Participant p : team) {
                                p.setTeam(t2);
                                participantRepo.save(p);

                                NotificationDTO notificationDTO = NotificationDTO.builder()
                                        .to(p.getEmail())
                                        .from("kamalsana5041@gmail.com")
                                        .body("Hey " + p.getName() + "! you have been added to Team " + t2.getId() + "🌟")
                                        .subject("Team Assignment Alert !")
                                        .build();


                                kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(notificationDTO));
                            }


//                            System.out.println("Next team");
                            team = new ArrayList<>();
                            teamsCount--;
                        }

                    }

                }
                index++;
            }
            if (list.size() % size != 0) {
                System.out.println("here1");
                for (Participant p : team)
                    System.out.println(p);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
