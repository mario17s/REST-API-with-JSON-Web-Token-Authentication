package com.example.api.control;

import com.example.api.dto.JwtResponseDTO;
import com.example.api.dto.LoginDTO;
import com.example.api.dto.SignUpDTO;
import com.example.api.serv.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO loginDTO){
        JwtResponseDTO jwtReponseDTO = authenticationService.login(loginDTO);
        ResponseEntity re = new ResponseEntity(jwtReponseDTO, HttpStatus.OK);
        return re;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDTO signupDTO) throws Exception {
        Long userId = authenticationService.signup(signupDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully with id: "+userId);
    }
}
