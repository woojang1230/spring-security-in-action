# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 8.1 선택기 메서드로 엔드포인트 선택
- 엔트포인트 별 권한 선택
## MVC Matcher
```java
@Configuration
public class ProjectWebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http, final HandlerMappingIntrospector introspector) throws Exception {
        http.httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new MvcRequestMatcher(introspector, "/hello")).hasRole("ADMIN")
                        .requestMatchers(new MvcRequestMatcher(introspector, "/ciao")).hasRole("MANAGER")
                        .anyRequest().permitAll()
                );
        return http.build();
    }
}
```

> ❗SpringBoot 2.3.0에서 사용되던 `mvcMatchers()`함수는 `requestMatchers()`로 통합되었고, 인자로 `MvcRequestMatcher` 객체를 만들어 전달하는 형식으로 사용할 수 있다. 
> 
> 문자를 인자로 전달할 수도 있지만, 명시적인 선택기 사용을 위해서 객체를 생성 주입하는 방법으로 예제를 진행한다.
> 
> ❗`MvcRequestMatcher`타입의 객체를 만들기 위해서는 `HandlerMappingIntrospector` 객체가 필요하다. 이 부분은 스프링이 이미 등록해놓은 빈을 주입받을 수 있기에 등록된 빈을 사용한다.


기존에 `anyRequest()` 함수가 사용되던 구간에 `requestMatchers()`라는 함수와 `MvcRequestMatcher`객체가 등장한다. 이 함수는 특정 엔드포인트를 특정 권한으로 묶을 수 있다.

위 코드로 예를 들자면 
- `requestMatchers(new MvcRequestMatcher(introspector, "/hello")).hasRole("ADMIN")` : `/hello` URL은 `ADMIN` 역할을 가진 사용자만 접근 가능
- `requestMatchers(new MvcRequestMatcher(introspector, "/ciao")).hasRole("MANAGER")` : `/ciao` URL은 `MANAGER` 역할을 가진 사용자만 접근 가능
- `anyRequest().permitAll()` : 기타 모든 요청은 누구에게나 허용.
> 여기서 잠깐! 선택기 다음에 오는 권한에 대해 살펴본다.
> 
> 권한 부여는 크게 `permitAll()`, `denyAll()`, `authenticated()` 이 3가지 방법이 있을것 같다. 이들은 동작의 차이점을 살펴보자.
> 1. `permitAll()` : 이 권한은 아예 아무런 선택기와 권한을 주지 않았을때와 똑같이 동작한다. 모든 요청이라고 해서 <u>잘못된 사용자 정보(**인증 실패**)</u>까지 허용하지는 않는다. 즉, <u>사용자 정보 없이(**미인증**)</u> 요청하거나 <u>정확한 사용자 정보를 추가</u>하여 요청한 건만 허용된다.
> 2. `denyAll()` : 모든 요청을 다 막는다.
> 3. `authenticated()` : <u>정확한 사용자 정보</u>만 허용된다.
