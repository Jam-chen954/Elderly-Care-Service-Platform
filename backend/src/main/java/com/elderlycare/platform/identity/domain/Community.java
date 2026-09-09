package com.elderlycare.platform.identity.domain;

/** Internal projection. Controllers return dedicated DTOs instead of persistence objects. */
public record Community(Long id, String name) {}
