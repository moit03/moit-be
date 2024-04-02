package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {
    List<Career> findByIdIn(List<Long> ids);
}
