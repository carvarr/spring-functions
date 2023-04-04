package sura.app.usecase.todo;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import sura.app.domain.todo.gateway.TaskToDoRepository;

import static sura.app.domain.todo.TaskToDoOperations.setPendingToReAssign;

@RequiredArgsConstructor
public class ReAssignUserTasksUseCase {

    private final TaskToDoRepository tasks;

    public Mono<Void> markUserTaskAsPendingToReAssign(String userId) {
        return tasks.findAllUserOpenTasks(userId)
            .map(setPendingToReAssign())
            .as(tasks::saveAll);
    }
}
