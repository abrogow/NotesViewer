package com.interfaces

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

@TestConfiguration
class WebConfigTest {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        User adminActiveUser = new User("admin", "admin", [new SimpleGrantedAuthority("ADMIN")])

        return new InMemoryUserDetailsManager(Arrays.asList(
                adminActiveUser
        ))
    }
}
