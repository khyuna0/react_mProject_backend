import { Link, useNavigate } from "react-router-dom";
import "../css/Board.css";
import api from "../api/axiosConfig";
import { useEffect, useState } from "react";

function Notice({ user }) {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalItems, setTotalItems] = useState(0);
  const navigate = useNavigate();

  // 글쓰기 버튼 이동
  const handleWrite = () => {
    if (!user) {
      alert("로그인 후에 이용 가능합니다.");
      navigate("/login");
      return;
    }
    navigate("/notice/write");
  };

  // 게시글 리스트
  const getPosts = async (page = 0) => {
    try {
      setLoading(true);
      const res = await api.get(`/api/notice?page=${page}&size=10`);
      setPosts(res.data.posts);
      if (!Array.isArray(res.data.posts)) {
        console.error("posts 배열 아님", res.data.posts);
        setPosts([]);
        return;
      }
      setCurrentPage(res.data.currentPage);
      setTotalPages(res.data.totalPages);
      setTotalItems(res.data.totalItems);
    } catch (err) {
      console.error(err);
      setError("게시글을 불러오는 데 실패하였습니다.");
      setPosts([]);
    } finally {
      setLoading(false);
    }
  };

  const getPageNumbers = () => {
    const startPage = Math.floor(currentPage / 10) * 10;
    const endPage = startPage + 10 > totalPages ? totalPages : startPage + 10;
    const pages = [];
    for (let i = startPage; i < endPage; i++) {
      pages.push(i);
    }
    return pages;
  };

  // 날짜 포맷
  const formatDate = (value) => {
    try {
      return String(value).substring(0, 10);
    } catch {
      return "-";
    }
  };

  useEffect(() => {
    getPosts(currentPage);
  }, [currentPage]);

  return (
    <div className="board-container">
      <div className="boardList-wrapper">
        <table className="board-table">
          <thead className="boardList-thead">
            <tr>
              <th>번호</th>
              <th>제목</th>
              <th>글쓴이</th>
              <th>조회수</th>
              <th>작성일</th>
            </tr>
          </thead>
          <tbody className="boardList-tbody">
            {posts.length > 0 ? (
              posts.slice().map((p, index) => (
                <tr key={p.id}>
                  <td>{totalItems - (index + 10 * currentPage)}</td>
                  <td
                    className="click-title"
                    onClick={() => navigate(`/NoticeDetail/${p.id}`)}
                  >
                    {p.title}
                  </td>
                  <td>{p.author.username}</td>
                  <td>{p.hit ?? 0}</td>
                  <td>{formatDate(p.createDate)}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5">게시물이 없습니다.</td>
              </tr>
            )}
          </tbody>
        </table>
        {/* 페이지 번호와 이동 화살표 출력 */}
        <div className="pagination">
          {/* 첫 번째 페이지로 */}
          <button
            onClick={() => setCurrentPage(0)}
            disabled={currentPage === 0}
          >
            ◀◀
          </button>
          {/* 이전 */}
          <button
            onClick={() => setCurrentPage(currentPage - 1)}
            disabled={currentPage === 0}
          >
            ◀
          </button>
          {/* 페이지 번호 그룹 10개씩 출력 기능 */}
          {getPageNumbers().map((num) => (
            <button
              className={num === currentPage ? "active" : ""}
              key={num}
              onClick={() => setCurrentPage(num)}
            >
              {num + 1}
            </button>
          ))}
          <button
            onClick={() => setCurrentPage(currentPage + 1)}
            disabled={currentPage === totalPages - 1 || totalPages === 0}
          >
            ▶
          </button>
          <button
            onClick={() => setCurrentPage(Math.max(0, totalPages - 1))}
            disabled={currentPage === totalPages - 1 || totalPages === 0}
          >
            ▶▶
          </button>
        </div>

        <div className="board-btn-wrapper">
          <button className="board-write-btn" onClick={handleWrite}>
            글쓰기
          </button>
        </div>
      </div>
    </div>
  );
}

export default Notice;
