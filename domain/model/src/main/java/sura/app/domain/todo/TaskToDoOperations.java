package sura.app.domain.todo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;
import sura.app.domain.user.User;

import java.util.Date;
import java.util.function.Function;

import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;
import static sura.app.domain.common.StringUtils.isEmpty;
import static sura.app.domain.common.ex.BusinessException.Type.TASK_ALREADY_ASSIGNED;
import static sura.app.domain.common.ex.BusinessException.Type.TASK_NOT_ASSIGNED;
import static sura.app.domain.todo.TaskToDo.TaskReportStatus.ASSIGNED;
import static sura.app.domain.todo.TaskToDo.TaskReportStatus.PENDING_REASSIGNMENT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskToDoOperations {

    public static Function<TaskToDo, Mono<TaskToDo>> markAsDone(Date doneDate){
        return task ->
            task.getReportStatus() != ASSIGNED ?
                error(TASK_NOT_ASSIGNED.build()) :
                just(task.toBuilder().doneDate(doneDate).done(true).build());
    }

    public static Function<TaskToDo, TaskToDo> setPendingToReAssign(){
        return task -> task.toBuilder().assignedUserId(null).reportStatus(PENDING_REASSIGNMENT).build();
    }

    public static Mono<TaskToDo> assignToUser(TaskToDo task, User user){
        return !isEmpty(task.getAssignedUserId()) ? error(TASK_ALREADY_ASSIGNED.build()) :
            just(task.toBuilder().assignedUserId(user.getId()).reportStatus(ASSIGNED).build());
    }

}
