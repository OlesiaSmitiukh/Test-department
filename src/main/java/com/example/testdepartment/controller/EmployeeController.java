package com.example.testdepartment.controller;

import com.example.testdepartment.model.Employee;
import com.example.testdepartment.model.dto.EmployeeRequestDto;
import com.example.testdepartment.model.dto.EmployeeResponseDto;
import com.example.testdepartment.service.EmployeeService;
import com.example.testdepartment.service.mapper.EmployeeMapper;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    @ApiOperation(value = " Adds a new employee ")
    public EmployeeResponseDto add(@RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeMapper.mapToModel(employeeRequestDto);
        return employeeMapper.mapToDto(employeeService.save(employee));
    }

    @PostMapping("/search")
    @ApiOperation(value = " Finds the employees by name/part of the name ")
    public List<EmployeeResponseDto> search(@RequestBody String search) {
        return employeeService.findBySearch(search).stream()
                .map(employeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = " Finds the employee by id ")
    public EmployeeResponseDto get(@PathVariable Long id) {
        Employee employee = employeeService.get(id);
        return employeeMapper.mapToDto(employee);
    }

    @GetMapping
    @ApiOperation(value = " Returns a list of all employees ")
    public List<EmployeeResponseDto> getAll() {
        return employeeService.getAll().stream()
                .map(employeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}/edit")
    @ApiOperation(value = " Edits employees with the specified id ")
    public EmployeeResponseDto update(@PathVariable Long id,
                                      @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeService.update(id,
                employeeMapper.mapToModel(employeeRequestDto));
        return employeeMapper.mapToDto(employee);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = " Makes employees with the specified id not active ")
    public List<EmployeeResponseDto> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return employeeService.getAll().stream()
                .map(employeeMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
