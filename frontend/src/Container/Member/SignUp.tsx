import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const SignUp = () => {
  return (
    <S.SignUp>
      <div className="w">
        <Logo isAdmin={false} />
        <div>
          <form action="/api/member/signup" method="POST">
            <input type="text" name="memberId" placeholder="아이디"></input>
            <input type="text" name="memberName" placeholder="고객명"></input>
            <input type="password" name="password" placeholder="비밀번호"></input>
            <input type="password" name="passwordCheck" placeholder="비밀번호 확인"></input>
            <input type="text" name="email" placeholder="이메일"></input>
            <button type="submit">회원가입</button>
          </form>
        </div>
      </div>
    </S.SignUp>
  );
};

export default SignUp;
