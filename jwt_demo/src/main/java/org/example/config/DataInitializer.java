package org.example.config;




import org.example.entity.Role;
import org.example.entity.User;
import org.example.dao.RoleRepository;
import org.example.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        Role adminRole = new Role();
        adminRole.setRoleName("管理员");
        adminRole.setRoleCode("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("普通用户");
        userRole.setRoleCode("ROLE_USER");
        roleRepository.save(userRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setEnabled(true);
        admin.getRoles().add(adminRole);
        userRepository.save(admin);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEnabled(true);
        user.getRoles().add(userRole);
        userRepository.save(user);
    }
}