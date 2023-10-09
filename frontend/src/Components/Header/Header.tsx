import React, { useContext } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../Logo/Logo';
import { ThemeContext, ThemeContextProps } from '../../Container/Home/Home';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지
import Instance from '../../Utils/api/axiosInstance';

const Header = () => {
  // 테마 전환: App.tsx에서 가져온 값
  const { toggleTheme, isLight } = useContext(ThemeContext);
  const isLoggedIn = false;

  Instance.get('/ideApi/api/member/')
    .then((response) => {
      console.log(response.data);
      if (response.status === 200) {
        console.log('성공:' + response.data);
      }
    })
    .catch((error) => {
      console.log(error.data);
    });

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
        {isLoggedIn ? (
          <div className="member">
            <Link to="/myaccount">홍구름</Link> 님<button type="button">로그아웃</button>
          </div>
        ) : (
          <div className="guest">
            <Link to="/login">로그인</Link>
            <Link to="/signup">회원가입</Link>
          </div>
        )}
      </div>
    </S.Header>
  );
};

export default Header;
