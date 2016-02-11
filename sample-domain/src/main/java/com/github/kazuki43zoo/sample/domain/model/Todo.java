package com.github.kazuki43zoo.sample.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String todoId;

    private String todoTitle;

    private boolean finished;

    private LocalDateTime createdAt;

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
