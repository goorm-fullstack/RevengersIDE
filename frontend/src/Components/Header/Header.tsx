import React, { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../Logo/Logo';
import { ThemeContext, ThemeContextProps } from '../../Container/Home/Home';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지
import Instance from '../../Utils/api/axiosInstance';
import axios from 'axios';
import LogoutBtn from '../LogoutBtn';
import { useAuth } from '../../Utils/api/AuthContext';

const Header = () => {
  const { toggleTheme, isLight } = useContext(ThemeContext); // 테마 전환: App.tsx에서 가져온 값
  const [isLoggedIn, setLogState] = useState(false); // 로그인 유무 체크
  const [logMemberName, setLogMemberName] = useState(); // 회원명(아이디)
  const {setLoggedIn} = useAuth();

  // 회원 이름(아이디) 가져오기
  axios.get('/ideApi/api/member/', { withCredentials: true })
    .then((response) => {
      // console.log(response);
      if (response.status === 200) {
        if (response.data === '') {
          // 세션 쿠키 없음
          setLogState(false);
          setLoggedIn(false);
          localStorage.setItem('isLoggedIn', 'false');
        } else {
          // 세션 쿠키 있음
          // console.log('헤더:' + response.data);
          setLogMemberName(response.data);
          setLogState(true);
          setLoggedIn(true);
          localStorage.setItem('isLoggedIn', 'true');
        }
      }
    })
    .catch((error) => {
      console.log(error.data);
      setLogState(false);
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
            <span>
              <Link to="/myaccount">{logMemberName}</Link> 님
            </span>
            <LogoutBtn />
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
