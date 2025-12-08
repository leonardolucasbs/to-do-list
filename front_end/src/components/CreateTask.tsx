import React, { useState } from "react";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import MenuItem from "@mui/material/MenuItem";
import Stack from "@mui/material/Stack";

import { createTask } from "../services/taskService";
import type { Task } from "../types/task/task";
import type { TaskCreate } from "../types/task/taskCreate";
import type { TaskDTO } from "../types/task/taskDTO";

const modalStyle = {
  position: "absolute" as const,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 420,
  bgcolor: "background.paper",
  borderRadius: 3,
  boxShadow: 24,
  p: 4,
};

type CreateTaskModalProps = {
  userId: number;
  open: boolean;
  onClose: () => void;
  onCreated?: (task: Task) => void;
};

type FormState = {
  title: string;
  dateTime: string;
  priority: string; // HIGH | MEDIUM | LOW (como string)
  description: string;
};

const priorityOptions = [
  { label: "HIGH", value: "HIGH" },
  { label: "MEDIUM", value: "MEDIUM" },
  { label: "LOW", value: "LOW" },
];

export const CreateTaskModal: React.FC<CreateTaskModalProps> = ({
  userId,
  open,
  onClose,
  onCreated,
}) => {
  const [form, setForm] = useState<FormState>({
    title: "",
    dateTime: "",
    priority: "",
    description: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleChange = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    const { name, value } = event.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  const resetForm = () => {
    setForm({
      title: "",
      dateTime: "",
      priority: "",
      description: "",
    });
    setError(null);
  };

  const handleClose = () => {
    if (loading) return;
    resetForm();
    onClose();
  };

  const handleSubmit = async () => {
    if (!form.title || !form.dateTime || !form.priority) {
      setError("Title, date/time and priority are required.");
      return;
    }

    try {
      setLoading(true);
      setError(null);

      const dto: TaskDTO = {
        title: form.title,
        dateTime: form.dateTime,
        description: form.description,
        priority: form.priority as "HIGH" | "MEDIUM" | "LOW",
      };

      const payload: TaskCreate = {
        userId,
        task: dto,
      };

      console.log("Creating task with payload:", payload);
      const createdTask = await createTask(payload);

      if (onCreated) {
        onCreated(createdTask);
      }

      handleClose();
    } catch (err) {
      console.error(err);
      setError("Erro ao criar task. Tente novamente.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={modalStyle}>
        <Typography variant="h6" component="h2" mb={2}>
          Create new task
        </Typography>

        <Stack spacing={2}>
          <TextField
            label="Title"
            name="title"
            type="text"
            fullWidth
            value={form.title}
            onChange={handleChange}
          />

          <TextField
            label="Date Time"
            name="dateTime"
            type="datetime-local"
            fullWidth
            value={form.dateTime}
            onChange={handleChange}
            InputLabelProps={{ shrink: true }}
          />

          <TextField
            select
            label="Priority"
            name="priority"
            fullWidth
            value={form.priority}
            onChange={handleChange}
          >
            {priorityOptions.map((opt) => (
              <MenuItem key={opt.value} value={opt.value}>
                {opt.label}
              </MenuItem>
            ))}
          </TextField>

          <TextField
            label="Description"
            name="description"
            fullWidth
            multiline
            rows={3}
            value={form.description}
            onChange={handleChange}
          />

          {error && (
            <Typography variant="body2" color="error">
              {error}
            </Typography>
          )}

          <Box display="flex" justifyContent="flex-end" gap={1} mt={1}>
            <Button onClick={handleClose} disabled={loading}>
              Cancel
            </Button>
            <Button
              variant="contained"
              onClick={handleSubmit}
              disabled={loading}
            >
              {loading ? "Saving..." : "Save"}
            </Button>
          </Box>
        </Stack>
      </Box>
    </Modal>
  );
};
