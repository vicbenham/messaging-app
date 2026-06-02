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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
public class NotificationService {

    private final Map<String, Sinks.Many<Notification>> sinkMap = new HashMap<>();

    public void addChannel(String userName){
        sinkMap.putIfAbsent(userName, Sinks.many().multicast().onBackpressureBuffer());
    }

    public void sendMessageToUser(String userName, Notification output) throws Exception {
        if(sinkMap.get(userName) == null){
            throw new Exception("No user");
        }
        sinkMap.get(userName).tryEmitNext(output);
    }

    public Flux<Notification> useUserChannel(String userName){
        return sinkMap.get(userName).asFlux();
    }

    public void unsubscribe(String userName){
        sinkMap.remove(userName);
    }

}
