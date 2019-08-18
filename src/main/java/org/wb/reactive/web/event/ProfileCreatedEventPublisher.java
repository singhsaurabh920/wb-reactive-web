package org.wb.reactive.web.event;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component("profileCreatedEventPublisher")
@Log4j2
public class ProfileCreatedEventPublisher implements ApplicationListener<ProfileEvent>,Consumer<FluxSink<ProfileEvent>> {

    private final Executor executor;
    private final BlockingQueue<ProfileEvent> queue = new LinkedBlockingQueue<>();

    ProfileCreatedEventPublisher(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onApplicationEvent(ProfileEvent event) {
       log.info("New event occured- "+event);
        this.queue.offer(event);
    }

     @Override
    public void accept(FluxSink<ProfileEvent> sink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    ProfileEvent event = queue.take();
                    sink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
            }
        });
    }
}
