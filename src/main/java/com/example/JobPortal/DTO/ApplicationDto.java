package com.example.JobPortal.DTO;

import com.example.JobPortal.Entity.JobsEntity;
import com.example.JobPortal.Utils.AppUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ApplicationDto {
    private Long id;

    @NotNull(message = "Jobseeker ID is must for applying")
     private Long jobSeekerId;

     private String resume;// internally fetched
     private AppUtils.status status;//internally set
    @NotNull(message = "Job Id is must for application")
    private Long jobId;
    @NotNull(message = "empId is must")
    private Long empId;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Long jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public AppUtils.status getStatus() {
        return status;
    }

    public void setStatus(AppUtils.status status) {
        this.status = status;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
