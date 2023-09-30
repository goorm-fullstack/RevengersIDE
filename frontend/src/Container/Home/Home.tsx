import React, { useState, useContext } from 'react';
import * as S from './Style';
import Header from '../../Components/Header/Header';
import Footer from '../../Components/Footer/Footer';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import { ThemeContext, ThemeContextProps } from '../../App';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지

const Home = () => {
  // 테마 상태: App.tsx에서 가져온 값
  const { isLight } = useContext(ThemeContext);

  return (
    <S.Home>
      <Header />
      <div className="container">
        <div className="wrapper run">
          <div className="tab">
            <button type="button">실행</button>
            {/** 일단 type button 후에 type은 변경될 수 있음 */}
          </div>
          <div className="editor">
            {/** 코드 편집기 영역 */}
            <Editor height="100%" theme={isLight ? 'light' : 'vs-dark'} defaultLanguage="javascript" defaultValue="// hello, world!" />
          </div>
        </div>
        <div className="wrapper">
          <ul className="tab">
            <li>
              <h2>터미널</h2>
            </li>
          </ul>
          <div className="terminal">{/** 터미널 영역 */}</div>
        </div>
      </div>
      <Footer />
    </S.Home>
  );
};

export default Home;
