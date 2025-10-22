import { useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import PostView from "../component/PostView";
import PostEdit from "../component/PostEdit";
import CommentList from "../component/CommentList";
import "../css/BoardDetail.css";
import api from "../api/axiosConfig";

function BoardDetail({ user }) {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [comments, setComments] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isEdit, setIsEdit] = useState(false);

  const loadPost = useCallback(async () => {
    try {
      setLoading(true);
      const res = await api.get(`/api/board/${id}`);
      setPost(res.data);
      setError(null);
    } catch (err) {
      console.error(err);
      setError("해당 게시글은 존재하지 않습니다.");
      setPost(null);
    } finally {
      setLoading(false);
    }
  }, [id]);

  const loadComments = useCallback(async () => {
    try {
      const res = await api.get(`/api/comments/${id}`);
      setComments(res.data ?? []);
    } catch (err) {
      console.error(err);
      setComments([]);
    }
  }, [id]);

  useEffect(() => {
    loadPost();
    loadComments();
  }, [loadPost, loadComments]);

  if (loading) return <div>로딩 중</div>;
  if (error) return <div>{error}</div>;
  if (!post) return <div>해당 게시글이 존재하지 않습니다.</div>;

  return (
    <div>
      {/* 게시글 영역 */}
      {!isEdit ? (
        <PostView
          setIsEdit={setIsEdit}
          post={post}
          user={user}
          loadComments={loadComments}
          comments={comments}
        />
      ) : (
        <PostEdit
          setIsEdit={setIsEdit}
          post={post}
          user={user}
          loadPost={loadPost}
        />
      )}
    </div>
  );
}

export default BoardDetail;
