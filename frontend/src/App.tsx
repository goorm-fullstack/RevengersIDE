import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { GlobalStyle } from './Style/GlobalStyle';
import * as dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/ko';
import Home from './Container/Home/Home';
import Login from './Container/Member/Login';
import SignUp from './Container/Member/SignUp';
import MyAccount from './Container/Member/MyAccount';
import AdminHome from './Admin/AdminHome/AdminHome';
import AdminLogin from './Admin/AdminLogin/AdminLogin';
import AdminMember from './Admin/AdminManage/AdminMember';
import AdminGroup from './Admin/AdminManage/AdminGroup';

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
        <Route path="/myaccount" element={<MyAccount />} />
        <Route path="/admin" element={<AdminHome />} />
        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/member" element={<AdminMember />} />
        <Route path="/admin/group" element={<AdminGroup />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
