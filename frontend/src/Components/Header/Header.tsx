import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import { ThemeContext, ThemeContextProps } from '../../App';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지

const Header = () => {
  // 테마 전환: App.tsx에서 가져온 값
  const { toggleTheme, isLight } = useContext(ThemeContext);
  return (
    <S.Header>
      <div className="left">
        <h1>
          <Link to="/">
            <strong>REVENGERS</strong> IDE
          </Link>
        </h1>
        <div>
          {/* theme button: dark mode(default), 디자인 수정 예정 */}
          <button type="button" onClick={toggleTheme} className={isLight ? 'dark' : 'light'}>
            {isLight ? 'Dark 🌚 ' : 'Light 🌝'}
          </button>
        </div>
      </div>
      <div className="right">
        <div className="guest">
          <Link to="/login">로그인</Link>
          <Link to="/signup">회원가입</Link>
        </div>
      </div>
    </S.Header>
  );
};

export default Header;
