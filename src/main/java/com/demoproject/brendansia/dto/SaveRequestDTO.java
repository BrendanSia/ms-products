package com.demoproject.brendansia.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveRequestDTO {
    private Integer id;
    private String code;
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;
}
