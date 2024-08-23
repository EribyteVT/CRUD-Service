package net.eribyte.crud.entity.tokens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="schedules", name="streamer_refresh_tokens")
public class StreamerRefreshToken {

    @Id
    @Column(name="streamer_id")
    private Integer streamerId;

    @Column(name="refresh_token")
    private String refreshToken;

    @Column(name="salt")
    private String salt;
}
