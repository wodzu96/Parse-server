package com.codreamstudio.parse_server;

/**
 * Created by wodzu on 20.09.2017.
 */

public class Message {
    String content;

    public Message() {
    }

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
