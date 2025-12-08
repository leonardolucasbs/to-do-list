import type { TaskPriority } from "../task/taskPriority";

export interface TaskDTO {
  title: string;
  dateTime: string;
  description: string;
  priority: TaskPriority;
}
