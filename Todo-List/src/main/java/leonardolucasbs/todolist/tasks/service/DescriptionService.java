package leonardolucasbs.todolist.tasks.service;

import leonardolucasbs.todolist.tasks.models.entities.Description;
import leonardolucasbs.todolist.tasks.models.entities.Task;
import leonardolucasbs.todolist.tasks.repositories.DescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    public Description createDescription(String description, Task task){
       return descriptionRepository.save(new Description(description, task));
    }
}
