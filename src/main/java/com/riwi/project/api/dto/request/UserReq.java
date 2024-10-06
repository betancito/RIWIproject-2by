package com.riwi.project.api.dto.request;

import com.riwi.project.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserReq {
    public UserReq() {
    }
    private Long id;
    private String username;
    private String email;
    private String password;
    private UserRole role;
}
