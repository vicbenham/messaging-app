package org.example.messagingapp.services;

import lombok.Getter;
import org.example.messagingapp.dtos.Notification;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Service
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
