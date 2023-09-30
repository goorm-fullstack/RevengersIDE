import React, { useState, useContext, useRef } from 'react';
import * as S from './Style';
import Header from '../../Components/Header/Header';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import { ThemeContext, ThemeContextProps } from '../../App';
import axios from 'axios';
// 미사용으로 체크되지만 ThemeContextProps(type 정의) 값도 있어야 합니다. => import에서 삭제 금지

interface Source{
  source: string;
  languageType: string;
  fileName: string;
}

interface Result{
  standardOutput: string;
  standardError: string;
  exceptions: string;
}

const Home = () => {
  // 테마 상태: App.tsx에서 가져온 값
  const { isLight } = useContext(ThemeContext);
  const [sourceData, setSourceData] = useState<Source | null>(null);
  const [result, setResult] = useState<Result | null>(null);
  const editorRef = useRef<any>(null);

  const fetchResult = async () => {
    if(editorRef.current){
      const editorValue = editorRef.current.getValue();
      const source: Source = {
        source: editorValue,
        languageType: 'Java',
        fileName: 'HelloWorld',
      };
      setSourceData(prevSource => ({...prevSource, ...source}));
      console.log('Editor Value:', editorValue);
      try{
        console.log(source);
        const response = await axios.post('http://localhost:8080/docker/java', source)
        setResult(response.data);
      }catch(error){
        console.error('Error:', error);
      }
    }
  }

  const handleGetResult = () => {
    fetchResult();
  }

  return (
    <S.Home>
      <Header />
      <div className="container">
        <div className="wrapper run">
          <div className="tab">
            <button type="button" onClick={handleGetResult}>실행</button>
            {/** 일단 type button 후에 type은 변경될 수 있음 */}
          </div>
          <div className="editor">
            {/** 코드 편집기 영역 */}
            <Editor height="100%" theme={isLight ? 'light' : 'vs-dark'} defaultLanguage="javascript" defaultValue="// hello, world!" onMount={(editor, monaco) => {
              editorRef.current = editor;
            }} />
          </div>
        </div>
        <div className="wrapper">
          <ul className="tab">
            <li>터미널</li>
          </ul>
          <div className="terminal">
            {result !== null && (
              <>
                <p>Standard Output: {result.standardOutput}</p>
                <p>Standard Error: {result.standardError}</p>
                <p>Exceptions: {result.exceptions}</p>
              </>
            )}
            </div>
        </div>
      </div>
    </S.Home>
  );
};

export default Home;
