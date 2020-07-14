package com.example.mynotes.model;

import java.io.Serializable;

public class Notes implements Serializable {
    public int id,category_id;
    public String  content,created_on,updated_on,title,image,audio,video;

    public Notes(int id, int category_id, String content, String created_on, String updated_on, String title, String image, String audio, String video) {
        this.id = id;
        this.content = content;
        this.created_on = created_on;
        this.updated_on = updated_on;
        this.title = title;
        this.image = image;
        this.audio = audio;
        this.video = video;
        this.category_id = category_id;
    }

}
