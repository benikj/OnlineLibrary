package mk.finki.lm;

import mk.finki.lm.model.Role;
import mk.finki.lm.repository.jpa.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LmApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

@Component
class DemoCommandLineRunner implements CommandLineRunner{

    private final RoleRepository roleRepository;

    DemoCommandLineRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role role_ADMIN = new Role();
        role_ADMIN.setId(1L);
        role_ADMIN.setName("ROLE_ADMIN");
        roleRepository.save(role_ADMIN);

        Role role_USER = new Role();
        role_USER.setId(2L);
        role_USER.setName("ROLE_USER");
        roleRepository.save(role_USER);

    }
}
