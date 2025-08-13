package com.msk.ToDoList.service;

import com.msk.ToDoList.model.ToDo;

public interface TodoService {

    void createTodo(String userId, ToDo toDo);
}
