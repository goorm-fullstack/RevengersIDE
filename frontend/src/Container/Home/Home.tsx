import React, { useState, createContext } from 'react';
import { ThemeProvider } from 'styled-components';
import { lightTheme, darkTheme } from '../../Style/theme';
import * as S from './Style';
import Header from '../../Components/Header/Header';
import Footer from '../../Components/Footer/Footer';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import Instance from '../../Utils/api/axiosInstance';
import axios from 'axios';

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
  const [lang, setLang] = useState('java');
  const [code, setCode] = useState('');
  const [result, setResult] = useState('');
  const isLight = theme === 'light';
  const toggleTheme = () => {
    if (theme === 'light') {
      setTheme('dark');
    } else {
      setTheme('light');
    }
  };

  const handleRunCode = () => {
    setResult('loading...');
    const data = {
      source: code,
      language: lang,
    };
    const jsonData = JSON.stringify(data);
    Instance.post(`/ideApi/docker/${lang}`, jsonData, {
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      const output = JSON.stringify(response.data);
      const data = JSON.parse(output);
      setResult(output);
    });
  };

  const handleCodeInput = (newCode: string | undefined) => {
    if (newCode !== undefined) {
      setCode(newCode);
    }
  };

  const handleLanguageChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setLang(event.target.value);
  };

  return (
    <ThemeContext.Provider value={{ theme, isLight, toggleTheme }}>
      <ThemeProvider theme={theme === 'light' ? lightTheme : darkTheme}>
        <S.Home>
          <Header />
          <div className="container">
            <div className="wrapper run">
              <div className="tab">
                <select value={lang} onChange={handleLanguageChange}>
                  <option value="java">JAVA</option>
                  <option value="python">Python</option>
                </select>
                <button type="button" onClick={handleRunCode}>
                  실행
                </button>
                {/** 일단 type button 후에 type은 변경될 수 있음 */}
              </div>
              <div className="editor">
                {/** 코드 편집기 영역 */}
                <Editor
                  height="100%"
                  theme={isLight ? 'light' : 'vs-dark'}
                  defaultLanguage="java"
                  defaultValue="// hello, world!"
                  value={code}
                  onChange={handleCodeInput}
                />
              </div>
            </div>
            <div className="wrapper">
              <ul className="tab">
                <li>
                  <h2>터미널</h2>
                </li>
              </ul>
              {result !== '' ? <div className="terminal">{result}</div> : <div className="terminal"></div>}
            </div>
          </div>
          <Footer />
        </S.Home>
      </ThemeProvider>
    </ThemeContext.Provider>
  );
};

export default Home;
