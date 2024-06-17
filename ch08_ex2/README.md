# 버전

- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 8.2 MVC 선택기로 권한을 부여할 요청 선택

- 객체 생성 : MvcRequestMatcher(HandlerMappingIntrospector introspector, String pattern) 
- HTTP 메소드 설정 : 생성된 객체에서 `setMethod()` 메소드로 설정한다. 변수를 따로 만들어야 하는 부분이 단점이다.

> Spring Boot Security에서는 CSRF(Cross-Site Request Forgery) 보호를 기본적으로 활성화하여, POST, PUT, DELETE와 같은 상태 변화를 일으킬 수 있는 HTTP
> 메서드에 대한 요청을 차단한다. 그래서 테스트를 위해서 CSRF를 잠시 비활성화한다.

```java

@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        http.httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(makeMvcMatcher(introspector, "/a", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/a", HttpMethod.POST)).permitAll()
                        .requestMatchers(makeMvcMatcher(introspector, "/a/b/**", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/product/{code:^[0-9]*$}", HttpMethod.GET)).authenticated()
                        .requestMatchers(makeMvcMatcher(introspector, "/code/{name}", HttpMethod.GET)).permitAll()
                        .anyRequest().permitAll());
        return http.build();
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

