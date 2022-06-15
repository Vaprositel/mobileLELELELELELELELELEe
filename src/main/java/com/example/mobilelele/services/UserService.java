package com.example.mobilelele.services;

import com.example.mobilelele.models.dtos.UserDTOs.UserLoginDTO;
import com.example.mobilelele.models.dtos.UserDTOs.UserRegisterDTO;
import com.example.mobilelele.models.entities.RoleEntity;
import com.example.mobilelele.models.entities.UserEntity;
import com.example.mobilelele.models.sessions.UserSession;
import com.example.mobilelele.repositories.RoleRepository;
import com.example.mobilelele.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, UserSession userSession) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.userSession = userSession;
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {
        UserEntity createdUser = this.modelMapper.map(userRegisterDTO, UserEntity.class);
        RoleEntity userRole = this.roleRepository.findById(1L).get();
        createdUser.setRole(userRole);

        this.userRepository.save(createdUser);
    }

    public void userLogin(UserLoginDTO userLoginDTO) {
        UserEntity user = this.userRepository
                .findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword())
                .orElse(null);

        userSession.setLoggedIn(true);
        userSession.setUsername(user.getUsername());
    }

    public void logout() {
        this.userSession.clear();
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}