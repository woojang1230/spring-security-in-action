# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 5.3 HTTP Basic 인증과 양식 기반 로그인 인증 이해하기(2)
## 5.3.2 양식 기반 로그인으로 인증 구현
### Login 화면 제공
1. 임의로 사용 될 페이지를 만든다. `resources/static`에 `home.html` 파일을 생성한다.
2. HttpSecurity 설정으로 Login From 사용을 설정한다.
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(auth -> auth
                      .anyRequest().authenticated()
              )
              .formLogin(configurer -> configurer                        // 로그인 페이지 사용 설정.
                      .defaultSuccessUrl("/home", true)                 // (추가 옵션) 인증 성공 시 이동할 기본 페이지 지정.
              );      
    }
}
```

### 로그인 성공/실패 핸들러 커스텀하기
위에서 `defaultSuccessUrl()`함수와 같이 로그인 처리 후 동작에 대해 사용자화 하려면 핸들러를 구현하고 `HttpSecurity`에 추가한다.
- 로그인 성공 핸들러 구현 클래스 생성
    ```java
    @Component
    class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, 
                                            final Authentication authentication) throws IOException, ServletException {
            // 로그인 성공 후 수행 될 로직.
            // 샘플에는 "read" 권한이 있을 경우에만 /home으로 리다이렉트 시키고 그 외 사용자는 /error로 리다이렉트 한다.
            authentication.getAuthorities()
                    .stream()
                    .filter(auth -> Objects.equals(auth.getAuthority(), "read"))
                    .findFirst()
                    .ifPresentOrElse(auth -> redirectPage(response, "/home"),
                            () -> redirectPage(response, "/error"));
        }
    
        private void redirectPage(final HttpServletResponse response, final String path) {
            try {
                response.sendRedirect(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    ````
- 로그인 실패 핸들러 구현 클래스 생성
    ```java
    @Component
    public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                            final AuthenticationException exception) throws IOException, ServletException {
            // 페이지 이동 없이 헤더에 특정 키와 값을 셋팅
            response.setHeader("failed", LocalDateTime.now().toString());
        }
    }
    ```
- 생성한 클래스를 HttpSecurity 객체에 설정
    ```java
    @Configuration
    public class ProjectConfig {
        private final AuthenticationSuccessHandler authenticationSuccessHandler;
        private final AuthenticationFailureHandler authenticationFailureHandler;
  
        public ProjectConfig(final AuthenticationSuccessHandler authenticationSuccessHandler,
                             final AuthenticationFailureHandler authenticationFailureHandler) {
            this.authenticationSuccessHandler = authenticationSuccessHandler;
            this.authenticationFailureHandler = authenticationFailureHandler;
        }
        
        @Bean
        public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(configurer -> configurer
                        .successHandler(this.authenticationSuccessHandler)
                        .failureHandler(this.authenticationFailureHandler)
                )
                .httpBasic(withDefaults());
            return http.build();
        }
    }
    ```
> ❗로그인 성공/실패 핸들러의 경우 로그인 인증이 끝난 뒤에 수행되는 로직이다. 로그인 인증이 성공했다면 실패 핸들러는 수행되지 않고, 반대로 인증에 실패한 경우에는 성공 핸들러가 수행되지 않는다.
