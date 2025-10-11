package com.hclSoftware.learningRESTAPIs.controller;

import com.hclSoftware.learningRESTAPIs.dto.StudentDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test/isWorking")
public class IsAPIWorkingController {
    @GetMapping("/")
    public StudentDto getStudent(){
        return new StudentDto(1L, "ABC", "ABC@gmail.com");  // by default sent to json
    }

    @GetMapping("/{id}/{semester}")
    public String getStudentDetail(
            @PathVariable("id") Long studentId,
            @PathVariable String semester,
            @RequestParam(value= "department", defaultValue = "Computer Science and Engineering") String departmentName,
            @RequestParam(required = false) String year) {

        return """
            Student Id   : """ + studentId + """
            \nDepartment  : """ + departmentName + """
            \nSemester    : """ + semester + """
            \nYear        : """ + (year != null ? year : "Not Provided");
    }
}
