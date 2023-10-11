import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import Instance from '../Utils/api/axiosInstance';
import { useAuth } from '../Utils/api/AuthContext';

const LogoutBtn = () => {
  const [, removeCookie] = useCookies(['JSESSIONID']); // 로그인 쿠키
  const { setLoggedIn } = useAuth();

  // 로그아웃
  const Logout = () => {
    Instance.post('/ideApi/api/member/logout')
      .then((response) => {
        if (response.status === 200) {
          console.log('React: 로그아웃 성공');
        }
      })
      .catch((error) => {
        console.log(error.data);
      });
    removeCookie('JSESSIONID', localStorage['jsessionid'], { path: '/ideApi' });
    setLoggedIn(false);
    localStorage.setItem('isLoggedIn', 'false');
    // window.location.href = '/';
  };

  return (
    <button type="button" onClick={Logout}>
      로그아웃
    </button>
  );
};

export default LogoutBtn;
