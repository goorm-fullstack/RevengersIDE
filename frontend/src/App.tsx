import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
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

function App() {
  return (
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
  );
}

export default App;
