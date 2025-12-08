import { z } from "zod";

export const taskSchema = z.object({
  title: z.string().min(1, "Título é obrigatório"),
  description: z.string().optional(),
  dateTime: z.string().min(1, "Data e hora são obrigatórias"),
  priority: z.enum(["HIGH", "MEDIUM", "LOW"]),
});
