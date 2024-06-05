package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.GET})
@Repository
public interface TranslationRepo extends JpaRepository<Translation, Long> {
    Translation findAnyByQuest_idAndLang(Long quest_id, String lang);
}
