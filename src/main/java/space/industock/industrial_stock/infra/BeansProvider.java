package space.industock.industrial_stock.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyAuthoritiesMapper;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.DateTimeFormatter;

@Configuration
@EnableMethodSecurity
public class BeansProvider {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ObjectMapper objectMapper(){
    ObjectMapper objectMapper = new ObjectMapper();
    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(java.time.LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.registerModule(javaTimeModule);
    return objectMapper;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder encoder, RoleHierarchy hierarchy){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(encoder);
    provider.setUserDetailsService(userDetailsService);
    provider.setAuthoritiesMapper(new RoleHierarchyAuthoritiesMapper(hierarchy));
    return provider;
  }

  @Bean
  public RoleHierarchy roleHierarchy(){
    RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

    hierarchy.setHierarchy(
        "ROLE_OWNER > ROLE_DELIVER\n" +
        "ROLE_OWNER > ROLE_MANAGER\n" +
        "ROLE_MANAGER > ROLE_WORKER"
    );
    return hierarchy;
  }
}
