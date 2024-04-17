package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Quest;
import org.nakedprogrammer.olympiad.models.Translation;
import org.nakedprogrammer.olympiad.repos.QuestRepo;
import org.nakedprogrammer.olympiad.repos.SolutionRepo;
import org.nakedprogrammer.olympiad.repos.TranslationRepo;
import org.nakedprogrammer.olympiad.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<String> resp = restClient.post().uri("http://localhost:8080")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("className", translation.getClassName(),
                "sourceCode", translation.getSample_solution(),
                "testSourceCode", translation.getVisibleTestCode(),
                "timelimit", 5000)).retrieve().toEntity(String.class);
        System.out.println(resp.getBody());
        if(resp.getStatusCode().equals(HttpStatusCode.valueOf(200))) {
            System.out.println("Saved");
            translation.setQuest(questRep.findAnyByName(questName));
            transRep.save(translation);
        }
        return ResponseEntity.ok(resp.getBody());
    }
}
