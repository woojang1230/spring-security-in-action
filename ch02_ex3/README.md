# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

## 다른 방법으로 인스턴스 재정의
SpringBoot 3.x 버전에서 사용되는 Security의 경우 `AuthenticationManagerBuilder`를 사용하여 `UserDetailsService`, `PasswordEncoder`를 설정하던 부분이 더이상 사용할 수 없다.

`UserDetailsService`, `PasswordEncoder`를 각각의 Bean으로 등록해서 사용해야 한다.
