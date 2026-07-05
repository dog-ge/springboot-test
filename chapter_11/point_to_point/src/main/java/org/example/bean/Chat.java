package org.example.bean;

public class Chat {
    private String to;
    private String from;
    private String content;
    // 省略 getter/setter

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "to='" + to + '\'' +
                ", from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}