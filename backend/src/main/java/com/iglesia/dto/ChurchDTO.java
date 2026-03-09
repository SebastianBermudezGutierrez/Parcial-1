package com.iglesia.dto;

import jakarta.validation.constraints.NotBlank;

public record ChurchDTO(
    Long id,
    @NotBlank String name,
    String address
) {
    public static ChurchDTO from(com.iglesia.Church church) {
        return new ChurchDTO(
            church.getId(),
            church.getName(),
            church.getAddress()
        );
    }
}
