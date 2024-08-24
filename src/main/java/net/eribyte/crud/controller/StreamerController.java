package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.entity.schedule.StreamerEntity;
import net.eribyte.crud.model.schedule.AddTwitchToStreamerRequest;
import net.eribyte.crud.repository.schedule.StreamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@PreAuthorize("hasIpAddress('10.0.0.6') or hasIpAddress('127.0.0.1') or hasIpAddress('10.0.0.158')")
public class StreamerController {

    @Autowired
    StreamerRepository repository;

    @Value("${security.delete.code}")
    private String password;

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

    @PostMapping(value = "/streamer/addTwitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String addTwitchToStreamer(@RequestBody AddTwitchToStreamerRequest addTwitchToStreamerRequest){

        if(!password.equals(addTwitchToStreamerRequest.getPassword())){
            log.error("INCORRECT_PASSWORD");
            return "FORBIDDEN";
        }
        //always send it via UTC timestamp
        try {
            StreamerEntity streamer = repository.findByStreamerIdEquals(addTwitchToStreamerRequest.getStreamerId());
            streamer.setTwitchId(addTwitchToStreamerRequest.getTwitchId());
            repository.save(streamer);
            return "UPDATED";
        }
        catch (Exception e){
            return "ERROR";
        }
    }
}
