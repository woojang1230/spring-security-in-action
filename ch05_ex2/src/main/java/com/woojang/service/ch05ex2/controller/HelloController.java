package com.woojang.service.ch05ex2.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class HelloController {

    /**
     * 1.
     * SecurityContextHolder.getContext().getAuthentication() 메소드를 통해서 스레드에 속한 Authentication 객체를 받을 수 있다.
     * 하지만 함수가 길어지기에 매개변수로 Authentication를 선언하고 SpringBoot으로 부터 객체 주입을 받아서 사용할 수 있다.
     *
     * @param authentication
     * @return
     */
    @GetMapping("/hello")
    public String hello(final Authentication authentication) {
//        final SecurityContext context = SecurityContextHolder.getContext();
//        final Authentication authentication = context.getAuthentication();
        return "Hello, " + authentication.getName() + "!";
    }

    /**
     * 2.
     *
     * @Async 어노테이션을 붙임으로 이 메소드는 요청 스레드와 별도의 스레스에서 실행된다.
     * context.getAuthentication()는 null은 반환하기에 getName()에서 NullPointException이 발생한다.
     * 하지만, MODE_INHERITABLETHREADLOCAL로 설정된 InitializingBean을 등록하면 정상적으로 객체를 받아올 수 있다.
     * @Async을 활성화 하기 위해 ProjectConfig 클래스에 @EnableAsync을 지정해 준다.
     */
    @GetMapping("/bye")
    @Async
    public void goodbye() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
    }


    /**
     * 3.
     * MODE_THREADLOCAL(기본 값)일 경우 1번 주석으로 서비스를 요청하면 에러가 발생한다.
     * 이 문제를 해결하기 위해서 DelegatingSecurityContextCallable를 사용한 2번 주석으로 서비스를 실행하면 문제없이
     * Authentication 객체를 받아온다.
     */
    @GetMapping("/ciao")
    public String ciao() throws Exception {
        Callable<String> task = () -> {
            final SecurityContext context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };
        final ExecutorService executorService = Executors.newCachedThreadPool();
        try {
//            1.
//            return "Ciao, " + executorService.submit(task).get() + "!";

//            2.
            final DelegatingSecurityContextCallable<String> contextTask = new DelegatingSecurityContextCallable<>(task);
            return "Ciao, " + executorService.submit(contextTask).get() + "!";
        } finally {
            executorService.shutdown();
        }
    }
}
