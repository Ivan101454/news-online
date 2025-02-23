package ru.clevertec.newsonline.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, JOURNALIST, SUBSCRIBER, NONE;

    @Override
    public String getAuthority() {
        return name();
    }
}
