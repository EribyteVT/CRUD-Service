package net.eribyte.crud.controller;

import lombok.extern.log4j.Log4j2;
import net.eribyte.crud.entity.xp.ConnectionTableEntity;
import net.eribyte.crud.entity.xp.XpTableEntity;
import net.eribyte.crud.model.xp.ConnectRequest;
import net.eribyte.crud.model.xp.UpdateRequest;
import net.eribyte.crud.repository.xp.ConnectionRepository;
import net.eribyte.crud.repository.xp.XpRepository;
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
public class XpController {

    @Value("${security.delete.code}")
    private String password;

    @Autowired
    XpRepository xpRepository;


    @Autowired
    ConnectionRepository connectionRepository;

    @GetMapping("/getbyId/discord/{discordId}")
    public XpTableEntity getByDiscordId(@PathVariable("discordId") String discordId){
        try {
            return xpRepository.findByServiceIdEqualsAndServiceNameEquals(discordId,"discord");
        }
        catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getbyId/twitch/{twitchId}")
    public XpTableEntity getByTwitchId(@PathVariable("twitchId") String twitchId){
        try {
            return xpRepository.findByServiceIdEqualsAndServiceNameEquals(twitchId,"twitch");
        }
        catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getbyId/youtube/{youtubeId}")
    public XpTableEntity getByYoutubeId(@PathVariable("youtubeId") String youtubeId){
        try {
            return xpRepository.findByServiceIdEqualsAndServiceNameEquals(youtubeId,"youtube");
        }
        catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getbyId/eriId/{EriId}")
    public XpTableEntity getByEriId(@PathVariable("EriId") int EriId){
        try {
            return xpRepository.findByEriIdEquals(EriId);
        }
        catch (Exception e){
            return null;
        }
    }

    @PostMapping(value = "/update/discord",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public XpTableEntity updateByDiscordId( @RequestBody UpdateRequest updateRequest){
        try {

            if(!password.equals(updateRequest.getPassword())){
                log.error("INCORRECT PASSWORD");
                return null;
            }

            XpTableEntity user = getByDiscordId(updateRequest.getId());

            if (user == null) {
                user = new XpTableEntity();
                user.setServiceId(updateRequest.getId());
                user.setServiceName("discord");
                user.setXp(0);
            }

            if(updateRequest.isUpdateTime()){
                Timestamp date = new Timestamp(updateRequest.getNewTime());
                user.setLastMessageXp(date);
            }

            user.setXp(user.getXp() + updateRequest.getXp());
            xpRepository.saveAndFlush(user);

            return user;
        }
        catch(Exception e){
            return null;
        }
    }

    @PostMapping(value = "/update/youtube",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public XpTableEntity updateByYoutubeId( @RequestBody UpdateRequest updateRequest){
        try {

            if(!password.equals(updateRequest.getPassword())){
                return null;
            }

            XpTableEntity user = getByYoutubeId(updateRequest.getId());

            if (user == null) {
                user = new XpTableEntity();
                user.setServiceId(updateRequest.getId());
                user.setServiceName("youtube");
                user.setXp(0);
            }

            if(updateRequest.isUpdateTime()){
                Timestamp date = new Timestamp(updateRequest.getNewTime());
                user.setLastMessageXp(date);
            }

            user.setXp(user.getXp() + updateRequest.getXp());
            xpRepository.save(user);
            return user;
        }
        catch(Exception e){
            return null;
        }
    }

    @PostMapping(value = "/update/twitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public XpTableEntity updateByTwitchId( @RequestBody UpdateRequest updateRequest){
        try {
            if(!password.equals(updateRequest.getPassword())){
                return null;
            }

            XpTableEntity user = getByTwitchId(updateRequest.getId());

            if (user == null) {
                user = new XpTableEntity();
                user.setServiceId(updateRequest.getId());
                user.setServiceName("twitch");
                user.setXp(0);
            }
            user.setXp(user.getXp() + updateRequest.getXp());

            if(updateRequest.isUpdateTime()){
                Timestamp date = new Timestamp(updateRequest.getNewTime());
                user.setLastMessageXp(date);
            }


            xpRepository.save(user);
            return user;
        }
        catch(Exception e){
            return null;
        }
    }

    //###################################### I'D SEPERATE BUT WE NEED EACHOTHER ####################################################################################################

    @GetMapping("/getConnections/{discordId}")
    public List<ConnectionTableEntity> getConnectedAccounts(@PathVariable("discordId") String discordId){
        List<ConnectionTableEntity> matches = connectionRepository.findAllByDiscordIdEquals(discordId);
        return matches;
    }

    @GetMapping("/getConnections/twitch/{discordId}")
    public ConnectionTableEntity getConnectedTwitch(@PathVariable("discordId") String discordId){
        ConnectionTableEntity match = connectionRepository.findByDiscordIdEqualsAndServiceNameEquals(discordId,"twitch");
        return match;
    }

    @GetMapping("/getConnections/youtube/{discordId}")
    public ConnectionTableEntity getConnectedYoutube(@PathVariable("discordId") String discordId){
        ConnectionTableEntity match = connectionRepository.findByDiscordIdEqualsAndServiceNameEquals(discordId,"youtube");
        return match;
    }

    @GetMapping("/getTwitchConnections/{twitchId}")
    public List<ConnectionTableEntity> getConnectedFromTwitch(@PathVariable("twitchId") String twitchId){
        ConnectionTableEntity discord = connectionRepository.findByServiceIdEqualsAndServiceNameEquals(twitchId,"twitch");
        return getConnectedAccounts(discord.getDiscordId());
    }

    @GetMapping("/GetAllAccountsAssociated/twitch/{twitchId}")
    public List<XpTableEntity> getAllAssociateTwitch(@PathVariable("twitchId") String twitchId){
        List<XpTableEntity> accountEntities = new ArrayList<>();

        ConnectionTableEntity discord = connectionRepository.findByServiceIdEqualsAndServiceNameEquals(twitchId,"twitch");

        if(discord == null){
            accountEntities.add(getByTwitchId(twitchId));
            return accountEntities;
        }

        List<ConnectionTableEntity> associated = getConnectedAccounts(discord.getDiscordId());


        //have to do discord manually due to layout
        accountEntities.add(getByDiscordId(discord.getDiscordId()));

        for(ConnectionTableEntity account : associated){
            switch(account.getServiceName()){
                case "twitch":
                    accountEntities.add(getByTwitchId(account.getServiceId()));
                    break;
                case "youtube":
                    accountEntities.add(getByYoutubeId(account.getServiceId()));
                    break;
            }
        }
        return accountEntities;
    }

    @GetMapping("/GetAllAccountsAssociated/discord/{discordId}")
    public List<XpTableEntity> getAllAssociateDiscord(@PathVariable("discordId") String discordId){
        List<XpTableEntity> accountEntities = new ArrayList<>();

        List<ConnectionTableEntity> associated = connectionRepository.findAllByDiscordIdEquals(discordId);

        if(associated == null || associated.isEmpty()){
            accountEntities.add(getByDiscordId(discordId));
            return accountEntities;
        }

        //have to do discord manually due to layout
        accountEntities.add(getByDiscordId(associated.get(0).getDiscordId()));

        for(ConnectionTableEntity account : associated){
            switch(account.getServiceName()){
                case "twitch":
                    accountEntities.add(getByTwitchId(account.getServiceId()));
                    break;
                case "youtube":
                    accountEntities.add(getByYoutubeId(account.getServiceId()));
                    break;
            }
        }
        return accountEntities;
    }


    @PostMapping(value = "/addConnectionDiscord/twitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ConnectionTableEntity addTwitchToDiscord(@RequestBody ConnectRequest connectRequest){

        if(!password.equals(connectRequest.getPassword())){
            return null;
        }

        ConnectionTableEntity match = getConnectedTwitch(connectRequest.getDiscordId());

        if(match != null){
            return null;
        }

        //this makes sure it's in the table lmao
        updateByTwitchId(new UpdateRequest(connectRequest.getServiceId(),0, false,0,connectRequest.getPassword()));

        ConnectionTableEntity newConnection = new ConnectionTableEntity();

        newConnection.setDiscordId(connectRequest.getDiscordId());
        newConnection.setServiceId(connectRequest.getServiceId());
        newConnection.setServiceName("twitch");

        connectionRepository.save(newConnection);


        return newConnection;
    }

    @PostMapping(value = "/addConnectionDiscord/youtube",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ConnectionTableEntity addYoutubeToDiscord(@RequestBody ConnectRequest connectRequest){

        if(!password.equals(connectRequest.getPassword())){
            return null;
        }

        ConnectionTableEntity match = getConnectedYoutube(connectRequest.getDiscordId());

        if(match != null){
            return null;
        }

        //this makes sure it's in the table lmao
        updateByYoutubeId(new UpdateRequest(connectRequest.getServiceId(),0, false,0,connectRequest.getPassword()));

        ConnectionTableEntity newConnection = new ConnectionTableEntity();

        newConnection.setDiscordId(connectRequest.getDiscordId());
        newConnection.setServiceId(connectRequest.getServiceId());
        newConnection.setServiceName("youtube");

        connectionRepository.save(newConnection);

        return newConnection;
    }

}
