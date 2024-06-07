# 버전
- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

## 인스턴스 재정의
### `UserDetailService`, `PasswordEncoder` 인스턴스
- 아무런 설정이 없다면 기본으로 구성되는 `UserDetailService`인스턴스가 생이고 이 때 `PasswordEncoder`도 자동으로 구성을 해준다.
- 하지만, 사용자가 `UserDetailService`를 재정의한다면 `PasswordEncoder`는 자동구성에서 빠지게 된다. 그렇기에 같이 재정의 해주어야 한다. 

> `PasswordEncoder`를 구성하지 않았을 경우 어떤일이 일어날까? 
> 일단, 애플리케이션은 실행이 되지만 엔드포인트를 요청하는 순간 아래와 같은 오류가 발생한다.
> ```shell
> java.lang.IllegalArgumentException: There is no PasswordEncoder mapped for the id "null"
> ```
