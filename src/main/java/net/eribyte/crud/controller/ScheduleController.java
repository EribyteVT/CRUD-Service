package net.eribyte.crud.controller;

import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.model.AddStreamRequest;
import net.eribyte.crud.repository.ScheduleRepository;
import net.eribyte.crud.entity.ScheduleEntity;
import net.eribyte.crud.entity.ScheduleEntityKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class ScheduleController {
    @Autowired
    ScheduleRepository repository;

    @GetMapping("/test/{dateStart}")
    public List<ScheduleEntity> testWithDate(@PathVariable("dateStart") Long weekStart){
        //always send it via UTC timestamp
        try {
            long oneWeek = 604800000;

            Timestamp date1 = new Timestamp(weekStart);
            Timestamp date2 = new Timestamp(weekStart+oneWeek);


            ScheduleEntityKey key1 = new ScheduleEntityKey(date1);
            ScheduleEntityKey key2 = new ScheduleEntityKey(date2);

            return repository.findByScheduleEntityKeyGreaterThanAndScheduleEntityKeyLessThan(key1, key2);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    @PostMapping(value = "/AddStream",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.ALL_VALUE)
    public String AddStream(@RequestBody AddStreamRequest addStreamRequest){
        //always send it via UTC timestamp
        try {
            ScheduleEntityKey newStreamKey = new ScheduleEntityKey();
            newStreamKey.setDate(new Timestamp(Long.parseLong(addStreamRequest.getTimestamp())*1000));

            ScheduleEntity newStream = new ScheduleEntity();
            newStream.setScheduleEntityKey(newStreamKey);
            newStream.setStreamName(addStreamRequest.getStreamName());
            newStream.setDescription(addStreamRequest.getDescription());

            repository.save(newStream);

            return "True";


        }
        catch (Exception e){
            e.printStackTrace();
            return "False";
        }
    }

}
