package com.sparta.moit.domain.meeting.repository;

import com.sparta.moit.domain.meeting.entity.RegionFirst;
import com.sparta.moit.domain.meeting.entity.RegionSecond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionSecondRepository extends JpaRepository<RegionSecond, Short> {
    List<RegionSecond> findAllByRegionFirstOrderByRegionSecondId(RegionFirst regionFirst);

    @Query(value = "SELECT s.*, f.region_first_id AS region_first_id_alias "
            + "FROM region_second s "
            + "JOIN region_first f ON s.region_first_id = f.region_first_id",
            nativeQuery = true)
    List<RegionSecond> findAllWithRegionFirstName();
}
