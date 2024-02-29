package org.zerock.w1.todo.service;

import org.zerock.w1.todo.dto.TodoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// enum : 정해진 수만큼만 객체를 생성할 수 있음
/*
INSTANCE는 항상 하나의 객체만을 가리킨다.(하나만 생성해서 사용)
:싱글톤
 */
public enum TodoService {
    INSTANCE;

    public void register(TodoDTO todoDTO){
        System.out.println("DEBUG........." + todoDTO);
    }

    public List<TodoDTO> getList(){
        //10개의 객체 생성
        List<TodoDTO> todoDTOS = IntStream.range(0, 10).mapToObj(i -> {
            TodoDTO dto = new TodoDTO();
            dto.setTno((long)i);
            dto.setTitle("Todo.." +i);
            dto.setDueDate(LocalDate.now());

            return dto;
        }).collect(Collectors.toList());

        return todoDTOS;
    }
}
