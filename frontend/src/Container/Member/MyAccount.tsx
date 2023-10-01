import React from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const MyAccount = () => {
  return (
    <S.MyAccount>
      <div className="w">
        <Logo />
        <div>
          <form>
            <input type="text" placeholder="아이디"></input>
            <input type="text" placeholder="고객명"></input>
            <input type="password" placeholder="비밀번호"></input>
            <input type="password" placeholder="비밀번호 확인"></input>
            <input type="text" placeholder="이메일"></input>
            <button type="submit">회원정보수정</button>
          </form>
          <p className="link">
            <Link to="/">탈퇴하기</Link>
          </p>
        </div>
      </div>
    </S.MyAccount>
  );
};

export default MyAccount;
