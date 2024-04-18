package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRepo extends JpaRepository<Translation, Long> {
    Translation findAnyByQuest_idAndLang(Long quest_id, String lang);
}
