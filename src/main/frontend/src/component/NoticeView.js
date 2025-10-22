import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosConfig";

function NoticeView({ setIsEdit, user }) {
  // props - 게시판 타입, user, 게시판 번호
  const { id } = useParams();
  const [post, setPost] = useState({});
  const navigate = useNavigate();

  const getpost = async () => {
    try {
      const res = await api.get(`/api/notice/${id}`);
      setPost(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(async () => {
    await getpost();
  }, []);

  return (
    <div className="board-detail-container">
      <h2>게시판 상세</h2>
      <label htmlFor="content" className="label-title">
        제목
      </label>
      <div className="input-title">{post.title}</div>

      <label htmlFor="content" className="label-content">
        내용
      </label>
      <div className="textarea-content" rows="10">
        {post.title}
      </div>

      <div className="btn-group">
        {user === post.author?.username && (
          <>
            <button className="btn btn-edit" onClick={() => setIsEdit(true)}>
              수정
            </button>
            <button className="btn btn-delete">삭제</button>
          </>
        )}
        <button className="btn btn-list" onClick={() => navigate("/board")}>
          목록
        </button>
      </div>
    </div>
  );
}

export default NoticeView;
