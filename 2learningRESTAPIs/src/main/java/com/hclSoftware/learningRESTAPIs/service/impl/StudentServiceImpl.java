package com.hclSoftware.learningRESTAPIs.service.impl;

import com.hclSoftware.learningRESTAPIs.dto.AddStudentRequestDto;
import com.hclSoftware.learningRESTAPIs.dto.StudentDto;
import com.hclSoftware.learningRESTAPIs.entity.Student;
import com.hclSoftware.learningRESTAPIs.repository.StudentRepository;
import com.hclSoftware.learningRESTAPIs.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
//@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    /*
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    // Either do this or use @RequiredArgsConstructor
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }
     */

    // Alternate of this is:
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        // but we need to return Student DTO
        // 1) Using for loop
        /*

        List<StudentDto> studentDtoList1 = new ArrayList<>();
        for(Student student : students){
            StudentDto newStudent = new StudentDto(student.getId(), student.getName(), student.getEmail());
            studentDtoList1.add(newStudent);
        }

        */


        // 2) using Stream
        return students
                .stream()
                .map(student -> modelMapper.map(student, StudentDto.class))
//                .map(student -> new StudentDto(student.getId(), student.getName(), student.getEmail()))
                .toList();
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID : "+ id));

        // convert the Student to StudentDto

        // 1) manually
//        return new StudentDto(student.getId(), student.getName(), student.getEmail());

        // 2) model mapper
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto) {
        Student newStudent = modelMapper.map(addStudentRequestDto, Student.class);
        Student student = studentRepository.save(newStudent);
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public void deleteStudentById(Long id) {
        if(!studentRepository.existsById(id)){
            throw new IllegalArgumentException("Student with id "+id+" does not exists");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto) {
        // Check Weather Student exists or not
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID : "+ id));

        // if user exists, then update the user
        modelMapper.map(addStudentRequestDto, student);

        // Save the changes to Database, using repository
        studentRepository.save(student);

        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public StudentDto updatePartialStudent(Long id, Map<String, Object> updates) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with ID : "+ id));
        updates.forEach((field, value) -> {
            switch (field){
                case "name": student.setName((String) value);
                    break;
                case "email": student.setEmail((String) value);
                    break;
                default: throw new IllegalArgumentException("Field is not Supported!");
            }
        });
        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent, StudentDto.class);
    }
}
