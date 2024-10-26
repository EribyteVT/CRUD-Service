package net.eribyte.crud.entity.tokens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="streamer_refresh_tokens")
public class StreamerRefreshToken {

    @Id
    @Column(name="twitch_user_id")
    private Integer twitchId;

    @Column(name="refresh_token")
    private String refreshToken;

    @Column(name="refresh_salt")
    private String refreshSalt;

    @Column(name="access_token")
    private String accessToken;

    @Column(name="access_salt")
    private String accessSalt;
}
