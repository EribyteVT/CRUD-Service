package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.entity.schedule.StreamTableEntity;
import net.eribyte.crud.model.schedule.AddStreamTableEntryRequest;
import net.eribyte.crud.model.schedule.AddOtherIdToStream;
import net.eribyte.crud.model.schedule.DeleteStreamRequest;
import net.eribyte.crud.model.schedule.EditStreamRequest;
import net.eribyte.crud.repository.schedule.StreamTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RestController
@PreAuthorize("hasIpAddress('10.0.0.6') or hasIpAddress('127.0.0.1') or hasIpAddress('10.0.0.158')")
public class StreamTableController {

    @Value("${security.delete.code}")
    private String password;

    @Autowired
    StreamTableRepository repository;

    @GetMapping("/getStreams/{streamerId}/{dateStart}")
    public List<StreamTableEntity> getStreams(@PathVariable("streamerId") String streamerId, @PathVariable("dateStart") Long weekStart){
        //always send it via UTC timestamp
        try {
            long oneWeek = 604800000;

            Timestamp date1 = new Timestamp(weekStart);
            Timestamp date2 = new Timestamp(weekStart+oneWeek);

            return repository.findByStreamDateGreaterThanAndStreamDateLessThanAndStreamerIdEquals(date1,date2,streamerId);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @PostMapping(value = "/AddStreamTable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String addStream(@RequestBody AddStreamTableEntryRequest addStreamTableEntryRequest){
        //always send it via UTC timestamp
        try {
            if(!password.equals(addStreamTableEntryRequest.getPassword())){
                log.error("INCORRECT_PASSWORD");
                return "FORBIDDEN";
            }

            StreamTableEntity newStream = new StreamTableEntity();
            newStream.setStreamerId(addStreamTableEntryRequest.getStreamerId());
            newStream.setStreamName(addStreamTableEntryRequest.getStreamName());
            newStream.setStreamDate(new Timestamp(Long.parseLong(addStreamTableEntryRequest.getTimestamp())*1000));

            repository.save(newStream);

            return "True";


        }
        catch (Exception e){
            e.printStackTrace();
            return "False";
        }
    }

    @PostMapping(value = "/editStream",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String editStream(@RequestBody EditStreamRequest editStreamRequest){
        //always send it via UTC timestamp
        try {

            if(!password.equals(editStreamRequest.getPassword())){
                log.error("INCORRECT_PASSWORD");
                return "FORBIDDEN";
            }

            if(!repository.existsById(editStreamRequest.getStreamId())){
                return "NOT FOUND";
            }


            StreamTableEntity newStreamEntity = repository.findById(editStreamRequest.getStreamId()).get();

            if("time".equals(editStreamRequest.getWhich())){
                newStreamEntity.setStreamDate(new Timestamp(Long.parseLong(editStreamRequest.getNewTimestamp())*1000));
            }
            if("name".equals(editStreamRequest.getWhich())){
                newStreamEntity.setStreamName(editStreamRequest.getNewName());
            }
            if("both".equals(editStreamRequest.getWhich())){
                newStreamEntity.setStreamName(editStreamRequest.getNewName());
                newStreamEntity.setStreamDate(new Timestamp(Long.parseLong(editStreamRequest.getNewTimestamp())*1000));
            }



            repository.save(newStreamEntity);

            return "UPDATED";

        }
        catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @PostMapping(value = "/deleteStream",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String deleteStream(@RequestBody DeleteStreamRequest deleteStreamRequest){
        //always send it via UTC timestamp
        try {

            if(!password.equals(deleteStreamRequest.getPassword())){
                log.error("INCORRECT_PASSWORD");
                return "FORBIDDEN";
            }

            if (repository.existsById(deleteStreamRequest.getStreamId())) {
                repository.deleteById(deleteStreamRequest.getStreamId());
            }
            else{
                return "NOT FOUND";
            }

            return "DELETED";

        }
        catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }

    @PostMapping(value = "/stream/addOtherId",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String editStream(@RequestBody AddOtherIdToStream addTwitchIdToStream){
        //always send it via UTC timestamp
        try {

            if(!password.equals(addTwitchIdToStream.getPassword())){
                log.error("INCORRECT_PASSWORD");
                return "FORBIDDEN";
            }

            log.info(addTwitchIdToStream);

            StreamTableEntity streamTableEntity = repository.findByStreamIdEquals(addTwitchIdToStream.getStreamId());

            log.info(streamTableEntity);
            if("discord".equals(addTwitchIdToStream.getServiceName())){
                streamTableEntity.setEventId(addTwitchIdToStream.getDiscordEventId());
            }

            else if ("twitch".equals(addTwitchIdToStream.getServiceName())) {
                streamTableEntity.setTwitchSegmentId(addTwitchIdToStream.getTwitchStreamId());
            }

            else if("both".equals(addTwitchIdToStream.getServiceName())){
                streamTableEntity.setEventId(addTwitchIdToStream.getDiscordEventId());
                streamTableEntity.setTwitchSegmentId(addTwitchIdToStream.getTwitchStreamId());
            }

            repository.save(streamTableEntity);

            return "UPDATED";
        }
        catch (Exception e){
            e.printStackTrace();
            return "ERROR";
        }
    }




}
