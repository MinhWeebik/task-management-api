package task_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task_management.Dto.TaskDto;
import task_management.Model.Task;
import task_management.Repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDto taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setAssigneeId(taskDTO.getAssigneeId());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus("TODO");
        return taskRepository.save(task);
    }

    public void updateTaskStatus(Long id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            String currentStatus = task.getStatus();
            if (currentStatus.equals("TODO") && status.equals("IN_PROGRESS")) {
                taskRepository.updateStatus(id, status);
            } else if (currentStatus.equals("IN_PROGRESS") && status.equals("DONE")) {
                taskRepository.updateStatus(id, status);
            } else {
                throw new IllegalStateException("Invalid status transition");
            }
        } else {
            throw new IllegalArgumentException("Task not found");
        }
    }

    public List<Task> getTasks(Long assigneeId, String status, LocalDate dueDateStart, LocalDate dueDateEnd, String sortBy, int page, int size) {
        if(sortBy == null || sortBy.isEmpty()){
            sortBy = "due_date DESC";
        }
        return taskRepository.findAll(assigneeId, status, dueDateStart, dueDateEnd, page, size, sortBy);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
}