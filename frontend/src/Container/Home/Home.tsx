import React, { useState, createContext } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme, darkTheme } from '../../Style/theme';
import * as S from './Style';
import Header from '../../Components/Header/Header';
import Footer from '../../Components/Footer/Footer';
import Editor from '@monaco-editor/react';

// 다른 컴포넌트와 테마 전환 설정 공유
export interface ThemeContextProps {
  theme: string;
  isLight: boolean;
  toggleTheme: any;
}
export const ThemeContext = createContext({} as ThemeContextProps);

const Home = () => {
  // 테마 전환 이벤트
  const [theme, setTheme] = useState('dark');
  const isLight = theme === 'light';
  const toggleTheme = () => {
    if (theme === 'light') {
      setTheme('dark');
    } else {
      setTheme('light');
    }
  };

  return (
    <ThemeContext.Provider value={{ theme, isLight, toggleTheme }}>
      <ThemeProvider theme={theme === 'light' ? lightTheme : darkTheme}>
        <S.Home>
          <Header />
          <div className="container">
            <div className="wrapper run">
              <div className="tab">
                <select>
                  <option value="java">JAVA</option>
                  <option value="python">Python</option>
                </select>
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
      </ThemeProvider>
    </ThemeContext.Provider>
  );
};

export default Home;
