import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosConfig";
import "../css/Signup.css";

function Signup() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [passwordCheck, setCheckPassword] = useState("");
  const [errors, setErrors] = useState("");
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();

    if (!username.trim()) {
      alert("아이디를 입력해 주세요!");
      return;
    }
    if (!password.trim()) {
      alert("비밀번호를 입력해 주세요!");
      return;
    }
    if (!passwordCheck.trim()) {
      alert("비밀번호 확인을 입력해 주세요!");
      return;
    }

    setErrors({});
    try {
      await api.post("/api/auth/signup", { username, password, passwordCheck });
      alert("회원가입 성공!");
      navigate("/login");
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setErrors(err.response.data);
        if (errors.username) {
          alert(errors.username);
        }
        if (errors.password) {
          alert(errors.password);
        }
      } else {
        console.error("회원가입 실패! :", err);
        alert("회원가입 실패! ");
      }
    }
  };

  return (
    <div className="form-container">
      <h2>회원 가입</h2>
      <form onSubmit={handleSignup}>
        <input
          type="text"
          placeholder="아이디"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <p>
          <input
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </p>
        <input
          type="password"
          value={passwordCheck}
          onChange={(e) => setCheckPassword(e.target.value)}
          placeholder="비밀번호 확인"
        />

        <button type="submit">회원가입</button>
      </form>
    </div>
  );
}

export default Signup;
