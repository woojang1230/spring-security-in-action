# 버전

- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

# 7.1 권한과 역할에 따라 접근 제한(1)
### 권한 부여
```java
@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        final UserDetails user1 = User.withUsername("john")
                .password("12345")
                .authorities("READ")
                .build();
        final UserDetails user2 = User.withUsername("jane")
                .password("12345")
                .authorities("WRITE")
                .build();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
    }

    ...
}
```

### 권한 검증
```java
@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        // http.authorizeRequests().anyRequest().hasAuthority("READ");              // hasAuthority - READ 단일 권한 검증
        // http.authorizeRequests().anyRequest().hasAuthority("WRITE");             // hasAuthority - WRITE 단일 권한 검증
        http.authorizeRequests().anyRequest().hasAnyAuthority("READ", "WRITE");     // hasAnyAuthority - 복수 권한 검증
    }
}
```
