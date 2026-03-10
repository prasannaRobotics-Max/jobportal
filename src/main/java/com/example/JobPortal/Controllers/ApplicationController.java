package com.example.JobPortal.Controllers;

import com.example.JobPortal.DTO.ApplicationDto;
import com.example.JobPortal.DTO.ApplicationResponseDto;
import com.example.JobPortal.Service.ApplicationService;
import com.example.JobPortal.UpdateDto.ApplicationUpdateDto;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/add")
    public ResponseEntity<ApplicationDto> createApplication(@RequestBody @Valid ApplicationDto dto){
        ApplicationDto savedApplication=applicationService.addApplication(dto);
        return new ResponseEntity<>(savedApplication, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getAll(){
        List<ApplicationDto> applications=applicationService.findAll();
        return new ResponseEntity<>(applications,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDto> getById(@PathVariable long id)
    {
        ApplicationDto application=applicationService.findApplicationId(id);
        return new ResponseEntity<>(application,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id)  {
       applicationService.DeleteApplication(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApplicationUpdateDto> changeApplication(@PathVariable long id, @RequestBody @Valid ApplicationUpdateDto dto){
        ApplicationUpdateDto updated=applicationService.update(id,dto);
        return new ResponseEntity<>(updated,HttpStatus.OK);
    }
@GetMapping("/jobSeeker/{id}")
    public ResponseEntity<List<ApplicationResponseDto>> findSeekersJob(@PathVariable  long id)
    {
        List<ApplicationResponseDto> jobSeeker=applicationService.jobSeeker( id);
        return new ResponseEntity<>(jobSeeker,HttpStatus.OK);
    }
    @GetMapping("/employer/{id}")
    public ResponseEntity<List<ApplicationResponseDto>> findEmployerJob(@PathVariable long id)
    {
        List<ApplicationResponseDto> employer=applicationService.employer(id);
        return new ResponseEntity<>(employer,HttpStatus.OK);
    }
}
