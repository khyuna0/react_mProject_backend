import { useEffect, useState } from "react";
import "../css/CommentList.css";
import api from "../api/axiosConfig";
import { useNavigate } from "react-router-dom";
import CommentEdit from "./CommentEdit";

function CommentList({ comments, loadComments, id, user }) {
  const [content, setContent] = useState("");
  const [isCommentEdit, setIsCommentEdit] = useState(false);
  const [commentErrors, setCommentErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    loadComments();
    setIsCommentEdit();
  }, [loadComments, setIsCommentEdit]);

  // 댓글 작성
  const handleCreate = async (e) => {
    e.preventDefault();
    setCommentErrors({});
    if (!user) {
      alert("로그인 후에 댓글 작성 가능합니다.");
      navigate("/login");
      return;
    }
    if (!content.trim()) {
      alert("내용을 입력해 주세요");
      return;
    }
    try {
      await api.post(`/api/comments/${id}`, { content });
      setContent("");
      loadComments();
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setCommentErrors(err.response.data);
        console.error(commentErrors);
      } else {
        console.error(e);
        alert("댓글 등록 실패");
      }
    }
  };

  // 댓글 삭제
  const handleDelete = async (commentId) => {
    try {
      if (!window.confirm("정말 삭제하시겠습니까?")) return;
      await api.delete(`/api/comments/${commentId}`);
      await loadComments();
    } catch (err) {
      console.error(err);
      alert("댓글 삭제 실패");
    }
  };

  const formatDate = (value) => {
    try {
      return String(value).substring(0, 10);
    } catch {
      return "-";
    }
  };

  return (
    <div className="comment-section">
      {/* 댓글 작성 영역 */}
      <div className="comment-form">
        <textarea
          placeholder="댓글을 입력하세요"
          value={content}
          onChange={(e) => setContent(e.target.value)}
        />
        <button onClick={handleCreate}>댓글 등록</button>
      </div>

      {/* 댓글 리스트 */}
      <ul className="comment-list">
        {(!comments || comments.length === 0) && (
          <li className="comment-empty">아직 댓글이 없습니다.</li>
        )}

        {comments?.map((c) => (
          <li key={c.id} className="comment-item">
            <div className="comment-header">
              <div className="comment-author">작성자 : {c.author.username}</div>
              <div className="comment-content">{c.content}</div>
              <div className="comment-date">
                등록일 : {formatDate(c.createDate)}
              </div>

              {/* {isCommentEdit && (
                <CommentEdit
                  setIsCommentEdit={setIsCommentEdit}
                  username={c.author.username}
                  createDate={c.createDate}
                  content={c.content}
                  user={user}
                  id={c.id}
                />
              )} */}

              {user === c.author?.username && (
                <div className="comment-actions">
                  {/* <button
                    className="btn-edit"
                    onClick={() => setIsCommentEdit(true)}
                  >
                    수정
                  </button> */}
                  <button
                    className="btn-delete"
                    onClick={() => handleDelete(c.id)}
                  >
                    삭제
                  </button>
                </div>
              )}
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CommentList;
