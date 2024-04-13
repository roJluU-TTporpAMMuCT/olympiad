package org.nakedprogrammer.olympiad.repos;

import org.nakedprogrammer.olympiad.models.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionRepo extends JpaRepository<Solution, Long> {
}
