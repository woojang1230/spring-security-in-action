# 버전

- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 8.3 앤트 선택기로 권한을 부여할 요청 선택
```java
@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/hello")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/ciao", HttpMethod.GET.name())).authenticated()
                );
        return http.build();
    }
}
```

## MvcMatcher와의 차이점.
`AntMatchers`와 `MvcMatchers`의 차이는 경로를 인식하는 방식이다.

책의 저자는 기본적으로 `MvcMatchers`를 사용할 것을 권한다. 이유는 `MvcMatchers`가 URL을 인식하고 동작하는 방식이 스프링 프레임워크가 URL을 인식하고 동작하는 방식과 동일하기 때문이다. 즉, 서비스단에서 하나의 URL에 대해 동일한 동작을 할 것을 예상할 수 있다..

예를 들어 스프링 프레임워크에서는 `/hello`와 `/hello/`가 동일한 URL로 인식하고 동작한다. 하지만 `AntMatchers`에서는 두 URL이 다르게 인식되고 동작한다. 이렇게 URL을 해석하는 부분이 스프링과 다르게 동작하게 되면서 의도치 않은 취약점을 노출하게 되는 경우가 생길 수 있다. 그렇기 때문에 `AntMatchers`보다는 `MvcMatchers`를 사용할 것을 권장한다.
