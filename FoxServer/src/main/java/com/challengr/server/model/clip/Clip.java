package com.challengr.server.model.clip;

import com.challengr.server.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Clip implements Post {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    private String title;

    @Getter
    @OneToMany(mappedBy = "clip", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Resolution> resolutions;

    @OneToMany(mappedBy = "clip")
    private List<Opinion> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User user;

}
