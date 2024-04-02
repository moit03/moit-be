package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="region")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Region {

    @Column(name = "region_id")
    @Id
    private Short regionId;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "region_type")
    @Enumerated(EnumType.STRING)
    private RegionType regionType;

    @Column(name = "parent_region_id")
    private Short parentRegionId;

    @Builder
    public Region(String regionName, RegionType regionType, Short parentregionId) {
        this.regionName = regionName;
        this.regionType = regionType;
        this.parentRegionId = parentregionId;
    }
}
