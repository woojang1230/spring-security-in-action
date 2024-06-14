# 버전
- SpringBoot : `2.3.0.RELEASE`
- Java : `11`
- Gradle : `6.3`

## 기본 구성
아무런 추가 설정 없이 실행했을 때 자동으로 리소스 접근이 제한되고, `Auth Basic`으로 사용자를 인증해야지만 API 접근이 된다.

```shell
# 사용자 정보 없이 API를 접근할 경우
$ curl http://localhost:8080/hello

# 아래처럼 오류 메시지 반환.
{
    "timestamp": "2024-06-07T05:24:16.586+00:00",
    "status": 401,
    "error": "Unauthorized",
    "message": "",
    "path": "/hello"
}
```

API에 접근하려면 초기 앱이 기동할 때 출력된 password를 입력해야 한다.

```shell
2024-06-07 13:40:57.942  INFO 42968 --- [           main] .s.s.UserDetailsServiceAutoConfiguration : 

Using generated security password: 37xxxxxx-xxxx-xxxx-xxxx-xxxxxxxx9070

2024-06-07 13:40:57.995  INFO 42968 --- [           main] o.s.s.web.DefaultSecurityFilterChain     : Creating filter chain: any request, [
```

기본 사용자 id는 user이고 아래처럼 설정하고 API를 요청한다.


```shell
# 사용자 인증 정보를 추가하여 API 요청
curl -u user:37xxxxxx-xxxx-xxxx-xxxx-xxxxxxxx9070 http://localhost:8080/hello

Hello
```

> 이 방법은 Basic Auth를 사용한 사용자 인증 방법이다.
> 위 명령어로 API 요청 시 curl 프로그램은 실제로 `username:password` 문자열을 Base64로 인코딩한 문자열을 헤더에 셋팅해서 API에 넘기게 된다.
> curl이 실제로 호출하는 명령어는 다음과 같은 형태로 만들어진다.
> 
> ```
> curl -H 'Authorization: Basic dXNlcjplZDEzZjFiOC1iNjA5LTQ0YzQtOTk0ZS04M2YzZmNmMjZhOWU=' http://localhost:8080/hello
> ```
> 
