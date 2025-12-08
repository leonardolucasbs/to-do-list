import type { TaskDTO } from "./taskDTO";

export interface TaskCreate {
  task: TaskDTO;
  userId: number;
}
