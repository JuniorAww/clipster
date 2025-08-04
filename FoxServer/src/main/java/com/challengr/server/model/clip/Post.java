package com.challengr.server.model.clip;

import java.util.List;

public interface Post {
    long getId();
    String getTitle();
    List<Resolution> getResolutions();
}
