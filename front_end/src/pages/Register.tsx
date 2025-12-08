import { useState, useCallback } from "react";
import { useNavigate } from "react-router-dom";
import {
  TextField,
  Button,
  Box,
  Typography,
  Paper,
  Alert,
  CircularProgress,
} from "@mui/material";
import { PersonAdd as PersonAddIcon } from "@mui/icons-material";
import { validateRegister } from "../schemas/validations";
import type { User } from "../types/user/user";
import { RegisterData } from "../services/userService";

const REDIRECT_DELAY = 2000;

export const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState<Record<string, string>>({});
  const [msgSucesso, setMsgSucesso] = useState<string>("");
  const [msgErro, setMsgErro] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const handleInputChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      const { name, value } = e.target;
      setFormData((prev) => ({ ...prev, [name]: value }));
      setErrors((prev) => ({ ...prev, [name]: "" }));
      setMsgErro("");
    },
    []
  );

  const handleSubmit = useCallback(
    async (event: React.FormEvent<HTMLFormElement>) => {
      event.preventDefault();

      const validation = validateRegister(formData);

      if (!validation.success) {
        setErrors(validation.errors);
        return;
      }

      setIsLoading(true);
      setMsgErro("");
      setMsgSucesso("");

      try {
        const registerData = {
          name: formData.name,
          email: formData.email,
          password: formData.password,
        };

        console.log("Enviando cadastro:", registerData);

        const user: User = await RegisterData(registerData);

        console.log("Resposta do cadastro:", user);

        setMsgSucesso(
          `Cadastro realizado com sucesso, ${user.name}! Redirecionando...`
        );

        setTimeout(() => {
          navigate("/");
        }, REDIRECT_DELAY);
      } catch (error) {
        console.error("Erro no cadastro:", error);
        setMsgErro(
          "Erro ao realizar cadastro. Verifique os dados e tente novamente."
        );
      } finally {
        setIsLoading(false);
      }
    },
    [formData, navigate]
  );

  const handleRedirectToLogin = () => {
    navigate("/");
  };

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="80vh"
    >
      <Paper elevation={2} sx={{ p: 3, width: 320 }}>
        <Box textAlign="center" mb={2}>
          <PersonAddIcon sx={{ fontSize: 36, color: "primary.main", mb: 1 }} />
          <Typography variant="h6" component="h2" fontWeight={600} mb={1}>
            Criar Conta
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Preencha os dados abaixo para se cadastrar
          </Typography>
        </Box>
        {msgSucesso && <Alert sx={{ mb: 2 }}>{msgSucesso}</Alert>}
        {msgErro && (
          <Alert severity="error" sx={{ mb: 2 }}>
            {msgErro}
          </Alert>
        )}
        <Box component="form" noValidate onSubmit={handleSubmit}>
          <TextField
            label="Nome"
            name="name"
            type="text"
            fullWidth
            margin="normal"
            value={formData.name}
            onChange={handleInputChange}
            error={!!errors.name}
            helperText={errors.name}
            disabled={isLoading}
          />
          <TextField
            label="Email"
            name="email"
            type="email"
            fullWidth
            margin="normal"
            value={formData.email}
            onChange={handleInputChange}
            error={!!errors.email}
            helperText={errors.email}
            disabled={isLoading}
          />
          <TextField
            label="Senha"
            name="password"
            type="password"
            fullWidth
            margin="normal"
            value={formData.password}
            onChange={handleInputChange}
            error={!!errors.password}
            helperText={errors.password}
            disabled={isLoading}
          />
          <Button
            type="submit"
            variant="contained"
            color="primary"
            fullWidth
            sx={{ mt: 2 }}
            disabled={isLoading}
          >
            {isLoading ? (
              <Box display="flex" alignItems="center" gap={1}>
                <CircularProgress size={20} color="inherit" />
                <span>Carregando...</span>
              </Box>
            ) : (
              "Cadastrar"
            )}
          </Button>
          <Button
            fullWidth
            sx={{ mt: 1 }}
            onClick={handleRedirectToLogin}
            disabled={isLoading}
          >
            Já tem uma conta? Faça login
          </Button>
        </Box>
      </Paper>
    </Box>
  );
};

export default Register;
