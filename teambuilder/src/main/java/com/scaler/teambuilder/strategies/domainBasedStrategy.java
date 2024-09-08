package com.scaler.teambuilder.strategies;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.teambuilder.dto.NotificationDTO;
import com.scaler.teambuilder.entities.Participant;
import com.scaler.teambuilder.entities.Team;
import com.scaler.teambuilder.repos.ParticipantRepo;
import com.scaler.teambuilder.repos.TeamRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Team> > buildTeam(int size) {

        List<Team> response=new ArrayList<>();

        try {

            List<Participant> list = participantRepo.findAll();

            // Collecting all the Unassigned members
            list=list.stream().filter(participant -> participant.getAssigned()==0).collect(Collectors.toList());

            int teamsCount = list.size() / size;

            if (list.size() % size != 0)
                teamsCount++;

            //Segregating on the basis of domain of the participant

            Map<String, List<Participant>> map = list.stream()
                    .collect(Collectors.groupingBy(participant -> participant.getDomain()));

            List<Participant> team = new ArrayList<>();

            System.out.println(map);


            int index = 0;


            //Team Building Algorithm : One fom each type till group size reached


            while (teamsCount != 0)
            {
                for (Map.Entry<String, List<Participant>> m : map.entrySet()) {

                    List<Participant> l = m.getValue();


                    if (teamsCount == 1 && team.size() == list.size() % size && list.size() % size != 0)
                    {   //if only one team left with numbers of members left is less than desired size

                        teamsCount = 0;

                        Team t1 = new Team();
                        t1.setPartcipant(team);

                        Team t2 = teamRepo.save(t1);



                        for (Participant p : team) {
                            p.setTeam(t2);
                            p.setAssigned(1);
                            participantRepo.save(p);
                            NotificationDTO notificationDTO = NotificationDTO.builder()
                                    .to(p.getEmail())
                                    .from("kamalsana5041@gmail.com")
                                    .body("Hey " + p.getName() + "! you have been added to Team " + t2.getId() + "🌟")
                                    .subject("Team Assignment Alert !")
                                    .build();


                            kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(notificationDTO));
                        }

                        response.add(t2);

                        break;
                    }
                    if (l.size() <= index)
                    {
                        //if all the members of this domian is alloted then move to next domain
                        continue;

                    }
                    else {
                        team.add(l.get(index));
                        if (team.size() == size)
                        {
                            //if team size reached desired size then create new team

                            Team t1 = new Team();
                            t1.setPartcipant(team);


                            Team t2 = teamRepo.save(t1);



                            for (Participant p : team) {
                                p.setTeam(t2);
                                p.setAssigned(1);
                                participantRepo.save(p);

                                NotificationDTO notificationDTO = NotificationDTO.builder()
                                        .to(p.getEmail())
                                        .from("kamalsana5041@gmail.com")
                                        .body("Hey " + p.getName() + "! you have been added to Team " + t2.getId() + "🌟")
                                        .subject("Team Assignment Alert !")
                                        .build();


                                kafkaTemplate.send("sendEmail", objectMapper.writeValueAsString(notificationDTO));
                            }

                            response.add(t2);
                            team = new ArrayList<>();
                            teamsCount--;
                        }

                    }

                }
                index++;
            }

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
