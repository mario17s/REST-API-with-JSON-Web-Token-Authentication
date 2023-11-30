package com.example.api.serv;

import com.example.api.dto.JwtResponseDTO;
import com.example.api.dto.LoginDTO;
import com.example.api.dto.SignUpDTO;
import com.example.api.model.ERole;
import com.example.api.model.Role;
import com.example.api.model.User;
import com.example.api.repo.RoleRepository;
import com.example.api.repo.UserRepository;
import com.example.api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponseDTO login(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(role -> role.getAuthority()).collect(Collectors.toList());
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        jwtResponseDTO.setId(userDetails.getId());
        jwtResponseDTO.setToken(jwt);
        jwtResponseDTO.setRoles(roles);
        return jwtResponseDTO;
    }

    public Long signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
       /* for(User u: userRepository.findAll())
            if(u.getEmail().equals(signUpDTO.getEmail()))
                throw new Exception("email exists");*/
        String hashedPassword = passwordEncoder.encode(signUpDTO.getPassword());
        Set<Role> roles = new HashSet<>();
        Role optRole = null;
        if(signUpDTO.getRole() != null && signUpDTO.getRole().equals("ADMIN")){
            for(Role r: roleRepository.findAll())
                if(r.getRoleName() == ERole.ADMIN)
                {
                    optRole = r;
                    break;
                }
        }else if(signUpDTO.getRole() != null && signUpDTO.getRole().equals("USER")){
            for(Role r: roleRepository.findAll())
                if(r.getRoleName() == ERole.USER)
                {
                    optRole = r;
                    break;
                }
        }
        roles.add(optRole);
        User uu = new User();
        uu.setEmail(signUpDTO.getEmail());
        uu.setPassword(signUpDTO.getPassword());
        uu.setRoles(roles);
        User us = userRepository.save(uu);
        return us.getId();
    }
}
