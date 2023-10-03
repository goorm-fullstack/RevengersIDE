import React from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const Login = () => {
  return (
    <S.Login>
      <div className="w">
        <Logo isAdmin={false} />    {/* 관리자의 로그인인지 확인 변수 전송 */}
        <div>
          <form>
            <input type="text" placeholder="아이디"></input>
            <input type="password" placeholder="비밀번호"></input>
            <button type="submit">로그인</button>
          </form>
          <p className="link">
            계정이 없으신가요? <Link to="/signup">회원가입</Link>
          </p>
        </div>
      </div>
    </S.Login>
  );
};

export default Login;
