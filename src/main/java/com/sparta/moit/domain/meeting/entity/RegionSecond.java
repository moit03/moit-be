package com.sparta.moit.domain.meeting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "region_second")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class RegionSecond {

    @Column(name = "region_second_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short regionSecondId;

    private Double regionLat;

    private Double regionLng;

    @Column(name = "region_second_name")
    private String regionSecondName;

    @ManyToOne
    @JoinColumn(name = "region_first_id", nullable = false)
    private RegionFirst regionFirst;

    public void update(Double lat, Double lng) {
        this.regionLat = lat;
        this.regionLng = lng;
    }
}
