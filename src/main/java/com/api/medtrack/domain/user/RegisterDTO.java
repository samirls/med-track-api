package com.api.medtrack.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}