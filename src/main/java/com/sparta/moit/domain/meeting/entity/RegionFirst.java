package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name="region_first")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class RegionFirst {

    @Column(name = "region_first_id")
    @Id
    private Long regionFirstId;

    @Column(name = "region_first_name")
    private String regionFirstName;
}
