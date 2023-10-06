import React from 'react';
import * as S from '../../Container/Member/Style';
import Logo from "../../Components/Logo/Logo";

const AdminLogin = () => {
  return (
      <S.Login>
        <div className="w">
          <Logo isAdmin={true}/>    {/* 관리자 페이지인지 확인 변수 전송 */}
          <div>
            <form>
              <input type="text" placeholder="관리자 아이디" />
              <input type="password" placeholder="관리자 비밀번호"/>
              <button type="submit">관리자 로그인</button>
            </form>
          </div>
        </div>
      </S.Login>
  )
};

export default AdminLogin;
