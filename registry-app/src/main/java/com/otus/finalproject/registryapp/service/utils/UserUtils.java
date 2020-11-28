package com.otus.finalproject.registryapp.service.utils;

import com.otus.finalproject.registryapp.role.UserRoles;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserUtils {

    public boolean isAdminUser() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.getAuthority().equals(String.format("ROLE_%s",UserRoles.ADMIN_ROLE))
        );
    }

    public String getUserName() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return principal.getName();
    }
}
