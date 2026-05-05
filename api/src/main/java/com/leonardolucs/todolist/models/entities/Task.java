package com.leonardolucs.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leonardolucs.todolist.models.dto.TaskDTO;
import com.leonardolucs.todolist.models.enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;
    private String title;
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private Description description;

    public TaskDTO toDto(){
        return new TaskDTO(
                this.taskId,
                this.title,
                this.dateTime,
                this.description == null ? "" : this.description.getDescription(),
                this.priority
        );
    }
}
