package com.stockmanagement.dto;

import com.stockmanagement.entity.RoleType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String fullName;
    private String email;
    private String username;
    private RoleType role;
    private Boolean active;
}
