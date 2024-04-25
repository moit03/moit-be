package com.sparta.moit.domain.meeting.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity(name="region_first")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class RegionFirst {

    @Column(name = "region_first_id")
    @Id
    private Short regionFirstId;

    @Column(name = "region_first_name")
    private String regionFirstName;

    @OneToMany(mappedBy = "regionFirst")
    @JsonManagedReference
    private List<RegionSecond> regionSeconds;
}
