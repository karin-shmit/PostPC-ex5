package exercise.android.reemh.todo_items;

import java.io.Serializable;
import java.util.Date;

public class TodoItem implements Serializable {
    private static int id = 0;
    private int taskId;
    private String task;
    private boolean isDone;
    private Date creation_timestamp;
    private Date edit_timestamp;

    public TodoItem(String taskDesc){
        this.task = taskDesc;
        this.isDone = false;
        this.taskId = id++;
        this.creation_timestamp = new Date();
        this.edit_timestamp = new Date();
    }

    public int getTaskId(){
        return this.taskId;
    }

    public String getTaskDescription(){
        return this.task;
    }

    public boolean getIsDone(){
        return this.isDone;
    }

    public Date getCreationTimestamp(){
        return this.creation_timestamp;
    }

    public Date getLastModifiedTimestamp() {
        return this.edit_timestamp;
    }

    public void setIfDone(boolean done){
        if (this.isDone != done){
            this.edit_timestamp = new Date();
        }
        this.isDone = done;
    }

    public void setTask(String description){
        if (!this.task.equals(description)){
            this.edit_timestamp = new Date();
        }
        this.task = description;
    }
}
