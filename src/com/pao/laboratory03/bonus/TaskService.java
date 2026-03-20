package com.pao.laboratory03.bonus;

import java.util.*;
import java.util.stream.Collectors;

public class TaskService {

    private static final TaskService INSTANCE = new TaskService();

    private final Map<String, Task> tasksById = new HashMap<>();
    private final Map<Priority, List<Task>> tasksByPriority = new EnumMap<>(Priority.class);
    private final List<String> auditLog = new ArrayList<>();
    private int idCounter = 0;

    private TaskService() {
        for (Priority p : Priority.values()) {
            tasksByPriority.put(p, new ArrayList<>());
        }
    }

    public static TaskService getInstance() {
        return INSTANCE;
    }

    private String generateId() {
        idCounter++;
        return String.format("T%03d", idCounter);
    }


    public Task addTaskWithId(String id, String title, Priority priority) {
        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Task cu id-ul " + id + " există deja");
        }
        Task task = new Task(id, title, priority);
        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);
        auditLog.add("[ADD] " + id + ": '" + title + "' (" + priority + ")");
        return task;
    }

    public Task addTask(String title, Priority priority) {
        String id = generateId();
        if (tasksById.containsKey(id)) {
            throw new DuplicateTaskException("Task cu id-ul " + id + " există deja");
        }
        Task task = new Task(id, title, priority);
        tasksById.put(id, task);
        tasksByPriority.get(priority).add(task);
        auditLog.add("[ADD] " + id + ": '" + title + "' (" + priority + ")");
        return task;
    }

    public void assignTask(String taskId, String assignee) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }
        task.setAssignee(assignee);
        auditLog.add("[ASSIGN] " + taskId + " → " + assignee);
    }

    public void changeStatus(String taskId, Status newStatus) {
        Task task = tasksById.get(taskId);
        if (task == null) {
            throw new TaskNotFoundException(taskId);
        }
        Status oldStatus = task.getStatus();
        if (!oldStatus.canTransitionTo(newStatus)) {
            throw new InvalidTransitionException(oldStatus, newStatus);
        }
        task.setStatus(newStatus);
        auditLog.add("[STATUS] " + taskId + ": " + oldStatus + " → " + newStatus);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        List<Task> list = tasksByPriority.get(priority);
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(list);
    }

    public Map<Status, Long> getStatusSummary() {
        Map<Status, Long> summary = new EnumMap<>(Status.class);
        for (Status s : Status.values()) {
            summary.put(s, 0L);
        }
        for (Task t : tasksById.values()) {
            summary.put(t.getStatus(), summary.get(t.getStatus()) + 1);
        }
        return summary;
    }

    public List<Task> getUnassignedTasks() {
        return tasksById.values().stream()
                .filter(t -> t.getAssignee() == null)
                .collect(Collectors.toList());
    }

    public void printAuditLog() {
        for (String entry : auditLog) {
            System.out.println(entry);
        }
    }

    public double getTotalUrgencyScore(int baseDays) {
        return tasksById.values().stream()
                .filter(t -> t.getStatus() != Status.DONE && t.getStatus() != Status.CANCELLED)
                .mapToDouble(t -> t.getPriority().calculateScore(baseDays))
                .sum();
    }
}
