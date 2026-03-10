package com.example.JobPortal.Service;

import com.example.JobPortal.DTO.ApplicationDto;
import com.example.JobPortal.DTO.ApplicationResponseDto;
import com.example.JobPortal.DTO.UserDto;
import com.example.JobPortal.Entity.ApplicationEntity;
import com.example.JobPortal.Entity.JobsEntity;
import com.example.JobPortal.Entity.UserEntity;
import com.example.JobPortal.Exception.ResourceNotFoundException;
import com.example.JobPortal.Repository.ApplicationRepository;
import com.example.JobPortal.Repository.JobRepository;
import com.example.JobPortal.UpdateDto.ApplicationUpdateDto;
import com.example.JobPortal.Utils.AppUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ApplicationService implements  ApplicationServiceInterface{
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private SmsService smsService;
    @Override
    public ApplicationDto addApplication(ApplicationDto dto)
    {
        ApplicationEntity applicationEntity=new ApplicationEntity();
        UserDto jobSeeker=userService.findUserDetails(dto.getJobSeekerId());
        if(jobSeeker==null)
        {
            throw new RuntimeException("Jobseeker Details Not Found");
        }
        if(jobSeeker.getRole()== AppUtils.UserRoles.EMPLOYER)
        {
            throw new RuntimeException("Only JobSeeker is allowed");
        }
        applicationEntity.setEmpId(dto.getEmpId());
        applicationEntity.setStatus(AppUtils.status.PENDING);
        applicationEntity.setJobSeekerId(dto.getJobSeekerId());
        applicationEntity.setResume(jobSeeker.getResumeURL());
        JobsEntity job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job ID",dto.getJobId(),"AddApplication","JOB_NOT_FOUND"));
        applicationEntity.setJobId(job);
        ApplicationEntity saved=applicationRepository.save(applicationEntity);
        return modelMapper.map(saved,ApplicationDto.class);
    }

    @Override
    public ApplicationUpdateDto update(long id, ApplicationUpdateDto dto) {
        ApplicationEntity user=applicationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Application ID",id,"Updating Application","APPLICATION_NOT_FOUND"));
        UserDto employer=userService.findUserDetails(user.getEmpId());
        if (employer.getRole() == AppUtils.UserRoles.EMPLOYER) {
            if (dto.getStatus() == AppUtils.status.APPROVED) {
                user.setStatus(AppUtils.status.APPROVED);
                UserDto userDet=userService.findUserDetails(user.getJobSeekerId());
                try {
                    smsService.sendSms("+91" + userDet.getContactNumber().toString(), "Congratulation Your Application Is approved on the NextStep portal .Kindly Check the status and further details will be followed by the company");
                }
                catch(Exception err)
                {
                    System.out.println("SMS failed"+err.getMessage());


                }
            }
            if (dto.getStatus() == AppUtils.status.REJECTED) {
                user.setStatus(AppUtils.status.REJECTED);
                UserDto userDet=userService.findUserDetails(user.getJobSeekerId());
                smsService.sendSms("+91"+userDet.getContactNumber().toString(),"Your Application is Rejected.All the Best for your next Job");
            }
        }
        else{
            throw new RuntimeException("UNAUTHORIZED");
        }
        ApplicationEntity updatedUser=applicationRepository.save(user);
        return modelMapper.map(updatedUser,ApplicationUpdateDto.class);
    }

    @Override
    public List<ApplicationDto> findAll() {
        List<ApplicationEntity> applications=applicationRepository.findAll();
        List<ApplicationDto> applicationList= applications.stream().map((application)->modelMapper.map(application,ApplicationDto.class)).toList();
        return applicationList;
    }

    @Override
    public ApplicationDto findApplicationId(long id) {
        ApplicationEntity applicant=applicationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Application ID",id,"findApplication","APPLICATION_NOT_FOUND"));
        return modelMapper.map(applicant,ApplicationDto.class);
    }

    @Override
    public void DeleteApplication(long id) {
        ApplicationEntity applicant=applicationRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Application ID",id,"Delete Application","APPLICATION_NOT_FOUND"));
        UserDto employer=userService.findUserDetails(applicant.getEmpId());
        if(employer.getRole()== AppUtils.UserRoles.JOBSEEKER)
        {
            throw new RuntimeException("UnAuthorized");
        }
        else {
            applicationRepository.delete(applicant);
        }
    }

    @Override
    public List<ApplicationResponseDto> jobSeeker(long id) {
        UserDto role=userService.findUserDetails(id);
        if(role==null)
        {
            throw new ResourceNotFoundException("Jobseeker",id,"Finding JobSeeker Application","USER_NOT_FOUND");
        }
        List<ApplicationEntity> filtered=applicationRepository.findByJobSeekerId(id);
        return filtered.stream().map(app -> {
            long jobId=app.getJobId().getId();
            JobsEntity job = jobRepository.findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("Job ID",jobId,"Finding JobSeeker Applications","JOB_NOT_FOUND"));

            ApplicationResponseDto dto = new ApplicationResponseDto();
            dto.setCompanyName(job.getCompanyName());
            dto.setRole(job.getJobRole());
            dto.setStatus(app.getStatus().toString());

            return dto;

        }).toList();

    }

   @Override
  public List<ApplicationResponseDto> employer(long id) {
       UserDto role=userService.findUserDetails(id);
        if(role==null)
        {
            throw new ResourceNotFoundException("Employer",id,"employerApplication","EMPLOYER_NOT_FOUND");
        }
        List<ApplicationEntity> filtered=applicationRepository.findByEmpId(id);
        return filtered.stream().map(app->{
ApplicationResponseDto response=new ApplicationResponseDto();
JobsEntity job=app.getJobId();
response.setId(app.getId());
response.setRole(job.getJobRole());
response.setResume(app.getResume());
response.setStatus(app.getStatus().toString());
        return response;}).toList();


    }
}