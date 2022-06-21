package com.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;

import java.util.List;

import static com.blog.config.AppConstants.ADMIN_USER;
import static com.blog.config.AppConstants.NORMAL_USER;;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApisApplication.class, args);
    }

    @Bean
    public ModelMapper modeMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println(this.passwordEncoder.encode("Ravi@223"));
        try {
            Role role = new Role();
            role.setId(ADMIN_USER);
            role.setName("ROLE_ADMIN");

            Role role1 = new Role();
            role1.setId(NORMAL_USER);
            role1.setName("ROLE_USER");

            List<Role> roles = List.of(role, role1);

            List<Role> result = this.roleRepo.saveAll(roles);

            result.forEach(r -> {
                System.out.println(r.getName());
            });

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
