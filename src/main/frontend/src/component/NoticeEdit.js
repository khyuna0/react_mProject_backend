import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosConfig";

function NoticeEdit({ setIsEdit, user }) {
  const [title, setTitle] = useState(post.title);
  const [content, setContent] = useState(post.content);
  const [post, setPost] = useState({});
  const navigate = useNavigate();
  const { id } = useParams();

  const getpost = async () => {
    try {
      const res = await api.get(`/api/notice/${id}`);
      setPost(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (!user) {
      alert("로그인 후에 이용 가능합니다.");
      navigate("/login");
      return;
    }
    getpost();
  }, []);

  const handleEdit = () => {};

  return (
    <div className="board-write-wrapper">
      <h2>게시판 수정</h2>
      <form onClick={handleEdit}>
        <label for="title">제목</label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="제목을 입력하세요"
          required
        />

        <label for="content">내용</label>
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          rows="10"
          placeholder="내용을 입력하세요"
          required
        ></textarea>

        <div class="btn-group">
          <button type="submit">등록</button>
          <button type="reset" onClick={() => setIsEdit(false)}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
}

export default NoticeEdit;
