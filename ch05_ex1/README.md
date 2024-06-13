# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 5.1 AuthenticationProvider의 이해

## Authentication 인터페이스
```java
public interface Authentication extends Principal, Serializable {
	Collection<? extends GrantedAuthority> getAuthorities();
	Object getCredentials();
	Object getDetails();
	Object getPrincipal();
	boolean isAuthenticated();
	void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
```

여기서 아래 3가지를 알고 간다.
- `isAuthenticated()`: 인증 프로세스가 끝났으면 `true`, 아직 진행중이면 `false`를 반환.
- `getCredentials()`: 인증 프로세스에 이용된 암호나 비밀을 반환.
- `getAuthorities()`: 인증된 요청에 허가된 권한의 컬렉션을 반환.

## 맞춤형 인증논리 구현
`AuthenticationProvider`의 구현체 작성.
```java
public interface AuthenticationProvider {
    /**
     * 1. 인증 실패 시 AuthenticationException을 던진다.
     * 2. 지원하지 않는 인증 객체의 경우 null을 반환한다.
     * 3. 인증된 객체의 Authentication 인스턴스를 반환한다.
     */ 
	Authentication authenticate(Authentication authentication) throws AuthenticationException;
    // 현재 AuthenticationProvider가 Authentication 객체로 제공된 형식을 지원하면 true를 반환.
	boolean supports(Class<?> authentication);
}
```
말이 조금 어려운데 쉽게 이야기 해서 `authenticate()` 함수는 인증에 실패하면 AuthenticationException을 던지고 성공하면 Authentication을 상속하는 객체를 반환한다.(지원하지 않는 케이스는 다음에)

`supports()`함수의 경우 `authenticate()`에서 인증되어 반환하는 Authentication을 상속하는 클래스 타입과 같은지를 검증한다.(자세한 내용은 코드 참고)
