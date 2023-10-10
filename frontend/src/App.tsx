import React, {useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { GlobalStyle } from './Style/GlobalStyle';
import * as dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import 'dayjs/locale/ko';
import Home from './Container/Home/Home';
import Login from './Container/Member/Login';
import SignUp from './Container/Member/SignUp';
import MyAccount from './Container/Member/MyAccount';
import AdminHome from './Admin/AdminHome';
import AdminLogin from './Admin/AdminLogin';
import AdminMember from './Admin/AdminMember/AdminMember';
import AdminDetail from './Admin/AdminMember/AdminDetail';
import Find from './Container/Member/Find';
import ChangePassword from './Container/Member/ChangePassword';
import { AuthProvider } from './Utils/api/AuthContext';

dayjs.extend(relativeTime);
dayjs.locale('ko');

function App() {
  const [logMemberName, setLogMemberName] = useState(null);

  return (
      <AuthProvider>
    <BrowserRouter>
      <GlobalStyle />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/find" element={<Find />} />
        <Route path="/myaccount" element={<MyAccount />} />
        <Route path="/admin" element={<AdminHome />} />
        <Route path="/admin/login" element={<AdminLogin />} />
        <Route path="/admin/member" element={<AdminMember />} />
        <Route path="/admin/member/detail/:memberId" element={<AdminDetail />} />
        <Route path="/changepassword" element={<ChangePassword />} />
      </Routes>
    </BrowserRouter>
        </AuthProvider>
  );
}

export default App;
