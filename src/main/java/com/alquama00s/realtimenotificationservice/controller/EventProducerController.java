package com.alquama00s.realtimenotificationservice.controller;


import com.alquama00s.realtimenotificationservice.eventnotifier.eventproducer.ProducerException;
import com.alquama00s.realtimenotificationservice.DTO.Event;
import com.alquama00s.realtimenotificationservice.service.EventProducerService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventProducerController {

    @Setter(onMethod_ = @Autowired)
    private EventProducerService eventProducerService;

    @PostMapping("/produce")
    public Event produce(@RequestBody Event event) throws ProducerException {
        return eventProducerService.produce(event);
    }






}
