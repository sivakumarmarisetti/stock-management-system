package com.stockmanagement.service;

import com.stockmanagement.dto.UserRegistrationRequestDto;
import com.stockmanagement.dto.UserResponseDto;
import com.stockmanagement.entity.RoleType;
import com.stockmanagement.entity.Tenant;
import com.stockmanagement.entity.User;
import com.stockmanagement.exception.DuplicateResourceException;
import com.stockmanagement.exception.ResourceNotFoundException;
import com.stockmanagement.mapper.UserMapper;
import com.stockmanagement.repository.TenantRepository;
import com.stockmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TenantRepository tenantRepository;

    public UserResponseDto registerUser(UserRegistrationRequestDto requestDto){
        // Prevent self-escalation — no one can register a SUPER_ADMIN via this endpoint
        // SUPER_ADMIN must be seeded directly in DB
        if (requestDto.getRole() == RoleType.SUPER_ADMIN) {
            throw new RuntimeException("Cannot register SUPER_ADMIN via this endpoint");
        }
        Tenant tenant = tenantRepository.findById(
                requestDto.getTenantId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Tenant not found"
                ));
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new DuplicateResourceException(
                    "Email already exists");
        }
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new DuplicateResourceException(
                    "Username already exists");
        }
        User user = User.builder()
                .tenant(tenant)
                .fullName(requestDto.getFullName())
                .email(requestDto.getEmail())
                .username(requestDto.getUsername())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(requestDto.getRole())
                .build();
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserResponseDto(savedUser);

    }

    public List<UserResponseDto> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id){
        User user = getUserEntityById(id);
        return UserMapper.mapToUserResponseDto(user);
    }

    private User getUserEntityById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }
}
