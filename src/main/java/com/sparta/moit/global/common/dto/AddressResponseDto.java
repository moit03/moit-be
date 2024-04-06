package com.sparta.moit.global.common.dto;

import lombok.Getter;

@Getter
public class AddressResponseDto {
    private String lat;
    private String lng;

    public AddressResponseDto(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
