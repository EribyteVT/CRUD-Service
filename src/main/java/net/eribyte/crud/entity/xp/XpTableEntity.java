package net.eribyte.crud.entity.xp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="leveling_bot", name="xptracker")
public class XpTableEntity {

    @Id
    @Column(name="eri_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eriId;

    @Column(name="xp")
    private int xp;

    @Column(name="service_id")
    private String serviceId;

    @Column(name="lastmessagexp")
    private Timestamp lastMessageXp;

    @Column(name="service_name")
    private String serviceName;


}
