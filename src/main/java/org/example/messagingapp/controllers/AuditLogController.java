package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.entities.AuditLog;
import org.example.messagingapp.repositories.AuditLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/admin/logs")
@AllArgsConstructor
public class AuditLogController {
        private final AuditLogRepository auditLogRepository;

        @GetMapping
        public List<AuditLog> getLogs() {
            return auditLogRepository.findAll();
        }
    }

