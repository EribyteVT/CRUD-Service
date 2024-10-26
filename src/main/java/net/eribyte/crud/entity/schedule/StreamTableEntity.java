package net.eribyte.crud.entity.schedule;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="stream_table_tied")
public class StreamTableEntity {

    @Id
    @GeneratedValue(generator="stream_table_tied_seq")
    @SequenceGenerator(name="stream_table_tied_seq",sequenceName="schedules.stream_table_tied_seq", allocationSize=1)
    @Column(name="stream_id")
    private Integer streamId;

    @Column(name="stream_date")
    private Timestamp streamDate;

    @Column(name="streamer_id")
    private String streamerId;

    @Column(name="stream_name")
    private String streamName;

    @Column(name="event_id")
    private String eventId;

    @Column(name="twitch_segment_id")
    private String twitchSegmentId;

    @Column(name="duration")
    private Integer duration;

    @Column(name="category_id")
    private String categoryId;
}
