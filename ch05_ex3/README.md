# 버전

- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

# 5.3 HTTP Basic 인증과 양식 기반 로그인 인증 이해하기(1)
## 5.3.1 HTTP Basic 이용 구성

`HttpSecurity` 객체에서 `httpBasic()`를 실행하면 된다.

```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // 기본 설정
        http.httpBasic();
        
        // 구성 추가 설정
        http.httpBasic(configurer -> {
            // 요청 영역 이름 지정
            configurer.realmName("OTHER");
            // 요청 실패시 처리 방식 사용자화
            configurer.authenticationEntryPoint((request, response, authException) -> {
                response.addHeader("message", "Luke, I am your father!");
                response.sendError(HttpStatus.UNAUTHORIZED.value());
            });
        });
    }
}
```
