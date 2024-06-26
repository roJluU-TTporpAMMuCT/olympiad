package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.*;
import org.nakedprogrammer.olympiad.repos.*;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@AllArgsConstructor
public class AttestationController {

    UserRepo userRep;
    QuestRepo questRep;
    TranslationRepo transRep;
    SolutionRepo solutionRep;
    RestClient restClient;
    DiscoveryClient discClient;
    SessionUserHolder holder;

    @PostMapping("/quest")
    public ResponseEntity<Quest> createQuest(@RequestBody Quest quest, @RequestHeader(name = "Authorization") String token){
        Userok user = holder.container.get(token);
        if(user == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        quest.setUserok(userRep.findAnyByUsername(user.getUsername()));
        return ResponseEntity.ok(questRep.save(quest));
    }

    @PostMapping("/quests/{questName}")
    public ResponseEntity<AttestResult> createTranslation(@PathVariable String questName, @RequestBody Translation translation,
                                                    @RequestHeader(name = "Authorization") String token) {
        Userok user = holder.container.get(token);
        if(user == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        AttestResult resp = this.attest(translation.getLang(), translation.getClassName(), translation.getSample_solution(),
                                                    translation.getVisibleTestCode(), translation.getTimelimit());
        if(resp.getPass().equals(true)) {
            translation.setQuest(questRep.findAnyByName(questName));
            translation.setUserok(userRep.findAnyByUsername(user.getUsername()));
            transRep.save(translation);
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    @PostMapping("/quests/{questName}/{lang}")
    public ResponseEntity<AttestResult> createSolution(@PathVariable String questName, @PathVariable String lang, @RequestBody Solution solution,
                                                 @RequestHeader(name = "Authorization") String token){
        Userok user = holder.container.get(token);
        if(user == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Translation translation = transRep.findAnyByQuest_idAndLang(questRep.findAnyByName(questName).getId(), lang);
        AttestResult resp = this.attest(translation.getLang(), translation.getClassName(), solution.getCode(),
                                        translation.getVisibleTestCode(), translation.getTimelimit());
        if(resp.getPass().equals(true)){
            solution.setTranslation(translation);
            solution.setUserok(userRep.findAnyByUsername(user.getUsername()));
            solutionRep.save(solution);
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resp);
    }

    private AttestResult attest(String lang, String className, String sourceCode, String testSourceCode, Integer timelimit){
        ServiceInstance inst = discClient.getInstances(lang + "-attestation-micro").get(0);
        return restClient.post().uri("http://" + inst.getHost() + ":" + inst.getPort())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("className", className != null ? className : "null", "sourceCode", sourceCode,
                        "testSourceCode", testSourceCode, "timelimit", timelimit)).retrieve().toEntity(AttestResult.class).getBody();
    }
}
