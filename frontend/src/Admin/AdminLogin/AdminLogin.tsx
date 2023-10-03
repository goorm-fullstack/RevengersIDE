import React from 'react';
import * as S from './Style';
import Footer from "../../Components/Footer/Footer";

const AdminLogin = () => {
  return <div>
    <div>
      <h2>관리자 로그인</h2>
      아이디 : <input type="text" />
      비밀번호 : <input type="password"/>
    </div>
    <Footer />
  </div>;
};

export default AdminLogin;
