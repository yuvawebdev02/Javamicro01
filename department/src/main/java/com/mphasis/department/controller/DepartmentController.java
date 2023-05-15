package com.mphasis.department.controller;

import com.mphasis.department.entity.Department;
import com.mphasis.department.entity.DisplayInfoVO;
import com.mphasis.department.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/")
    public ResponseEntity<String> saveDepartment(@RequestBody Department department){
        log.info("saveDepartment : Start");
        Department saved = departmentService.saveDepartment(department);
        return new ResponseEntity<>("Department added successfully " + saved.getDepartmentId(), HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Department> getDepartment(@PathVariable("Id") Long Id){
        log.info("getDepartment : Start");
        Department found = departmentService.getDepartmentById(Id);
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Department>> getDepartments(){
        log.info("getDepartment : Start");
        List<Department> found = departmentService.getDepartments();
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> saveDepartments(@RequestParam("file") MultipartFile file){
        List<Department> inputDepartments = null;

        try(BufferedReader filereader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"))){
            inputDepartments = filereader.lines()
                    .map(this::stringToDepartment)
                    .collect(Collectors.toList());

            inputDepartments.stream()
                    .forEach(this::saveDepartment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("Departments are processed "+ inputDepartments.size(), HttpStatus.OK);
    }

    private Department stringToDepartment(String str){
        Department dep = new Department();
        String[] strValues = str.split(",");

        dep.setDepartmentName(strValues[0]);
        dep.setDepartmentAddress(strValues[1]);
        dep.setDepartmentCode(strValues[2]);
        dep.setInitiatives(strValues[3]);

        return dep;
    }

    @GetMapping("/WithLocale")
    public ResponseEntity<DisplayInfoVO> getDepartmentsWithLocale(@RequestHeader("Accept-Language") String language){
        log.info("getDepartmentsWithLocale with specific locale : Start");
        DisplayInfoVO found = departmentService.getDisplayMessageWithLocale(language);

        return new ResponseEntity<>(found, HttpStatus.OK);
    }

}
