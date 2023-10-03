import React from 'react';
import * as S from './Style';
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import Footer from "../../Components/Footer/Footer";

const AdminHome = () => {
  return(
   <S.AdminHome>
    <AdminSidebar />
    <S.statistics>
      <ul>
        <li>현재 가입된 회원 수 : n명</li>
        <li>현재 생성된 그룹 수 : n그룹</li>
        <li>현재 사용중인 DB 저장소 : n%</li>     {/* 기능 구현 가능하다면 적용 */}
        <li>현재 남은 DB 저장소 : n%</li>        {/* 기능 구현 가능하다면 적용 */}
      </ul>
    </S.statistics>
    <Footer />
  </S.AdminHome>
  );
};

export default AdminHome;
