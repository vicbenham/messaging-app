package org.example.messagingapp.controllers;

import lombok.AllArgsConstructor;
import org.example.messagingapp.dtos.AuditLogView;
import org.example.messagingapp.services.AuditLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/logs")
@AllArgsConstructor
public class AuditLogController {
        private final AuditLogService auditLogService;

        @GetMapping
        public List<AuditLogView> listAllLogs() {
            return auditLogService.listAllLogs();
        }
    }

