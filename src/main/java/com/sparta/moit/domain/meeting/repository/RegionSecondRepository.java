package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import com.sparta.moit.domain.meeting.entity.RegionSecond;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionSecondRepository extends JpaRepository<RegionSecond, Short> {
    List<RegionSecond> findAllByRegionFirst(RegionFirst regionFirst);
}
