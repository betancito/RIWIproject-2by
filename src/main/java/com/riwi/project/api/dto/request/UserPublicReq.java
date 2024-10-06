package com.riwi.project.api.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPublicReq {
    public String username;
    public String password;
    public String email;
}
