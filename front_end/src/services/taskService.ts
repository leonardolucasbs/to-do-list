import { ENV } from "../config/env";
import axios from "axios";
import type { Task } from "../types/task/task";
import type { TaskCreate } from "../types/task/taskCreate";

const API_URL = ENV.PUBLIC_API_URL;

export const getTasks = async (userId: number): Promise<Task[]> => {
  const response = await axios.get(`${API_URL}/tasks?userId=${userId}`);

  return response.data as Task[];
};

export const createTask = async (taskData: TaskCreate): Promise<Task> => {
  const { userId, task } = taskData;
  const response = await axios.post<Task>(
    `${API_URL}/tasks?userId=${userId}`,
    task
  );
  return response.data;
};

export const updateTask = async (
  taskId: number,
  taskData: Partial<Task>
): Promise<Task> => {
  const response = await axios.put<Task>(
    `${API_URL}/tasks/${taskId}`,
    taskData
  );
  return response.data;
};
export const deleteTask = async (taskId: number): Promise<void> => {
  await axios.delete(`${API_URL}/tasks/${taskId}`);
};
