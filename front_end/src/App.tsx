import { Routes, Route } from "react-router-dom";
import { Login } from "../src/pages/Login";
import { Register } from "../src/pages/Register";
import { HomePage } from "./pages/HomePage";
import "./App.css";

function App() {
  console.log("App renderizado");
  return (
    <div style={{ backgroundColor: "#f5f5f5", minHeight: "100vh" }}>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<HomePage />} />
      </Routes>
    </div>
  );
}

export default App;
