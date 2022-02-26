package com.tianya.springcloud.hystrix.feign;

import com.tianya.springcloud.hystrix.entity.BookVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 设置回调工厂类，可以获取错误提示
 */
@Component
public class BookServiceFallBackFactory implements FallbackFactory<BookServiceFeignClient> {

    @Override
    public BookServiceFeignClient create(Throwable e) {
        return new BookServiceFallBack(){
            @Override
            public BookVo getBookVo() {
                return BookVo.builder()
                        .name(String.format("《Hystrix回调获取异常之%s》", e.getMessage()))
                        .author("tianwyam")
                        .build();
            }
        };
    }
}
