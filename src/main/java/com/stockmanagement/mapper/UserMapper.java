package com.stockmanagement.mapper;

import com.stockmanagement.dto.UserResponseDto;
import com.stockmanagement.entity.User;

public class UserMapper {

    public static UserResponseDto mapToUserResponseDto(User user){

        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.getActive())
                .build();
    }
}
