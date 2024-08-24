package net.eribyte.crud.entity.xp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="leveling_bot", name="connections")
public class ConnectionTableEntity {
    @Id
    @Column(name="connection_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer connectionId;

    @Column(name="discord_id")
    private String discordId;

    @Column(name="service_id")
    private String serviceId;

    @Column(name="service_name")
    private String serviceName;
}
