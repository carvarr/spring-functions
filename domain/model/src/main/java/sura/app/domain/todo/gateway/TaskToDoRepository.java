package sura.app.domain.todo.gateway;

import sura.app.domain.todo.TaskToDo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskToDoRepository {
    Mono<TaskToDo> save(TaskToDo taskToDo);
    Mono<Void> saveAll(Flux<TaskToDo> tasks);
    Mono<TaskToDo> findById(String id);
    Flux<TaskToDo> findAll();
    Flux<TaskToDo> findAllUserOpenTasks(String userId);
}
