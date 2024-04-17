package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepo extends JpaRepository<Quest, Long> {

    Quest findAnyByName(String name);
}
