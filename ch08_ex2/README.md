# 버전

- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

# 8.2 MVC 선택기로 권한을 부여할 요청 선택

- mvcMatchers(HttpMethos method, String... patterns) : 제한을 적용할 HTTP 방식과 경로 모두 설정.
- mvcMatchers(String... patterns) : 제한을 적용할 경로 설정.

> Spring Boot Security에서는 CSRF(Cross-Site Request Forgery) 보호를 기본적으로 활성화하여, POST, PUT, DELETE와 같은 상태 변화를 일으킬 수 있는 HTTP 메서드에 대한 요청을 차단한다. 그래서 테스트를 위해서 CSRF를 잠시 비활성화한다.

```java
@Configuration
public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()
                .mvcMatchers(HttpMethod.GET, "/a/b/**").authenticated()
                .mvcMatchers(HttpMethod.GET, "/product/{code:^[0-9]*$}").permitAll()
                .anyRequest().denyAll();
        http.csrf().disable();
    }
}
```

## MvcMatcher 경로 문법

| 식                | 설명                                                                  |
|------------------|---------------------------------------------------------------------|
| /a               | /a 경로만                                                              |
| /a/*             | * 연산자는 한 경로 이름만 대체한다. 이 경우 /a/b 또는 /a/c와는 일치하지만, /a/b/c와는 일치하지 않는다. |
| /a/**            | ** 연산자는 여러 경로 이름을 대체한다. 이 경우 /a, /a/b, /a/b/c가 모두 이 식과 일치한다.        |
| /a/{param}       | 이 식은 주어진 경로 매개 변수를 포함한 /a 경로에 적용된다.                                 |
| /a/{param:regex} | 이 식은 매개 변수 값과 주어진 정규식이 일치할 때만 주어진 경로 매개 변수를 포함한 /a 경로에 적용된다.        |

