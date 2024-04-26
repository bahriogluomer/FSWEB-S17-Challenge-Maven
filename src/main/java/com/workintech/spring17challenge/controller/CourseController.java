package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.dto.ApiResponse;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.model.*;
import com.workintech.spring17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa lowCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa highCourseGpa;


    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @PostConstruct
    public void init() {
        courses= new ArrayList<>();
    }

    @GetMapping
    public List<Course> getCourses() {
        return courses;
    }

    @GetMapping("/{name}")
    public Course getCourseByName(@PathVariable("name") String name) {
        CourseValidation.checkName(name);
        return courses.stream().filter(course -> course.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Course course) {
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkGrade(course.getGrade());
        CourseValidation.checkId(course.getId());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        return new ResponseEntity<>(new ApiResponse(course, totalGpa), HttpStatus.CREATED);
    }

    private Integer getTotalGpa(Course course) {
        int totalGpa = 0;
      if(course.getCredit()<=2){
          totalGpa = course.getGrade().getCoefficient()*course.getCredit()*lowCourseGpa.getGpa();
      }else if(course.getCredit()==3){
          totalGpa =  course.getGrade().getCoefficient()*course.getCredit()*mediumCourseGpa.getGpa();
      }else{
          totalGpa = course.getGrade().getCoefficient()*course.getCredit()*highCourseGpa.getGpa();
      }
      return totalGpa;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Integer id, @RequestBody Course course) {
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkGrade(course.getGrade());
        CourseValidation.checkId(course.getId());
        Course existingCourse = courses.stream().filter(course1 -> course1.getId() == id).findFirst().orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
        int indexOfExisting = courses.indexOf(existingCourse);
        courses.set(indexOfExisting, course);
        Integer totalGpa = getTotalGpa(existingCourse);
        existingCourse.setName(course.getName());
        existingCourse.setCredit(course.getCredit());
        existingCourse.setGrade(course.getGrade());

        return new ResponseEntity<>(new ApiResponse(existingCourse, totalGpa), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable("id") Integer id) {
        CourseValidation.checkId(id);
        Course existingCourse = courses.stream().filter(course1 -> course1.getId() == id).findFirst().orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));
        courses.remove(existingCourse);
        Integer totalGpa = getTotalGpa(existingCourse);
        return new ResponseEntity<>(new ApiResponse(existingCourse, totalGpa), HttpStatus.OK);
    }

}
