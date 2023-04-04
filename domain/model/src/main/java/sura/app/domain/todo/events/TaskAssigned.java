package sura.app.domain.todo.events;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import sura.app.domain.common.Event;
import sura.app.domain.todo.TaskToDo;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class TaskAssigned implements Event {

    public static final String EVENT_NAME = "todoTasks.task.assigned";
    private final TaskToDo task;
    private final Date date;

    @Override
    public String name() {
        return EVENT_NAME;
    }
}
