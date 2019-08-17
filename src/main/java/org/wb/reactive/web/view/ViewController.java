package org.wb.reactive.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sun.misc.Contended;

@Controller
@RequestMapping("/view")
public class ViewController {

    @RequestMapping(value = "/ws")
    public Mono<String> profileEventView(){
        return Mono.just("ws");
    }
}
