import React from 'react';
import * as S from './Style';

const AdminSidebar = () => {
  const Logout = () => {
  //   Logout 함수
  }

  return(
      <S.Sidebar>
        <div>
          <h2>이동규 관리자</h2>
          <button onClick={Logout}>로그아웃</button>
          <ul>
            <h3>관리자 계정 설정</h3>
            <ul>
              <li>관리자 비밀번호 변경</li>
              <li>관리자 이름 변경</li>
            </ul>

            <h3>회원 관리</h3>
            <ul>
              <li>회원 관리</li>
              <li>그룹 관리</li>
            </ul>
          </ul>

        </div>
      </S.Sidebar>
  );
};

export default AdminSidebar;
