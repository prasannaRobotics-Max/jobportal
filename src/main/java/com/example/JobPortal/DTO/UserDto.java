package com.example.JobPortal.DTO;

import com.example.JobPortal.Utils.AppUtils;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "UserName cannot be empty")
    private String userName;
    @NotBlank(message = "email cannot be empty")
    @Email(message = "Give me a valid Email")
    private String email;
    @NotNull(message = "contact Number cannot be null")
    @Digits(integer = 10, fraction = 0,message = "give a valid 10 digits contact number")
    private Long contactNumber;
    @NotNull(message = "role must be specified")
    private AppUtils.UserRoles role;

    private String companyName;//EMPLOYER

    private String location;//EMPLOYER
    @NotBlank(message = "password cannot be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",message = "Password must be at least 8 characters long and contain letters, digits, and special characters")
    private String password;

    private MultipartFile resume;// to receive inputs,JOBSEEKER
    private MultipartFile profile;// to receive inputs,JOBSEEKER

    private String resumeURL;//to set in response
    private String profileURL;// to set in response

    private List<EducationDto> educations;//JOBSEEKER

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUtils.UserRoles getRole() {
        return role;
    }

    public void setRole(AppUtils.UserRoles role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getResume() {
        return resume;
    }

    public void setResume(MultipartFile resume) {
        this.resume = resume;
    }

    public MultipartFile getProfile() {
        return profile;
    }

    public void setProfile(MultipartFile profile) {
        this.profile = profile;
    }

    public String getResumeURL() {
        return resumeURL;
    }

    public void setResumeURL(String resumeURL) {
        this.resumeURL = resumeURL;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public List<EducationDto> getEducations() {
        return educations;
    }

    public void setEducations(List<EducationDto> educations) {
        this.educations = educations;
    }
}

