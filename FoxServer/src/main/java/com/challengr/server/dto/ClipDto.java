package com.challengr.server.dto;

import com.challengr.server.model.clip.Clip;
import com.challengr.server.model.clip.Resolution;

import java.util.List;

public class ClipDto {
    long id;
    public String title;
    public List<ResDto> resolutions;

    public ClipDto(long id, String title, List<ResDto> resolutions) {
        this.title = title;
        this.id = id;
        this.resolutions = resolutions;
    }

    public ClipDto() {}

    public static ClipDto from(Clip clip) {
        List<ResDto> res = clip.getResolutions().stream().map(ResDto::new).toList();
        return new ClipDto(clip.getId(), clip.getTitle(), res);
    }

    public static class ResDto {
        public short type;
        public String link;
        public ResDto(Resolution r) {
            this.type = r.getType();
            this.link = r.getLink();
        }
        public ResDto() {
            
        }
    }
}
