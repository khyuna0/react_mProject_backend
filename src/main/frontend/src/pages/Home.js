import "../css/Home.css";
import Board from "./Board";

function Home({ user }) {
  return (
    <div className="home-wrapper">
      {/* 상단 소개 섹션 */}
      <section className="intro-section">
        <div className="intro-text">
          <h1 className="intro-title">Moim - 우리동네 취미 모임 플랫폼</h1>
          <p className="intro-sub">
            Moim은 지역과 관심사에 맞는 소모임을 쉽게 찾고 참여할 수 있는
            커뮤니티 플랫폼입니다. <br />
            이웃과 함께하는 운동, 취미, 스터디, 반려생활까지! <br />
            오프라인의 생생한 만남과 교류의 장을 지금 열어보세요.
          </p>
          <p className="intro-location">📍 서울특별시 송파구 근처 모임</p>
        </div>
        <div className="intro-image">
          <img
            src="https://cdn-icons-png.flaticon.com/512/3176/3176367.png"
            alt="소모임 일러스트"
          />
        </div>
      </section>

      {/* 게시판 컴포넌트 */}
      <section className="board-section">
        <h1 className="board-title">최근 게시글</h1>
        <Board user={user} />
      </section>
    </div>
  );
}

export default Home;
