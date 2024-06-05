package org.nakedprogrammer.olympiad.controllers;

import lombok.AllArgsConstructor;
import org.nakedprogrammer.olympiad.models.Quest;
import org.nakedprogrammer.olympiad.models.Translation;
import org.nakedprogrammer.olympiad.repos.QuestRepo;
import org.nakedprogrammer.olympiad.repos.TranslationRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class FetchController {

    private TranslationRepo tRepo;
    private QuestRepo qRepo;

    @GetMapping("/translation/{questName}/{lang}")
    public ResponseEntity<Translation> getTranslation(@PathVariable String questName, @PathVariable String lang){
        Translation t = tRepo.findAnyByQuest_idAndLang(qRepo.findAnyByName(questName).getId(), lang);
        t.setQuest(null);
        t.setUserok(null);
        t.setSolutions(null);
        return ResponseEntity.ok(t);
    }

    @GetMapping("/quest")
    public ResponseEntity<List<Quest>> getQuests(){
        List<Quest> quests = qRepo.findAll();
        List<Quest> toSend = new ArrayList<>();
        for(Quest quest : quests){
            Quest toAdd = new Quest(quest.getId(),quest.getName(),quest.getDescription(),new ArrayList<>(), null);
            toSend.add(toAdd);
            for(Translation t : quest.getTranslations()){
                toAdd.getTranslations().add(new Translation(t.getId(),t.getLang(),t.getSample_solution(),t.getClassName()
                                                            ,t.getTimelimit(),t.getVisibleTestCode(),t.getHiddenTestCode()
                                                            ,null,null,null));
            }
        }
        return ResponseEntity.ok(toSend);
    }
}
