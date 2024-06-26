package com.workintech.spring17challenge.dto;

import com.workintech.spring17challenge.model.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Course course;
    private Integer totalGpa;
}
