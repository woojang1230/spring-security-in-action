# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

## UserDetailsService 인터페이스 구현
```java
public class InMemoryUserDetailsService implements UserDetailsService {

    private final List<UserDetails> users;

    public InMemoryUserDetailsService(List<UserDetails> users) {
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // 사용자를 찾는 로직
        return users.stream()
                .filter(u -> Objects.equals(u.getUsername(), username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

```

그리고 위 객체를 빈으로 등록하는 로직 추가

```java
@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails user = new User("Jang", "12345", "read");
        return new InMemoryUserDetailsService(List.of(user));
    }

    ...
}
```

여기서 User 객체는 UserDetails 인터페이스의 구현체.

```java
public class User implements UserDetails {
    private final String username;
    private final String password;
    private final String authority;

    public User(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    ...
}
```
