package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Quest;
import org.nakedprogrammer.olympiad.models.Solution;
import org.nakedprogrammer.olympiad.models.Translation;
import org.nakedprogrammer.olympiad.repos.QuestRepo;
import org.nakedprogrammer.olympiad.repos.SolutionRepo;
import org.nakedprogrammer.olympiad.repos.TranslationRepo;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AttestationController {

    UserRepo userRep;
    QuestRepo questRep;
    TranslationRepo transRep;
    SolutionRepo solutionRep;
    RestClient restClient;

    @PostMapping("quest")
    public ResponseEntity createQuest(@RequestBody Quest quest){
        return ResponseEntity.ok(questRep.save(quest) );
    }

    @PostMapping("quest/{questName}")
    public ResponseEntity createTranslation(@PathVariable String questName, @RequestBody Translation translation) {
        System.out.println(translation.getClassName());
        ResponseEntity<String> resp = this.attest(translation.getClassName(), translation.getSample_solution(), translation.getVisibleTestCode(),
                translation.getTimelimit());
        System.out.println(resp.getBody());
        if(resp.getBody().equals("[]")) {
            System.out.println("Saved");
            translation.setQuest(questRep.findAnyByName(questName));
            transRep.save(translation);
        }
        return ResponseEntity.ok(resp.getBody());
    }

    @PostMapping("quest/{questName}/{lang}")
    public ResponseEntity createSolution(@PathVariable String questName, @PathVariable String lang, @RequestBody Solution solution){
        Translation translation = transRep.findAnyByQuest_idAndLang(questRep.findAnyByName(questName).getId(), lang);
        ResponseEntity<String> resp = this.attest(translation.getClassName(),
                solution.getCode(), translation.getVisibleTestCode(), translation.getTimelimit());
        if(resp.getBody().equals("[]")){
            System.out.println("Saved");
            solution.setTranslation(translation);
            solutionRep.save(solution);
        }
        return ResponseEntity.ok(resp.getBody());
    }

    private ResponseEntity<String> attest(String className, String sourceCode, String testSourceCode, Integer timelimit){
        return restClient.post().uri("http://localhost:8080")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("className", className, "sourceCode", sourceCode,
                        "testSourceCode", testSourceCode, "timelimit", timelimit)).retrieve().toEntity(String.class);
    }
}
