# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 7.1 권한과 역할에 따라 접근 제한(2)
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
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(request ->
                        request.anyRequest()
                                .access(new WebExpressionAuthorizationManager("hasAuthority('read') and !hasAuthority('delete')"))  // read권한을 가지고 delete 권한이 없는 사용자
                );
        return http.build();
    }
}
```
