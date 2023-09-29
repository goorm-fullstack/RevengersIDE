import React, { useState, createContext } from 'react';
import * as S from './Style';
import Header from '../../Components/Header/Header';

const Home = () => {
  return (
    <S.Home>
      <Header />
      <div className="container">
        <div className="wrapper run">
          <div className="tab">
            <button type="button">실행</button>
            {/** 일단 type button 후에 type은 변경될 수 있음 */}
          </div>
          <div className="editor">{/** 코드 편집기 영역 */}</div>
        </div>
        <div className="wrapper">
          <ul className="tab">
            <li>터미널</li>
          </ul>
          <div className="terminal">{/** 터미널 영역 */}</div>
        </div>
      </div>
    </S.Home>
  );
};

export default Home;
