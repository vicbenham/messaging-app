package org.example.messagingapp.services;

import lombok.Getter;
import org.aspectj.weaver.ast.Not;
import org.example.messagingapp.dtos.Notification;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class NotificationService {

    private final Sinks.Many<Notification> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void addMessage(Notification output){
        sink.tryEmitNext(output);
    }

    public Flux<Notification> getFlux(){
        return sink.asFlux();
    }

}
