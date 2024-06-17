# 버전

- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 8.4 정규식 선택기로 권한을 부여할 요청 선택
```java
@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new RegexRequestMatcher(".*/(us|uk|ca)+/(en|fr).*", HttpMethod.GET.name())).authenticated()
                        .anyRequest().hasAuthority("premium")
                );
        return http.build();
    }
}
```

> ❗정규식 선택기는 최후의 방법으로만 사용하자. 다양한 케이스에 대한 대응은 가능하지만 직관적이지 않아 읽기가 어렵다. 따라서 MVC 또는 앤트 선택기를 우선적으로 사용하고 다른 대안이 없을때에 정규식 선택기를 고려한다.
