# 버전

- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

# 8.4 정규식 선택기로 권한을 부여할 요청 선택 

- regexMatchers(HttpMethod method, String... regexPatterns) : 제한을 적용할 HTTP 방식과 경로 모두 설정.
- regexMatchers(String... regexPatterns) : 제한을 적용할 경로 설정.

```java
@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*")
                .authenticated()
                .anyRequest()
                .hasAuthority("premium");
    }
}
```

> ❗정규식 선택기는 최후의 방법으로만 사용하자. 다양한 케이스에 대한 대응은 가능하지만 직관적이지 않아 읽기가 어렵다. 따라서 MVC 또는 앤트 선택기를 우선적으로 사용하고 다른 대안이 없을때에 정규식 선택기를 고려한다.
