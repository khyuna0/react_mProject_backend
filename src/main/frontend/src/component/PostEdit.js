import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosConfig";

function PostEdit({ setIsEdit, user, post, loadPost }) {
  const [title, setTitle] = useState(post.title);
  const [content, setContent] = useState(post.content);
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (!user) {
      alert("로그인 후에 이용 가능합니다.");
      navigate("/login");
      return;
    }
  }, []);

  const handleEdit = async () => {
    if (!window.confirm("정말 수정하시겠습니까?")) {
      setIsEdit(false);
      return;
    }
    try {
      await api.post(`/api/board/${id}`, { title, content });
      loadPost();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="board-write-wrapper">
      <h2>게시판 수정</h2>
      <form>
        <div>제목</div>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목을 입력하세요"
          required
        />

        <div>내용</div>
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          rows="10"
          placeholder="내용을 입력하세요"
          required
        ></textarea>

        <div className="btn-group">
          <button type="submit" onClick={handleEdit}>
            등록
          </button>
          <button type="reset" onClick={() => setIsEdit(false)}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
}

export default PostEdit;
