package task_management.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import task_management.Model.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            Task task = new Task();
            task.setId(rs.getLong("id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setStatus(rs.getString("status"));
            task.setDueDate(rs.getDate("due_date").toLocalDate());
            task.setAssigneeId(rs.getLong("assignee_id"));
            return task;
        }
    }

    public Task save(Task task) {
        String sql = "INSERT INTO tasks (title, description, status, due_date, assignee_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getTitle(), task.getDescription(), task.getStatus(), task.getDueDate(), task.getAssigneeId());
        return task;
    }

    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), id);
        return tasks.stream().findFirst();
    }

    public void updateStatus(Long id, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }

    public List<Task> findAll(Long assigneeId, String status, LocalDate dueDateStart, LocalDate dueDateEnd, int page, int size, String sortBy) {

        String sql = "SELECT * FROM tasks " +
        "WHERE (:assigneeId IS NULL OR assignee_id = :assigneeId) " +
        "AND (:status IS NULL OR status = :status) " +
        "AND (:dueDateStart IS NULL OR :dueDateEnd IS NULL OR (due_date BETWEEN :dueDateStart AND :dueDateEnd)) " +
        "ORDER BY " + sortBy + " LIMIT :offset, :limit";

        Map<String, Object> params = new HashMap<>();
        params.put("assigneeId", assigneeId);
        params.put("status", status);
        params.put("dueDateStart", dueDateStart);
        params.put("dueDateEnd", dueDateEnd);
        params.put("offset", page * size);
        params.put("limit", size);

        return namedParameterJdbcTemplate.query(sql,params,new TaskRowMapper());
    }
}