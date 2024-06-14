# 버전
- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

## 다른 방법으로 인스턴스 재정의(InMemory 사용자용)
### configure(final AuthenticationManagerBuilder auth) 오버라이딩
```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Jang")
                .password("12345")
                .authorities("read")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
    ...
}
```
> ❗애플리케이션의 책임과 관심사의 분리 측면으로 접근해서 위 설정은 가능하면 하지 않는 방향으로 구성한다.
