# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 5.3 HTTP Basic 인증과 양식 기반 로그인 인증 이해하기(1)
## 5.3.1 HTTP Basic 이용 구성

`HttpSecurity` 객체에서 `httpBasic()`를 실행하면 된다.

```java
@Configuration
public class ProjectConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                .httpBasic(withDefaults())          // 기본 Basic 설정인 경우
                
                .httpBasic(configurer -> {          // 사용자화
                    configurer.realmName("OTHER");                                                  // 영역 지정
                    configurer.authenticationEntryPoint((request, response, authException) -> {     // 인증 실패 응답 사용자화
                        response.addHeader("message", "Luke, I am your father!");
                        response.sendError(HttpStatus.UNAUTHORIZED.value());
                    });
                });
        return http.build();
    }
}
```
