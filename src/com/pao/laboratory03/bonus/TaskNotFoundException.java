package com.pao.laboratory03.bonus;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String taskId){
        super(taskId + " nu a fost gasit!");
    }
}
