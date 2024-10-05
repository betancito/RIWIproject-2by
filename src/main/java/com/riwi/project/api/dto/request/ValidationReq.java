package com.riwi.project.api.dto.request;

import com.riwi.project.api.Exeptions.UnauthorizedException;
import com.riwi.project.infrastructure.security.JwtService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class ValidationReq {

    @Autowired
    private JwtService jwtService;

    public void isAdmin(String token) {
        String role = jwtService.extractRole(token).trim();
        if (!"ADMIN".equals(role)) {
            throw new UnauthorizedException("Only Administrators can use this feature");
        }
    }
}
