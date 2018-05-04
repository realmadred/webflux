package com.feng.webflux;

import com.feng.webflux.entity.MyEvent;
import com.feng.webflux.entity.SysUser;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class WebClientTest {

    @Test
    public void test() throws InterruptedException {
        WebClient webClient = WebClient.create("http://127.0.0.1:8080");
        webClient.get().uri("/user")
                .retrieve().bodyToFlux(String.class)
                .subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void test1() throws InterruptedException {
        WebClient webClient = WebClient.create("https://www.hao123.com");
        CountDownLatch latch = new CountDownLatch(1);
        webClient.get()
                .retrieve().bodyToMono(String.class)
                .subscribe(System.out::println,null,() -> latch.countDown());
        latch.await(10,TimeUnit.SECONDS);
    }

    @Test
    /**
     * 1这次我们使用WebClientBuilder来构建WebClient对象；
     * 2配置请求Header：Content-Type: application/stream+json；
     * 3获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”；
     * 4使用flatMap来将ClientResponse映射为Flux；
     * 5只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流；
     * 6上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用。
     */
    public void webClientTest2() throws InterruptedException {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build(); // 1
        webClient
                .get().uri("/user")
                .accept(MediaType.APPLICATION_STREAM_JSON) // 2
                .exchange() // 3
                .flatMapMany(response -> response.bodyToFlux(SysUser.class))   // 4
                .doOnNext(System.out::println)  // 5
                .blockLast();   // 6
    }

    @Test
    /**
     * 1 配置请求Header：Content-Type: text/event-stream，即SSE；
     * 2 这次用log()代替doOnNext(System.out::println)来查看每个元素；
     * 3 由于/times是一个无限流，这里取前10个，会导致流被取消；
     */
    public void webClientTest3() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient
                .get().uri("/times")
                .accept(MediaType.TEXT_EVENT_STREAM)    // 1
                .retrieve()
                .bodyToFlux(String.class)
                .log()  // 2
                .take(10)   // 3
                .blockLast();
    }

    @Test
    public void test4() {
        WebClient webClient = WebClient.create("http://localhost:8080");

        Flux<MyEvent> flux = Flux.interval(Duration.ofMillis(1000))
                .map(l -> new MyEvent(System.currentTimeMillis(),"msg-"+l));
        webClient.post().uri("/events")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(flux,MyEvent.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Test
    public void test5() {
        WebClient webClient = WebClient.create("http://localhost:8080");
        webClient.get().uri("/events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .block();
    }
}
