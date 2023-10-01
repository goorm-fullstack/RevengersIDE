import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const SignUp = () => {
  return (
    <S.SignUp>
      <div className="w">
        <Logo />
        <div>
          <form>
            <input type="text" placeholder="아이디"></input>
            <input type="text" placeholder="고객명"></input>
            <input type="password" placeholder="비밀번호"></input>
            <input type="password" placeholder="비밀번호 확인"></input>
            <input type="text" placeholder="이메일"></input>
            <button type="submit">회원가입</button>
          </form>
        </div>
      </div>
    </S.SignUp>
  );
};

export default SignUp;
