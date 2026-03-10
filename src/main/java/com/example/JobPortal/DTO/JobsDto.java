package com.example.JobPortal.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobsDto {

   private Long id;
   @NotNull(message = "empId cannot be null")
   private Long empId;
   @NotBlank(message = "companyName cannot be blank")
   private String companyName;

   private MultipartFile companyLogo;

   private String companyProfile;// used in response
   @NotBlank(message = "jobRole cannot be blank")
   private String jobRole;
   @NotBlank(message = "description cannot be blank")
   private String description;
   @NotNull(message = "salary cannot be null")
   private Long salary;
   @NotBlank(message = "category cannot be blank")
   private String category;
   @NotBlank(message = "location cannot be blank")
   private String location;
   @NotBlank(message = "deadline cannot be blank")
   private String deadLine;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getEmpId() {
      return empId;
   }

   public void setEmpId(Long empId) {
      this.empId = empId;
   }

   public String getCompanyName() {
      return companyName;
   }

   public void setCompanyName(String companyName) {
      this.companyName = companyName;
   }

   public MultipartFile getCompanyLogo() {
      return companyLogo;
   }

   public void setCompanyLogo(MultipartFile companyLogo) {
      this.companyLogo = companyLogo;
   }

   public String getJobRole() {
      return jobRole;
   }

   public void setJobRole(String jobRole) {
      this.jobRole = jobRole;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Long getSalary() {
      return salary;
   }

   public void setSalary(Long salary) {
      this.salary = salary;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getDeadLine() {
      return deadLine;
   }

   public void setDeadLine(String deadLine) {
      this.deadLine = deadLine;
   }

   public String getCompanyProfile() {
      return companyProfile;
   }

   public void setCompanyProfile(String companyProfile) {
      this.companyProfile = companyProfile;
   }
}
