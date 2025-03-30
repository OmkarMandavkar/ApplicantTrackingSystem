package com.dev.ATSapp.Enums;

public enum RoundStatus {
    PENDING,        // Round is created but not yet scheduled
    SCHEDULED,      // Email sent, round is officially scheduled
    IN_PROGRESS,    // Interview is currently happening
    SELECTED,       // Candidate passed this round
    REJECTED,       // Candidate failed this round
    COMPLETED       // Round has been conducted
}
