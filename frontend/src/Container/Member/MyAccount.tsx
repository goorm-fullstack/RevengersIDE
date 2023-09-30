import React from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const MyAccount = () => {
  return (
    <S.MyAccount>
      <div>
        <Logo />
      </div>
      <div>
        <h2>My Account</h2>
        <form>
          <input type="text" placeholder="ID"></input>
          <input type="text" placeholder="Name"></input>
          <input type="password" placeholder="Password"></input>
          <input type="password" placeholder="Confirm Password"></input>
          <input type="text" placeholder="Email"></input>
          <button type="submit">회원정보수정</button>
        </form>
        <p>
          <Link to="/">탈퇴하기</Link>
        </p>
      </div>
    </S.MyAccount>
  );
};

export default MyAccount;
