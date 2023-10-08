import React from 'react';
import * as S from './Style';
import {Link, useNavigate} from "react-router-dom";

const AdminSidebar = () => {
  const navigate = useNavigate();

  const Logout = () => {
  //   Logout 함수
    navigate('/admin/login');
  }

  return(
      <S.Sidebar>
        <div>
          <h2><Link to="/admin">IDE Admin</Link></h2>
          <button onClick={Logout}>로그아웃</button>
          <h3><Link to="/admin/member/">✔️회원 관리</Link></h3>
        </div>
      </S.Sidebar>
  );
};

export default AdminSidebar;