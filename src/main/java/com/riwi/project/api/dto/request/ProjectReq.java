package com.riwi.project.api.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProjectReq {
    private Long id;
    private String name;
    private String description;
}
