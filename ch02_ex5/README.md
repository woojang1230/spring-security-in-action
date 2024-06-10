# 버전
- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

## AuthenticationProvider 재정의
```java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        // 인증 논리를 추가
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        // Authentication 형식의 구현을 추가
    }
}
```

아래와 같이 구현한다.

```java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        // ID를 꺼낸다.
        final String username = authentication.getName();
        // 비밀번호를 꺼낸다.
        final String password = authentication.getCredentials().toString();
        // 사용자 인증 로직
        if (Objects.equals("Jang", username) && Objects.equals("12345", password)) {
            // 인증 성공 시 반환. principal, credentials, authorities를 인자로 한 UsernamePasswordAuthenticationToken 객체 반환
            return new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList());
        }
        throw new AuthenticationCredentialsNotFoundException("Error in authentication!");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        // 위 함수에서 반환하는 객체 타입을 추가?
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```
몇가지 중요한 부분을 보자.

1. `authentication.getName()` : 사용자 ID를 꺼낸다.
2. `authentication.getCredentials().toString()` : Password를 꺼낸다.
3. `new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList())` : 
