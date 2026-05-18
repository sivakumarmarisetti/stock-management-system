package com.stockmanagement.service;

import com.stockmanagement.entity.AuditLog;
import com.stockmanagement.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void saveLog(
            String username,
            String action,
            String endpoint,
            String httpMethod
    ) {

        AuditLog auditLog = AuditLog.builder()
                .username(username)
                .action(action)
                .endpoint(endpoint)
                .httpMethod(httpMethod)
                .createdAt(LocalDateTime.now())
                .build();

        auditLogRepository.save(auditLog);
    }
}