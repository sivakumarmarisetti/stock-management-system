package com.stockmanagement.service;

import com.stockmanagement.dto.TenantRequestDto;
import com.stockmanagement.dto.TenantResponseDto;
import com.stockmanagement.entity.Tenant;
import com.stockmanagement.exception.DuplicateResourceException;
import com.stockmanagement.exception.ResourceNotFoundException;
import com.stockmanagement.mapper.TenantMapper;
import com.stockmanagement.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantResponseDto createTenant(
            TenantRequestDto requestDto) {

        if (tenantRepository
                .findByTenantCode(
                        requestDto.getTenantCode()
                ).isPresent()) {

            throw new DuplicateResourceException(
                    "Tenant code already exists"
            );
        }

        Tenant tenant = Tenant.builder()
                .tenantCode(requestDto.getTenantCode())
                .tenantName(requestDto.getTenantName())
                .active(true)
                .build();

        Tenant savedTenant =
                tenantRepository.save(tenant);

        return TenantMapper
                .mapToTenantResponseDto(savedTenant);
    }

    public List<TenantResponseDto> getAllTenants() {

        return tenantRepository.findAll()
                .stream()
                .map(TenantMapper::mapToTenantResponseDto)
                .toList();
    }

    public TenantResponseDto getTenantById(Long id) {

        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tenant not found"
                        ));

        return TenantMapper
                .mapToTenantResponseDto(tenant);
    }
}