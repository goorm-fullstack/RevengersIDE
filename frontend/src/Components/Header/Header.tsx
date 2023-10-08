import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../Logo/Logo';
import { ThemeContext, ThemeContextProps } from '../../Container/Home/Home';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지

const Header = () => {
  // 테마 전환: App.tsx에서 가져온 값
  const { toggleTheme, isLight } = useContext(ThemeContext);
  return (
    <S.Header>
      <div className="left">
        <Logo isAdmin={false} />
        <div className="darkmodew">
          {/* theme button: dark mode(default) */}
          <button type="button" onClick={toggleTheme} className={isLight ? 'dark' : 'light'}>
            <span>{/** 해/달 아이콘 영역 */}</span>
          </button>
        </div>
      </div>
      <div className="right">
        <div className="guest" data-isactive="true">
          <Link to="/login">로그인</Link>
          <Link to="/signup">회원가입</Link>
        </div>
        <div className="member" data-isactive="false">
          <Link to="/myaccount">홍구름 님</Link>
        </div>
      </div>
    </S.Header>
  );
};

export default Header;
