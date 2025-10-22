import { Route, Routes } from "react-router-dom";
import "./App.css";
import Home from "./pages/Home";
import Navbar from "./pages/Navbar";
import Login from "./pages/Login";
import BoardWrite from "./pages/BoardWrite";
import Board from "./pages/Board";
import BoardDetail from "./pages/BoardDetail";
import PostEdit from "./component/PostEdit";
import Signup from "./pages/Signup";
import { useEffect, useState } from "react";
import api from "./api/axiosConfig";
import Footer from "./component/Footer";
import Notice from "./pages/Notice";
import NoticeDetail from "./pages/NoticeDetail";
import NoticeWrite from "./pages/NoticeWrite";

function App() {
  // 로그인
  const [user, setUser] = useState(null);
  // const [boardType, setBoardType] = useState("default");

  const checkUser = async () => {
    try {
      const res = await api.get("/api/auth/me");
      setUser(res.data.username);
    } catch {
      setUser(null);
    }
  };

  useEffect(() => {
    checkUser();
  }, []);

  // 로그아웃
  const handleLogout = async () => {
    await api.post("/api/auth/logout");
    setUser(null);
    alert("로그아웃");
  };

  return (
    <div className="App">
      <Navbar handleLogout={handleLogout} user={user} />
      <Routes>
        <Route path="/" element={<Home user={user} />} />
        <Route path="/login" element={<Login onLogin={setUser} />} />
        <Route path="/board" element={<Board user={user} />} />
        <Route path="/notice" element={<Notice user={user} />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/board/write" element={<BoardWrite user={user} />} />
        <Route path="/notice/write" element={<NoticeWrite user={user} />} />
        <Route path="/boardDetail/:id" element={<BoardDetail user={user} />} />
        <Route
          path="/noticeDetail/:id"
          element={<NoticeDetail user={user} />}
        />
        <Route path="/postEdit" element={<PostEdit user={user} />} />
      </Routes>
      <Footer></Footer>
    </div>
  );
}

export default App;
