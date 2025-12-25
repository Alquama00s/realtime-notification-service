package com.alquama00s.realtimenotificationservice.service;

import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.ProducerException;
import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.impl.redis.RedisPubSubEventProducer;
import com.alquama00s.realtimenotificationservice.DTO.Event;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    @Setter(onMethod_ = @Autowired)
    private RedisPubSubEventProducer eventProducer;


    public Event produce(Event e) throws ProducerException {
        eventProducer.produce(e);
        return e;
    }


}
