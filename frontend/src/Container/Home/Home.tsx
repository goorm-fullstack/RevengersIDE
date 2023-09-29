import React, { useState, createContext } from 'react';
import * as S from './Style';
import Header from '../../Components/Header/Header';

const Home = () => {
  return (
    <S.Home>
      <Header />
      <div>
        <div></div>
        <div>
          <ul>
            <li>터미널</li>
          </ul>
          <div></div>
        </div>
      </div>
    </S.Home>
  );
};

export default Home;
