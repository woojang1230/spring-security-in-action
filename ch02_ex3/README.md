# 버전
- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

## 다른 방법으로 인스턴스 재정의
### configure(final AuthenticationManagerBuilder auth) 오버라이딩
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        final var userDetailService = new InMemoryUserDetailsManager();
        final var user = User.withUsername("Jang")
                .password("12345")
                .authorities("read")
                .build();
        userDetailService.createUser(user);
        
        auth.userDetailsService(userDetailService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    ...
}
```
- AuthenticationManagerBuilder 객체에 직접 UserDetailsService, PasswordEncoder 객체를 주입한다.

> ❗주의사항으로는 configure를 재정의하는 방법과 Bean을 등록하는 방법을 혼용해서 사용하면 안된다. 유지보수 측면에서 좋지 않다.
> 
> 설정 방법은 한가지로 통일시키는것이 좋고, AuthenticationManagerBuilder에서 직접 메소드 체이닝으로 UserDetailsService 객체를 선택하고 값을 설정하는건 지양해야한다.
> 
> ex) auth.inMemoryAuthentication().withUser("john").password("12345").authorities("read")...
> 
> 애플리케이션의 책임과 관심사의 분리 측면으로 접근해서 설정을 구성한다.
