package com.example.JobPortal.Service;

import com.example.JobPortal.DTO.JobsDto;
import com.example.JobPortal.Entity.JobsEntity;
import com.example.JobPortal.Exception.*;
import com.example.JobPortal.Repository.JobRepository;
import com.example.JobPortal.UpdateDto.JobsUpdateDto;
import com.example.JobPortal.Utils.FIleUploadUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.JobPortal.DTO.UserDto;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobsService implements JobsServiceInterface {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Override
    public JobsDto createJobs(JobsDto dto) throws IOException{
        JobsEntity jobsEntity=new JobsEntity();
        UserDto user=userService.findUserDetails(dto.getEmpId());
        if(user.getRole().toString().equals("EMPLOYER"))
        {
            jobsEntity.setEmpId(dto.getEmpId());
        }
        else{
            throw new RuntimeException("only Employer is allowed");
        }
        jobsEntity.setJobRole(dto.getJobRole());
        jobsEntity.setCompanyName(dto.getCompanyName());
        String url= FIleUploadUtils.saveFiles("logo/upload",dto.getCompanyLogo());
        jobsEntity.setCompanyProfile(url);
        jobsEntity.setDescription(dto.getDescription());
        jobsEntity.setSalary(dto.getSalary());
        jobsEntity.setCategory(dto.getCategory());
        jobsEntity.setLocation(dto.getLocation());
        jobsEntity.setDeadLine(dto.getDeadLine());
        JobsEntity savedJob=jobRepository.save(jobsEntity);
        return modelMapper.map(savedJob,JobsDto.class);
    }

    @Override
    public JobsDto findJobDetails(long id) {
        JobsEntity job=jobRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("JOB ID",id,"FindJobDetails","JOB_NOT_FOUND_WITH_ID"));
        return modelMapper.map(job, JobsDto.class);
    }

    @Override
    public List<JobsDto> findAllJobs() {
        List<JobsEntity> jobs= jobRepository.findAll();
        List<JobsDto> filteredJob=jobs.stream().map((job)->modelMapper.map(job,JobsDto.class)).toList();
        return filteredJob;
    }

    @Override
    public void deleteJob(long id) {
        JobsEntity jobId=jobRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("JOB ID",id,"DeleteJobDetails","JOB_NOT_FOUND_WITH_ID"));
        jobRepository.delete(jobId);
    }

    @Override
    public JobsUpdateDto updateJob(long id, JobsUpdateDto dto) throws IOException {
        JobsEntity jobsEntity=jobRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("JOB ID",id,"UpdateJobDetails","JOB_NOT_FOUND_WITH_ID"));

        if(dto.getEmpId()!=null) {
            jobsEntity.setEmpId(dto.getEmpId());
        }
        if(dto.getJobRole()!=null && !dto.getJobRole().isEmpty()) {
            jobsEntity.setJobRole(dto.getJobRole());
        }
        if(dto.getCategory()!=null && !dto.getCategory().isEmpty()) {
            jobsEntity.setCategory(dto.getCategory());
        }
        if(dto.getDescription()!=null &&!dto.getDescription().isEmpty()) {
            jobsEntity.setDescription(dto.getDescription());
        }
        if(dto.getCompanyName()!=null && !dto.getCompanyName().isEmpty()) {
            jobsEntity.setCompanyName(dto.getCompanyName());
        }
        if(dto.getCompanyLogo()!=null && !dto.getCompanyLogo().isEmpty()) {
            String url= FIleUploadUtils.saveFiles("logo/upload",dto.getCompanyLogo());
            jobsEntity.setCompanyProfile(url);
        }
        if(dto.getSalary()!=null) {
            jobsEntity.setSalary(dto.getSalary());
        }
        if(dto.getLocation()!=null && !dto.getLocation().isEmpty())
        {
            jobsEntity.setLocation(dto.getLocation());
        }
        if(dto.getDeadLine()!=null && !dto.getDeadLine().isEmpty()){
            jobsEntity.setDeadLine(dto.getDeadLine());
        }
               JobsEntity updatedJob=jobRepository.save(jobsEntity);
        return modelMapper.map(updatedJob,JobsUpdateDto.class);
    }

    @Override
    public List<JobsDto> findMatchingOnes(String name) {
        List<JobsEntity> matched=jobRepository.findByCompanyName(name).orElseThrow(()->new CompanyNotFoundException("CompanyName",name,"Find Matching company","COMPANY_NAME_NOT_FOUND"));
        List<JobsDto> filteredOne;
        if(matched.size()>0) {
            filteredOne = matched.stream().map((job) -> modelMapper.map(job, JobsDto.class)).toList();

            return filteredOne;
        }
        else{
            throw new RuntimeException("Company Not Found");
        }
    }

    @Override
    public List<JobsDto> findMatchingRoles(String role) {
        List<JobsEntity> matched=jobRepository.findByJobRole(role).orElseThrow(()->new JobRoleNotFoundException("JobRole",role,"matchingrole","JOB_ROLE_NOT_FOUND"));
        if(matched.size()>0) {
            List<JobsDto> filteredOne = matched.stream().map((job) -> modelMapper.map(job, JobsDto.class)).toList();

            return filteredOne;
        }
        else{
            throw new RuntimeException("There is no Roles");
        }
    }

    @Override
    public List<JobsDto> findMatchingLocations(String location) {
        List<JobsEntity> matched=jobRepository.findByLocation(location).orElseThrow(()->new CompanyLocationNotFoundException("Company Location",location,"matching location","COMPANY_LOCATION_NOT_FOUND"));
            List<JobsDto> filtered = matched.stream().map((job) -> modelMapper.map(job, JobsDto.class)).toList();
            return filtered;

    }

    @Override
    public List<JobsDto> findMatchingSalary(Long salary) {
        List<JobsEntity> matched=jobRepository.findBySalary(salary).orElseThrow(()->new SalaryNotFoundException("Salary",salary,"Matching Salary","SALARY_NOT_FOUND"));

            List<JobsDto> filtered = matched.stream().map((job) -> modelMapper.map(job, JobsDto.class)).toList();
            return filtered;


    }

    @Override
    public List<JobsDto> findMatchingJobs(String location, String role) {
        List<JobsEntity> matched=jobRepository.findByJobRoleAndLocation(location,role).orElseThrow(()->new MatchingJobsNotFoundException("JobsMatched",location,role,"Matching Jobs","JOBS_NOT_FOUND"));
        if(matched.size()>0) {
            List<JobsDto> filtered = matched.stream().map((job) -> modelMapper.map(job, JobsDto.class)).toList();
            return filtered;
        }
        else{
            throw new RuntimeException("There is No Job with the location and the role");
        }
    }

    @Override
    public List<JobsDto> findRelJobs(long id) {
        List<JobsEntity> jobs=jobRepository.findByEmpId(id).orElseThrow(()->new ResourceNotFoundException("employer ID",id,"find employer jobs","JOBS_NOT_FOUND"));
        List<JobsDto> relJobs=jobs.stream().map((job->modelMapper.map(job, JobsDto.class))).collect(Collectors.toList());
        return relJobs;
    }
}
