package net.eribyte.crud.entity.schedule;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="schedules", name="streamer_lookup")
public class StreamerEntity {

    @Id
    @Column(name="streamer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer streamerId;

    @Column(name="streamer_name")
    private String streamerName;

    @Column(name="timezone")
    private String timezone;

    @Column(name="guild")
    private String guild;

    @Column(name="level_system")
    private Character levelSystem;

    @Column(name="level_ping_role")
    private String levelPingRole;

    @Column(name="level_channel")
    private String levelChannel;

    @Column(name="twitch_user_id")
    private String twitchId;

    @Column(name="auto_discord_event")
    private String autoDiscordEvent;

    @Column(name="auto_twitch_schedule")
    private String autoTwitchSchedule;

    @Column(name="auto_image_post")
    private String autoImagePost;

    @Column(name="schedule_message_id")
    private String scheduleMessageId;

    @Column(name="auto_change_schedule")
    private String autoChangeSchedule;

    @Column(name="image_message_id")
    private String imageMessageId;
}
