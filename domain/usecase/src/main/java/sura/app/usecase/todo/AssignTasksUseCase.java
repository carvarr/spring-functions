package sura.app.usecase.todo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import sura.app.domain.common.EventsGateway;
import sura.app.domain.common.ex.BusinessException.Type;
import sura.app.domain.todo.TaskToDo;
import sura.app.domain.todo.TaskToDoOperations;
import sura.app.domain.todo.events.TaskAssigned;
import sura.app.domain.todo.gateway.TaskToDoRepository;
import sura.app.domain.user.User;
import sura.app.domain.user.gateway.UserGateway;

import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.zip;
import static reactor.function.TupleUtils.function;
import static sura.app.domain.common.UniqueIDGenerator.now;

@RequiredArgsConstructor
public class AssignTasksUseCase {

    private final TaskToDoRepository tasks;
    private final UserGateway users;
    private final EventsGateway eventBus;

    public Mono<Void> assignTask(String taskId, String userId){
        return zip(findTask(taskId), findUser(userId))
            .flatMap(function(TaskToDoOperations::assignToUser))
            .flatMap(tasks::save)
            .flatMap(this::emitAssignedEvent);
    }

    private Mono<Void> emitAssignedEvent(TaskToDo task) {
        return now().flatMap(now -> eventBus.emit(new TaskAssigned(task, now)));
    }

    private Mono<TaskToDo> findTask(String id){
        return tasks.findById(id).switchIfEmpty(error(Type.TASK_NOT_FOUND.defer()));
    }

    private Mono<User> findUser(String id){
        return users.findById(id).switchIfEmpty(error(Type.USER_NOT_EXIST.defer()));
    }
}
