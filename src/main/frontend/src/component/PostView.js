import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosConfig";
import CommentList from "./CommentList";

function PostView({ setIsEdit, user, post, comments, loadComments }) {
  const { id } = useParams();
  const navigate = useNavigate();

  // 게시글 삭제

  const handleDelete = async () => {
    try {
      if (!window.confirm("정말 삭제하시겠습니까?")) {
        return;
      }
      await api.delete(`/api/board/${id}`);
      navigate("/board");
    } catch (err) {
      console.error(err);
    }
  };

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
            <button className="btn btn-delete" onClick={() => handleDelete()}>
              삭제
            </button>
          </>
        )}
        <button className="btn btn-list" onClick={() => navigate("/board")}>
          목록
        </button>
      </div>

      {/* 댓글 */}

      {/* 댓글 영역 */}
      <CommentList
        loadComments={loadComments}
        comments={comments}
        user={user}
        id={post.id}
      />
    </div>
  );
}

export default PostView;
