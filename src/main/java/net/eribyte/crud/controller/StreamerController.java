package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.Constants.ResponseConstants;
import net.eribyte.crud.entity.schedule.StreamerEntity;
import net.eribyte.crud.model.CrudResponseEntity;
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
    public CrudResponseEntity getStreamerByGuild(@PathVariable("guild") String guild){
        //always send it via UTC timestamp
        try {
            CrudResponseEntity response = new CrudResponseEntity();
            response.setData(repository.findByGuildEquals(guild));
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            log.info(response);
            return response;
        }
        catch (Exception e){
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

    @PostMapping(value = "/streamer/addTwitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity addTwitchToStreamer(@RequestBody AddTwitchToStreamerRequest addTwitchToStreamerRequest){

        if(!password.equals(addTwitchToStreamerRequest.getPassword())){
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
            return response;
        }
        //always send it via UTC timestamp
        try {

            StreamerEntity streamer = repository.findByStreamerIdEquals(addTwitchToStreamerRequest.getStreamerId());
            streamer.setTwitchId(addTwitchToStreamerRequest.getTwitchId());
            StreamerEntity streamer_returned = repository.save(streamer);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(streamer_returned);
            return response;
        }
        catch (Exception e){
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }
}
