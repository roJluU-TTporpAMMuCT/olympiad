package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Solution;
import org.nakedprogrammer.olympiad.models.Translation;
import org.nakedprogrammer.olympiad.repos.QuestRepo;
import org.nakedprogrammer.olympiad.repos.SolutionRepo;
import org.nakedprogrammer.olympiad.repos.TranslationRepo;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("quests/{questName}")
    public ResponseEntity<String> createTranslation(@PathVariable String questName, @RequestBody Translation translation) {
        ResponseEntity<String> resp = this.attest(translation.getClassName(), translation.getSample_solution(), translation.getVisibleTestCode(),
                translation.getTimelimit());
        if(resp.getBody().equals("[]")) {
            translation.setQuest(questRep.findAnyByName(questName));
            transRep.save(translation);
        }
        return ResponseEntity.ok(resp.getBody());
    }

    @PostMapping("quests/{questName}/{lang}")
    public ResponseEntity<String> createSolution(@PathVariable String questName, @PathVariable String lang, @RequestBody Solution solution){
        Translation translation = transRep.findAnyByQuest_idAndLang(questRep.findAnyByName(questName).getId(), lang);
        ResponseEntity<String> resp = this.attest(translation.getClassName(),
                solution.getCode(), translation.getVisibleTestCode(), translation.getTimelimit());
        if(resp.getBody().equals("[]")){
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
