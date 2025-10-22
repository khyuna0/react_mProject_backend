import { Link } from "react-router-dom";
import "../css/Navbar.css";

function Navbar({ handleLogout, user }) {
  return (
    <div className="navbar">
      <div className="navigate">
        <div className="logo">
          <Link to="/">Moim</Link>
        </div>

        <div className="menu">
          <Link to="/board">게시판</Link>
          <Link to="/notice">공지사항</Link>
          {!user && <Link to="/login">로그인</Link>}
          {!user && <Link to="/signup">회원가입</Link>}
          {user && (
            <>
              <button className="logout_btn" onClick={() => handleLogout()}>
                로그아웃
              </button>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default Navbar;
