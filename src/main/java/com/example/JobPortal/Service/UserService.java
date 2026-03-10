package com.example.JobPortal.Service;


import com.example.JobPortal.DTO.AuthenticateDto;
import com.example.JobPortal.DTO.AuthenticateResponseDto;
import com.example.JobPortal.DTO.EducationDto;
import com.example.JobPortal.DTO.UserDto;
import com.example.JobPortal.Entity.EducationEntity;
import com.example.JobPortal.Entity.UserEntity;
import com.example.JobPortal.Exception.EmailNotFoundException;
import com.example.JobPortal.Exception.ResourceNotFoundException;
import com.example.JobPortal.Repository.UserRepository;
import com.example.JobPortal.UpdateDto.UserUpdateDto;
import com.example.JobPortal.Utils.FIleUploadUtils;
import com.example.JobPortal.Utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;
    private  final UserRepository userRepository;


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserService(PasswordEncoder passwordEncoder, ModelMapper modelMapper, UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public UserDto createUser(UserDto dto, MultipartFile resume, MultipartFile profile) throws IOException {
        UserEntity userEntity=new UserEntity();
        if(resume!=null && !resume.isEmpty()) {
            String path = FIleUploadUtils.saveFiles("files/resume", resume);
            userEntity.setResumeURL(path);
        }
        userEntity.setUserName(dto.getUserName());
        userEntity.setEmail(dto.getEmail());
        userEntity.setLocation(dto.getLocation());
        userEntity.setRole(dto.getRole());
        userEntity.setContactNumber(dto.getContactNumber());
        userEntity.setCompanyName(dto.getCompanyName());
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        if(profile!=null && !profile.isEmpty()) {
            String profilePath = FIleUploadUtils.saveFiles("files/profile", profile);
            userEntity.setProfileURL(profilePath);
        }
       List<EducationEntity> educationDetails=new ArrayList<>();
        if(dto.getEducations()!=null) {
            for (EducationDto dtos : dto.getEducations()) {
                EducationEntity entity = new EducationEntity();
                entity.setName(dtos.getName());
                entity.setLevelOfStudy(dtos.getLevelOfStudy());
                entity.setTotalMarks(dtos.getTotalMarks());
                entity.setYearOfCompletion(dtos.getYearOfCompletion());
                entity.setUser(userEntity);
                educationDetails.add(entity);
            }
        }
         userEntity.setEducations(educationDetails);
        UserEntity savedUser=userRepository.save(userEntity);
        return modelMapper.map(savedUser,UserDto.class);
    }

    public void deleteUser(long id){
        UserEntity user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User ID",id,"DeleteUser","USER_NOT_FOUND_WITH_ID"));
        userRepository.delete(user);
    }

    public List<UserDto> findAllUsers(){
       List<UserEntity> users=userRepository.findAll();
       List<UserDto> allUsers=users.stream().map(user->modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

       return allUsers;
    }

    public UserDto findUserDetails( long id){
        UserEntity user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User ID",id,"FindUserDetails","USER_NOT_FOUND_WITH_ID"));
        return modelMapper.map(user,UserDto.class);
    }
    public UserUpdateDto updateUser(long id, UserUpdateDto dto) throws IOException {
       UserEntity userEntity=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User ID",id,"UpdateUserDetails","USER_NOT_FOUND_WITH_ID") );
       if(dto.getResume()!=null && !dto.getResume().isEmpty()) {
           String filePath=FIleUploadUtils.saveFiles("file/resume",dto.getResume());
           userEntity.setResumeURL(filePath);
       }
       if(dto.getProfile()!=null && !dto.getProfile().isEmpty()){
           String profilePath=FIleUploadUtils.saveFiles("file/profile",dto.getProfile());
           userEntity.setProfileURL(profilePath);
       }
       if(dto.getUserName()!=null && !dto.getUserName().isEmpty()) {
           userEntity.setUserName(dto.getUserName());
       }
       if(dto.getRole()!=null) {
           userEntity.setRole(dto.getRole());
       }
       if(dto.getLocation()!=null && !dto.getLocation().isEmpty()) {
           userEntity.setLocation(dto.getLocation());
       }
       if(dto.getPassword()!=null) {
           userEntity.setPassword(dto.getPassword());
       }
       if(dto.getContactNumber()!=null) {
           userEntity.setContactNumber(dto.getContactNumber());
       }
       if(dto.getCompanyName()!=null && !dto.getCompanyName().isEmpty()) {
           userEntity.setCompanyName(dto.getCompanyName());
       }
       if(dto.getEmail()!=null && !dto.getEmail().isEmpty()) {
           userEntity.setEmail(dto.getEmail());
       }
       UserEntity updatedUser=userRepository.save(userEntity);
       return modelMapper.map(updatedUser,UserUpdateDto.class);
    }
    public AuthenticateResponseDto authUser(AuthenticateDto dto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
        );


        UserEntity user = userRepository.findByEmail(
                dto.getEmail()
        ).orElseThrow(() -> new EmailNotFoundException("Email",dto.getEmail(),"UserService","USER_NOT_FOUND_WITH_GIVEN_EMAIL"));


        String token = jwtUtil.generateToken(user.getEmail());


        AuthenticateResponseDto response = new AuthenticateResponseDto();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setRole(user.getRole().toString());
        response.setEmail(user.getEmail());

        return response;
    }
}
