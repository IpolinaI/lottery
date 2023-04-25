package com.demo.lottery.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@EqualsAndHashCode
public class User {
    private UUID uuid;

    private String username;

    private String password;

    private Set<Role> roles;
}
