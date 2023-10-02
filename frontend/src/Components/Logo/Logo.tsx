import React, { useState, useContext } from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';

const Logo = () => {
  return (
    <S.Logo>
      <Link to="/">
        <strong>REVENGERS</strong> IDE
      </Link>
    </S.Logo>
  );
};

export default Logo;
