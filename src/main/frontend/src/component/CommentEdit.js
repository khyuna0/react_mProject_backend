import { useEffect, useState } from "react";
import "../css/CommentEdit.css";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosConfig";

function CommentEdit({
  user,
  setIsCommentEdit,
  content,
  id,
  username,
  createDate,
}) {
  const [editContent, SetEditContent] = useState(content);
  const navigate = useNavigate();

  const formatDate = (value) => {
    try {
      return String(value).substring(0, 10);
    } catch {
      return "-";
    }
  };
  // 댓글 수정
  const handleEdit = async () => {
    try {
      await api.post(`/api/comments/edit/${id}`, { content: editContent });
      alert("수정되었습니다.");
      setIsCommentEdit(false);
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
  }, []);

  return (
    <div>
      <form>
        <div className="comment-author">작성자 : {username}</div>
        <input
          className="comment-content"
          value={editContent}
          onChange={(e) => SetEditContent(e.target.value)}
        ></input>
        <div className="comment-date">등록일 : {formatDate(createDate)}</div>

        <button type="submit" onClick={handleEdit}>
          수정
        </button>
        <button onClick={() => setIsCommentEdit(false)}>취소</button>
      </form>
    </div>
  );
}

export default CommentEdit;
