package com.workintech.spring17challenge.validation;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.Grade;
import org.springframework.http.HttpStatus;


public class CourseValidation {
    public static void checkName(String name) {
        if (name == null || name.isEmpty()) {
            throw new ApiException("Course name cannot be empty." + name, HttpStatus.BAD_REQUEST);
        }
    }

    public  static void checkCredit(Integer credit) {
        if (credit == null || credit < 0 ||  credit>4) {
            throw new ApiException("Course credit cannot be empty." + credit, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkId(Integer id) {
        if (id == null || id < 0) {
            throw new ApiException("Course id cannot be empty." + id, HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkGrade(Grade grade) {
        if (grade == null) {
            throw new ApiException("Course grade cannot be empty." + grade, HttpStatus.BAD_REQUEST);
        }
    }
}
