import React, { createContext, useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import { lightTheme, darkTheme } from './Style/theme';
import { GlobalStyle } from './Style/GlobalStyle';
import * as dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/ko';
import Home from './Container/Home/Home';
import Login from './Container/Login/Login';
import SignUp from './Container/SignUp/SignUp';
import AdminHome from './Admin/AdminHome/AdminHome';
import AdminLogin from './Admin/AdminLogin/AdminLogin';

dayjs.extend(relativeTime);
dayjs.locale('ko');

// 다른 컴포넌트와 테마 전환 설정 공유
export interface ThemeContextProps {
  theme: string;
  isLight: boolean;
  toggleTheme: any;
}
export const ThemeContext = createContext({} as ThemeContextProps);

function App() {
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
        <BrowserRouter>
          <GlobalStyle />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<SignUp />} />
            <Route path="/admin" element={<AdminHome />} />
            <Route path="/admin/login" element={<AdminLogin />} />
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </ThemeContext.Provider>
  );
}

export default App;
