import type { TaskPriority } from "../task/taskPriority";

export interface Task {
  id: number;
  title: string;
  description?: string;
  dateTime: string;
  priority: TaskPriority;
}
