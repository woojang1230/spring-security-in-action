# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 6장 실전: 작고 안전한 웹 애플리케이션

** JPA 구성과 같은 Security 설정과 직접적인 연관이 없는 내용은 제외한다.

## 1. 첫 진입점인 AuthenticationProvider 구현
```java
@Service
public class AuthenticationProviderService implements AuthenticationProvider {
    /**
     * 인증 작업의 시작점인 authenticate()함수.
     * 여기에 필요한 기타 클래스들을 순차적으로 만들어간다.
     * */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        ...
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```
`authenticate()` 함수 내부에서는 다음과 같은 작업이 기본적으로 일어난다.
1. 매개변수인 `authentication`으로 부터 `username`, `password`를 얻는다.
   - `authentication.getName()` : getName() 함수는 `Authentication`가 상속하는 `Principal` 인터페이스에 있는 함수다. 화면에서 입력한 사용자이름(username)을 반환한다.
   - `authentication.getCredentials().toString()` : 화면에서 입력한 비밀번호(password)를 반환한다.
2. `UserDetailsService` 구현체로부터 `username`을 기준으로 `UserDetails`를 얻는다.
   - `UserDetailsService` 구현체는 내부적으로 DB를 조회해서 `UserDetails`구현체를 반환하는 로직을 가진다.
3. 비밀번호를 비교하고 적절한 결과를 반환한다.

## 2. 사용자 정보를 조회
### UserDetails 구현체
`UserDetails` 구현체는 사용자 Entity를 속성으로 가지는 Wrapper 클래스로 작성한다.  

- [CustomUserDetails.java](src%2Fmain%2Fjava%2Fcom%2Fwoojang%2Fservice%2Fch06ex1%2Fmodel%2FCustomUserDetails.java)

조회된 User Entity는 Wrapper로 감싸진 상태로 전달된다.

### UserDetailsService 구현체
- [JpaUserDetailsService.java](src%2Fmain%2Fjava%2Fcom%2Fwoojang%2Fservice%2Fch06ex1%2Fservice%2FJpaUserDetailsService.java)

loadUserByUsername() 함수를 재정의 한다. 주의할 점은 반환타입을 `UserDetails`가 아니라 `CustomUserDetails`로 변경했다는 점이다. 이유는 반환 타입을 `CustomUserDetails`로 명시해줘야 이 메소드를 사용한 곳에서 `CustomUserDetails` 객체의 속성들(메소드)을 사용할 수 있기 때문이다.

구현체 내부는 여러 방식으로 구현될 수 있을것이다. 여기서는 `JpaRepository`를 상속받을 `UserRepository`를 통해서 사용자를 조회하고 그 결과에 따른 로직을 처리하고 있다. 

## 3. 비밀번호 검증
테이블에는 사용자의 비밀번호 암호화 알고리즘 종류를 가지고 있다. 사용자를 조회했다면 그 사용자의 알고리즘을 비교하고 해당 알고리즘의 `PasswordEncoder`를 사용하여 `matches()` 함수로 비밀번호를 비교한다.

## 4. AuthenticationProvider, PasswordEncoder 등록
이전까지는 실질적인 처리 로직을 다루었고, 그 로직이 수행될 수 있게 `spring`에 해당 클래스들을 등록한다.
- `AuthenticationProvider` 등록
    ```java
    @RequiredArgsConstructor
    @Configuration
    public class ProjectWebConfig extends WebSecurityConfigurerAdapter {
        // AuthenticationProvider 구현체를 주입받는다.
        private final AuthenticationProviderService authenticationProvider;
  
        // 주입받은 구현체를 authenticationProvider()에 등록한다.
        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(this.authenticationProvider);
        }
    
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            // 로그인 페이지 사용을 설정하고 모든 요청에 해대 인증 요구를 설정한다.
            http.formLogin()
                    .defaultSuccessUrl("/main", true);
            http.authorizeRequests()
                    .anyRequest().authenticated();
        }
    }
    ```
- `PasswordEncoder` 등록 : 이 예제에서는 BCrypt, SCrypt 인코더를 사용한다. 그렇기에 두가지 타입의 빈을 등록한다.
    ```java
    @Configuration
    public class ProjectConfig {
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    
        @Bean
        public SCryptPasswordEncoder sCryptPasswordEncoder() {
            return new SCryptPasswordEncoder();
        }
    }
    ```

> MySQL Docker 실행
> ```shell
> docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
> ```
