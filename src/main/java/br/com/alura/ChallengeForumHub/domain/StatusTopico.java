package br.com.alura.ChallengeForumHub.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum StatusTopico {
    ABERTO,
    FECHADO;

    @JsonCreator
    public static StatusTopico fromString(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return StatusTopico.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
