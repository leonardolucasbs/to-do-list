package com.leonardolucs.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "descriptions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Description {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    @OneToOne
    @JoinColumn(name = "task_id")
    @JsonIgnore
    private Task task;

    public Description(String description, Task task) {
        this.description = description;
        this.task = task;
    }
}
