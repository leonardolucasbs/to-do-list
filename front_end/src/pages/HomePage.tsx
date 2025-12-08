import React, { useEffect } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import { DataGrid } from "@mui/x-data-grid";
import type { GridColDef } from "@mui/x-data-grid";
import Paper from "@mui/material/Paper";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/useAuth";
import { getTasks } from "../services/taskService";
import type { Task } from "../types/task/task";
import AddIcon from "@mui/icons-material/Add";
import Modal from "@mui/material/Modal";
import Button from "@mui/material/Button";
import Fab from "@mui/material/Fab";

import { CreateTaskModal } from "../components/CreateTask";

const modalStyle = {
  position: "absolute" as const,
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "1px solid #000",
  boxShadow: 24,
  borderRadius: 3,
  p: 4,
};

type RowType = {
  id: number;
  Task: string;
  Date: string;
  priority: string;
  description: string;
};

const paginationModel = { page: 2, pageSize: 5 };

export const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const { user } = useAuth();

  const [openCreate, setOpenCreate] = React.useState(false);
  const [rows, setRows] = React.useState<RowType[]>([]);
  const [open, setOpen] = React.useState(false);
  const [selectedTask, setSelectedTask] = React.useState<RowType | null>(null);

  const handleOpenModalForViewMore = (rowData: RowType) => {
    setSelectedTask(rowData);
    setOpen(true);
  };

  const handleCloseModalForViewMore = () => {
    setOpen(false);
    setSelectedTask(null);
  };

  useEffect(() => {
    if (!user) {
      navigate("/");
      return;
    }

    const fetchTasks = async () => {
      const tasks = await getTasks(user.id);

      const mappedRows: RowType[] = tasks.map((task: Task, index: number) => ({
        id: index + 1,
        Task: task.title,
        Date: new Date(task.dateTime).toLocaleDateString("pt-BR"),
        priority: task.priority,
        description: task.description ?? "",
      }));

      setRows(mappedRows);
    };

    fetchTasks();
  }, [user, navigate]);

  if (!user) return null;

  const columns: GridColDef[] = [
    { field: "Task", headerName: "Task", width: 200 },
    { field: "Date", headerName: "Date", width: 200 },
    {
      field: "priority",
      headerName: "Priority",
      type: "string",
      width: 130,
    },
    {
      field: "actions",
      headerName: "Details",
      width: 150,
      sortable: false,
      filterable: false,
      renderCell: (params) => (
        <Button
          variant="outlined"
          size="small"
          onClick={() => handleOpenModalForViewMore(params.row as RowType)}
        >
          see more
        </Button>
      ),
    },
  ];

  return (
    <>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static">
          <Toolbar variant="dense">
            <IconButton
              edge="start"
              color="inherit"
              aria-label="menu"
              sx={{ mr: 2 }}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" color="inherit" component="div">
              Organizes
            </Typography>
          </Toolbar>
        </AppBar>
      </Box>

      <Fab
        color="primary"
        aria-label="add"
        onClick={() => setOpenCreate(true)}
        sx={{
          position: "fixed",
          top: 16,
          right: 16,
          zIndex: 9999,
        }}
      >
        <AddIcon />
      </Fab>

      {/* Modal de criar tarefa */}
      <CreateTaskModal
        userId={user.id}
        open={openCreate}
        onClose={() => setOpenCreate(false)}
        onCreated={(task: Task) => {
          setRows((prev) => [
            ...prev,
            {
              id: prev.length + 1,
              Task: task.title,
              Date: new Date(task.dateTime).toLocaleDateString("pt-BR"),
              priority: task.priority,
              description: task.description ?? "",
            },
          ]);
        }}
      />

      {/* DataGrid */}
      <Paper sx={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={rows}
          columns={columns}
          initialState={{ pagination: { paginationModel } }}
          pageSizeOptions={[5, 10]}
          checkboxSelection
          disableRowSelectionOnClick
          sx={{ border: 0 }}
        />
      </Paper>

      <Modal
        open={open}
        onClose={handleCloseModalForViewMore}
        aria-labelledby="task-modal-title"
        aria-describedby="task-modal-description"
      >
        <Box sx={modalStyle}>
          <Typography id="task-modal-title" variant="h6" component="h2">
            {selectedTask?.Task ?? "task details"}
          </Typography>

          <Typography sx={{ mt: 1 }} variant="body2" color="text.secondary">
            Date: {selectedTask?.Date}
          </Typography>

          <Typography sx={{ mt: 1 }} variant="body2" color="text.secondary">
            Priority: {selectedTask?.priority}
          </Typography>

          <Typography id="task-modal-description" sx={{ mt: 2 }}>
            {selectedTask?.description || "No description."}
          </Typography>
        </Box>
      </Modal>
    </>
  );
};
