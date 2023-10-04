import React from 'react';
import { Link } from 'react-router-dom';
import * as S from './Style';

interface LogoProps {
  isAdmin: boolean;
}

const Logo: React.FC<LogoProps> = ({ isAdmin }) => {
  return (
    <S.Logo>
      <Link to={isAdmin ? '/admin/login' : '/'}>
        <strong>REVENGERS</strong> IDE {isAdmin ? <strong>ADMIN</strong> : ''}
      </Link>
    </S.Logo>
  );
};

export default Logo;
