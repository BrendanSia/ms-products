package com.demoproject.brendansia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name length must be between 2 and 50")
    private String name;
    private String category;
    private String brand;
    private String type;
    private String description;
}