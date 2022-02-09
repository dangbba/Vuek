package com.ssafy.db.repository;

import com.ssafy.db.entity.Marathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarathonRepository extends JpaRepository<Marathon, Integer> {
}
