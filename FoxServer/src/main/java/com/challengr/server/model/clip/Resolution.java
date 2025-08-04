package com.challengr.server.model.clip;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Resolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 0 - audio SD, 1 - audio HD
    // 2 - video 360, 3 - video 480, 4 - HD, 5 - Full HD
    @Getter
    private short type;
    @Getter
    private String link;

    @ManyToOne
    @JoinColumn(name = "clip_id")
    private Clip clip;
}