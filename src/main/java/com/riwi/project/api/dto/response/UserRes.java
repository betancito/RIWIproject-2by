package com.riwi.project.api.dto.response;

import com.riwi.project.domain.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRes {
    private String username;
    private String email;
    private UserRole role;
}
