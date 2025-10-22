import "../css/Footer.css"

function Footer() {
    return (
        <>
          <footer className="footer">
        <div className="container footer-inner">
        <p className="footer-brand">Moim</p>
        <nav className="footer-nav">
        <a href="#">이용약관</a>
        <a href="#">개인정보처리방침</a>
        <a href="#">문의</a>
      </nav>
    </div>
    </footer>
    </>
    );
}

export default Footer;