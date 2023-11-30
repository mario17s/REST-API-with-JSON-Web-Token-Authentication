package com.example.api.utils;

import com.example.api.model.ERole;
import com.example.api.model.Role;
import com.example.api.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Startup implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        Role optRoleAd = null;
        for(Role r: roleRepository.findAll())
            if(r.getRoleName() == ERole.ADMIN)
                optRoleAd = r;
        if(optRoleAd == null){
            Role re = new Role();
            re.setRoleName(ERole.ADMIN);
            roleRepository.save(re);
        }
        optRoleAd = null;
        for(Role r: roleRepository.findAll())
            if(r.getRoleName() == ERole.USER)
                optRoleAd = r;
        if(optRoleAd == null){
            Role re = new Role();
            re.setRoleName(ERole.USER);
            roleRepository.save(re);
        }
    }
}
