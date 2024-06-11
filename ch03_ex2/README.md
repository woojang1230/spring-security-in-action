# 버전
- SpringBoot : `3.3.0`
- Java : `17`
- Gradle : `8.7`

## UserDetailsManager 인터페이스 구현 - 사용자 관리에 JdbcUserDetailsManager 이용
JDBC를 이용해야 함으로 H2, MySQL 등의 RDB를 사용한다.(프로젝트에서는 H2를 기준으로 한다.)

### application.yml에 데이터 정보 설정
```yaml
# H2
spring:
  datasource:
    url: jdbc:h2:mem:ssia/spring
    username: sa
    password:
    initialization-mode: always
    driver-class-name: org.h2.Driver

# MySQL (아래쪽에 Docker Run 가이드 추가)
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring
    username: root
    password: password
    initialization-mode: always
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 사전 테이블 생성 및 데이터 생성.
```sql
/* schema.sql 파일 */
create schema IF NOT EXISTS spring;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NULL,
    password VARCHAR(45) NULL,
    enabled INT NOT NULL,
    PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS `authorities` (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(45) NULL,
    authority VARCHAR(45) NULL,
    PRIMARY KEY (id));

/* data.sql 파일 */
INSERT INTO authorities(username, authority) VALUES ('Jang', 'write');
INSERT INTO users(username, password, enabled) VALUES ('Jang', '12345', '1');


```

### ProjectConfig 설정
```java
@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService(final DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    ...
}
```

사용자 정보 조회 시 기본적으로 아래 쿼리가 실행된다.
```sql
select username, password, enabled from users where username = ?
```
해당 쿼리와 동일한 테이블이 구성되어 있다면 문제없이 자동으로 사용자 조회가 수행된다.

하지만, 다른 테이블을 사용하려면 UserDetailsService 빈 설정 구간을 수정해야 한다. 다음처럼 직접 쿼리를 사용할 수 있다.

```java
@Configuration
public class ProjectConfig {
    @Bean
    public UserDetailsService userDetailsService(final DataSource dataSource) {
        String usersByUsernameQuery = "select username, password, enabled from spring.users where username = ?";
        String authsByUserQuery = "select username, authority from spring.authorities where username = ?";
        final JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usersByUsernameQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authsByUserQuery);
        return userDetailsManager;
    }

    ...
}
```

> MySQL Docker 실행
> ```shell
> docker run -p 3306:3306 --name mysql -e MYSQL_ROOT_PASSWORD=password -d mysql --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
> ```
