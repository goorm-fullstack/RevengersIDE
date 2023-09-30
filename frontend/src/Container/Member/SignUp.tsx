import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';

const SignUp = () => {
  return (
    <S.SignUp>
      <Logo />
      <div>
        <form>
          <input type="text" placeholder="ID"></input>
          <input type="text" placeholder="Name"></input>
          <input type="password" placeholder="Password"></input>
          <input type="password" placeholder="Confirm Password"></input>
          <input type="text" placeholder="Email"></input>
          <button type="submit">회원가입</button>
        </form>
      </div>
    </S.SignUp>
  );
};

export default SignUp;
