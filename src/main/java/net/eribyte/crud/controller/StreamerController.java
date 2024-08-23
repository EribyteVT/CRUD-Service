package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.entity.schedule.StreamerEntity;
import net.eribyte.crud.repository.schedule.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@PreAuthorize("hasIpAddress('10.0.0.6') or hasIpAddress('127.0.0.1') or hasIpAddress('10.0.0.158')")
public class StreamerController {

    @Autowired
    StreamerRepository repository;

    @GetMapping("/getStreamer/{guild}")
    public StreamerEntity getStreamerByGuild(@PathVariable("guild") String guild){
        //always send it via UTC timestamp
        try {
            return repository.findByGuildEquals(guild);
        }
        catch (Exception e){
            return null;
        }
    }
}
