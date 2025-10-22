import { useEffect, useState, useCallback } from "react";
import { useParams } from "react-router-dom";
import "../css/BoardDetail.css";
import api from "../api/axiosConfig";
import NoticeView from "../component/NoticeView.js";
import NoticeEdit from "../component/NoticeEdit.js";

function NoticeDetail({ user }) {
  const { id } = useParams();
  const [post, setPost] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isEdit, setIsEdit] = useState(false);

  const loadPost = useCallback(async () => {
    try {
      setLoading(true);
      const res = await api.get(`/api/notice/${id}`);
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

  useEffect(() => {
    loadPost();
  }, [loadPost]);

  if (loading) return <div>로딩 중</div>;
  if (error) return <div>{error}</div>;
  if (!post) return <div>해당 게시글이 존재하지 않습니다.</div>;

  return (
    <div>
      {/* 게시글 영역 */}
      {!isEdit ? (
        <NoticeView setIsEdit={setIsEdit} post={post} user={user} />
      ) : (
        <NoticeEdit setIsEdit={setIsEdit} post={post} user={user} />
      )}
    </div>
  );
}

export default NoticeDetail;
