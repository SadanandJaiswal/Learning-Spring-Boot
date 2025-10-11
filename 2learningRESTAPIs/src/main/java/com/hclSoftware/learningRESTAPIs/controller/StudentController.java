package com.hclSoftware.learningRESTAPIs.controller;

import com.hclSoftware.learningRESTAPIs.dto.AddStudentRequestDto;
import com.hclSoftware.learningRESTAPIs.dto.StudentDto;
import com.hclSoftware.learningRESTAPIs.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
//@RequestMapping("/student")   // if all routes in this start with /student/
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(){
//        return ResponseEntity.status(200).body(studentService.getAllStudents());
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(id));
    }

    @PostMapping("/student")
    public ResponseEntity<StudentDto> createNewStudent(@RequestBody AddStudentRequestDto addStudentRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createNewStudent(addStudentRequestDto));
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody AddStudentRequestDto addStudentRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(studentService.updateStudent(id, addStudentRequestDto));
    }

    @PatchMapping("/student/{id}")
    public ResponseEntity<StudentDto> updatePartialStudent(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates
    ){
        return ResponseEntity.ok(studentService.updatePartialStudent(id, updates));
    }
}
