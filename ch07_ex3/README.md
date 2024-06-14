# 버전

- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

# 7.1 권한과 역할에 따라 접근 제한(3)
## 역할 기준 엔트포인트 접근 제한
### 역할 부여
```java
@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        final UserDetails user1 = User.withUsername("john")
                .password("12345")
                .roles("ADMIN")             // .authorities("ROLE_ADMIN")
                .build();
        final UserDetails user2 = User.withUsername("jane")
                .password("12345")
                .roles("MANAGER")           // .authorities("ROLE_MANAGER")
                .build();
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
    }

    ...
}
```
- 역할의 경우 `User` 클래스의 빌더를 사용한다면 `roles()` 함수로 역할을 지정한다. `authorities()` 함수를 사용하게 된다면 역할 앞에 Prefix로 `ROLE_`을 붙여야한다.

### 역할 검증
```java
@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .anyRequest()
                .hasRole("MANAGER");     // 특정 역할만 허용.
    }
}
```

## 모든 엔트포인트에 대한 접근 제한
아래 코드로 모든 요청에 대한 전체 접근 제어를 할 수 있다.
```java
@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests().anyRequest()
                .denyAll();         // 모든 요청에 대해 거절
        http.authorizeRequests().anyRequest()
                .permitAll();       // 모든 요청에 대해 허용
    }
}
```
- `denyAll()`이나 `permitAll()` 함수의 경우 요청의 필터해서 일괄적으로 요청 수락/거절을 수행할 수 있다.
