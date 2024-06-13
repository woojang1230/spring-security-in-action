# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

# 5.2 SecurityContext 이용

### SecurityContext 인터페이스

```java
public interface SecurityContext extends Serializable {
    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);
}
```

- `SecurityContext`는 `SecurityContextHolder`에서 관리한다.

### SecurityContext 관리 전략

- `MODE_THREADLOCAL` : 각 스레드에 각각의 컨텍스트가 저장된다. 즉, 각 컨텍스트는 속해있는 스레들에서만 접근할 수 있다. 일반적인 설정.
- `MODE_INHERITABLETHREADLOCAL` : `MODE_THREADLOCAL`과 비슷. 비동기 메소드에서 다음 스레드로 컨텍스트를 복사.
- `MODE_GLOBAL` : 모든 스레드에 공유되는 컨텍스트. 이 설정을 할 경우 개발자는 동시접근 문제를 해결해야한다.

### 자체 관리 스레드

자체 관리 스레드는 프레임워크가 관리하는 범위를 벗어나서 생성되는 스레드이다. 이때는 `SecurityContext`의 전략으로는 제어가 불가능.

우선, `DelegatingSecurityContextRunnable`로 이 문제는 해결해본다.(코드 참고)

#### * 보안 컨텍스트를 별도의 스레드로 전파하는 객체 종류와 설명 
| 클래스                                                | 설명                                                                                                                      |
|----------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| DelegatingSecurity-ContextExecutor                 | Executor 인터페이스를 구현하며 Executor 객체를 장식하면 보안 컨텍스트를 해당 풀에 의해 생성된 스레드로 전달하는 기능을 제공하도록 디자인됐다.                                 |
| DelegatingSecurityContext-ExecutorService          | ExecutorService 인터페이스를 구현하며 ExecutorService 객체를 장식하면 보안 컨텍스트를 해당 풀에 의해 생성된 스레드로 전달하는 기능을 제공하도록 디자인됐다.                   |
| DelegatingSecurityContext-ScheduledExecutorService | ScheduledExecutorService 인터페이스를 구현하며 ScheduledExecutorService 객체를 장식하면 보안 컨텍스트를 해당 풀에 의해 생성된 스레드로 전달하는 기능을 제공하도록 디자인됐다. |
| DelegatingSecurityContext-Runnable                 | Runnable 인터페이스를 구현하고 다른 스레드에서 실행되며 응답을 반환하지 않는 작업을 나타낸다. 일반 Runnable의 기능에 더해 새 스레드에서 이용하기 위해 보안 컨텍스트를 전파할 수 있다.         |
| DelegatingSecurityContext-Callable                 | Callable 인터페이스를 구현하고 다른 스레드에서 실행되며 최종적으로 응답을 반환하는 작업을 나타낸다. 일반 Callable의 기능에 더해 새 스레드에서 이용하기 위해 보안 컨텍스 트를 전파할 수 있다.     |
