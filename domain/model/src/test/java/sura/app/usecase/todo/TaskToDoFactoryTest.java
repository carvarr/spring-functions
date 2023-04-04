package sura.app.usecase.todo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import sura.app.domain.common.ex.BusinessException;
import sura.app.domain.todo.TaskToDo;
import sura.app.domain.todo.TaskToDoFactory;

public class TaskToDoFactoryTest {

    private String desc = "Desc";
    private String id = "2321";
    private String name = "Tarea";

    @Test
    public void shouldCreateANewTask(){
        final Mono<TaskToDo> taskMono = TaskToDoFactory.createTask(id, name, desc);
        StepVerifier.create(taskMono).assertNext(task -> {
            assertThat(task)
                .extracting(TaskToDo::getId, TaskToDo::getName, TaskToDo::getDescription)
                .containsExactly(id, name, desc);
        }).verifyComplete();
    }

    @Test
    public void shouldFailCreateANewTaskWithoutName(){
        final Mono<TaskToDo> taskMono = TaskToDoFactory.createTask(id, "", desc);
        verifyCreationBusinessError(taskMono);
    }

    @Test
    public void shouldFailCreateANewTaskWithoutId(){
        final Mono<TaskToDo> taskMono = TaskToDoFactory.createTask("", name, desc);
        verifyCreationBusinessError(taskMono);
    }

    @Test
    public void shouldFailCreateANewTaskWithoutDescription(){
        final Mono<TaskToDo> taskMono = TaskToDoFactory.createTask(id, name, null);
        verifyCreationBusinessError(taskMono);
    }

    private void verifyCreationBusinessError(Mono<TaskToDo> taskMono){
        StepVerifier.create(taskMono)
            .expectErrorSatisfies(error ->
                assertThat(error)
                    .hasNoCause()
                    .hasMessage(BusinessException.Type.INVALID_TODO_INITIAL_DATA.getMessage())
            )
            .verify();
    }

}