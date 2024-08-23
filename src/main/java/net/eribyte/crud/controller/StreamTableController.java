package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.entity.schedule.StreamTableEntity;
import net.eribyte.crud.entity.schedule.StreamTableEntityKey;
import net.eribyte.crud.model.schedule.AddStreamTableEntryRequest;
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

            return repository.findByStreamTableEntityKeyStreamDateGreaterThanAndStreamTableEntityKeyStreamDateLessThanAndStreamTableEntityKeyStreamerIdEquals(date1,date2,streamerId);
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

            StreamTableEntityKey newStreamKey = new StreamTableEntityKey();
            newStreamKey.setStreamDate(new Timestamp(Long.parseLong(addStreamTableEntryRequest.getTimestamp())*1000));
            newStreamKey.setStreamerId(addStreamTableEntryRequest.getStreamerId());

            StreamTableEntity newStream = new StreamTableEntity();
            newStream.setStreamTableEntityKey(newStreamKey);
            newStream.setStreamName(addStreamTableEntryRequest.getStreamName());

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

            StreamTableEntityKey deleteStreamKey = new StreamTableEntityKey();
            deleteStreamKey.setStreamDate(new Timestamp(Long.parseLong(editStreamRequest.getTimestamp())*1000));
            deleteStreamKey.setStreamerId(editStreamRequest.getStreamerId());

            StreamTableEntity deleteStreamEntity = new StreamTableEntity();

            deleteStreamEntity.setStreamName(editStreamRequest.getStreamName());
            deleteStreamEntity.setStreamTableEntityKey(deleteStreamKey);

            StreamTableEntityKey newStreamKey = new StreamTableEntityKey();
            newStreamKey.setStreamDate(new Timestamp(Long.parseLong(editStreamRequest.getNewTimestamp())*1000));
            newStreamKey.setStreamerId(editStreamRequest.getStreamerId());

            StreamTableEntity newStreamEntity = new StreamTableEntity();

            newStreamEntity.setStreamName(editStreamRequest.getNewName());
            newStreamEntity.setStreamTableEntityKey(newStreamKey);

            if (repository.existsById(deleteStreamKey)) {
                repository.deleteById(deleteStreamKey);
            }
            else{
                return "OLD DATA NOT IN DB";
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

            StreamTableEntityKey newStreamKey = new StreamTableEntityKey();
            newStreamKey.setStreamDate(new Timestamp(Long.parseLong(deleteStreamRequest.getTimestamp())*1000));
            newStreamKey.setStreamerId(deleteStreamRequest.getStreamerId());

            StreamTableEntity newStreamEntity = new StreamTableEntity();

            newStreamEntity.setStreamName(deleteStreamRequest.getStreamName());

            if (repository.existsById(newStreamKey)) {
                repository.deleteById(newStreamKey);
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


}
