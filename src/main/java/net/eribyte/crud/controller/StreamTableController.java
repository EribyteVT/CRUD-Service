package net.eribyte.crud.controller;


import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.Constants.ResponseConstants;
import net.eribyte.crud.entity.schedule.StreamTableEntity;
import net.eribyte.crud.model.CrudResponseEntity;
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
    public CrudResponseEntity getStreams(@PathVariable("streamerId") String streamerId, @PathVariable("dateStart") Long weekStart){
        //always send it via UTC timestamp
        try {
            long oneWeek = 604800000;

            Timestamp date1 = new Timestamp(weekStart);
            Timestamp date2 = new Timestamp(weekStart+oneWeek);

            List<StreamTableEntity> streams = repository.findByStreamDateGreaterThanAndStreamDateLessThanAndStreamerIdEquals(date1,date2,streamerId);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(streams);

            return response;
        }
        catch (Exception e){
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

    @PostMapping(value = "/AddStreamTable",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity addStream(@RequestBody AddStreamTableEntryRequest addStreamTableEntryRequest){
        //always send it via UTC timestamp
        try {
            if(!password.equals(addStreamTableEntryRequest.getPassword())){
                log.error("INCORRECT_PASSWORD");
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
            }

            StreamTableEntity newStream = new StreamTableEntity();
            newStream.setStreamerId(addStreamTableEntryRequest.getStreamerId());
            newStream.setStreamName(addStreamTableEntryRequest.getStreamName());
            newStream.setStreamDate(new Timestamp(Long.parseLong(addStreamTableEntryRequest.getTimestamp())*1000));


            StreamTableEntity streamTableEntity = repository.save(newStream);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(streamTableEntity);

            return response;


        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

    @PostMapping(value = "/editStream",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity editStream(@RequestBody EditStreamRequest editStreamRequest){
        //always send it via UTC timestamp
        try {

            if(!password.equals(editStreamRequest.getPassword())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
            }

            if(!repository.existsById(editStreamRequest.getStreamId())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.NOT_FOUND_RESPONSE);
                return response;
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



            StreamTableEntity streamTableEntity = repository.save(newStreamEntity);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(streamTableEntity);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

    @PostMapping(value = "/deleteStream",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity deleteStream(@RequestBody DeleteStreamRequest deleteStreamRequest){
        //always send it via UTC timestamp
        try {

            if(!password.equals(deleteStreamRequest.getPassword())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
            }

            if (repository.existsById(deleteStreamRequest.getStreamId())) {
                repository.deleteById(deleteStreamRequest.getStreamId());
            }
            else{
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.NOT_FOUND_RESPONSE);
                return response;
            }

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);

            return response;

        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }

    @PostMapping(value = "/stream/addOtherId",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CrudResponseEntity editStream(@RequestBody AddOtherIdToStream addTwitchIdToStream){
        //always send it via UTC timestamp
        try {

            if(!password.equals(addTwitchIdToStream.getPassword())){
                CrudResponseEntity response = new CrudResponseEntity();
                response.setResponse(ResponseConstants.FORBIDDEN_RESPONSE);
                return response;
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

            StreamTableEntity savedEntity = repository.save(streamTableEntity);

            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.OKAY_RESPONSE);
            response.setData(savedEntity);

            return response;
        }
        catch (Exception e){
            e.printStackTrace();
            CrudResponseEntity response = new CrudResponseEntity();
            response.setResponse(ResponseConstants.ERROR_RESPONSE);
            return response;
        }
    }




}
