package com.benjamin_simon.todos.models;

/**
 *
 * @author simon
 */
public class Todo {

    private String id;
    private String content;
    private boolean done;
    private String userId;

    public Todo(String id, String content, boolean done, String userId) {
        this.id = id;
        this.content = content;
        this.done = done;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
