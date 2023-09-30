import React from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const Login = () => {
  return (
    <S.Login>
      <Logo />
      <div>
        <form>
          <input type="text" placeholder="ID"></input>
          <input type="password" placeholder="Password"></input>
          <button type="submit">로그인</button>
        </form>
        <p>
          계정이 없으신가요? <Link to="/signup">회원가입</Link>
        </p>
      </div>
    </S.Login>
  );
};

export default Login;
