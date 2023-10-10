import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import Instance from "../../Utils/api/axiosInstance";

const MyAccount = () => {
  const navigate = useNavigate();
  const deleteAccount = () => {
    if(window.confirm("정말 회원을 탈퇴하시겠습니까?")){
      Instance.delete(`/ideApi/api/member/deleteId`)
        .then((response) => {
          alert('회원이 정상적으로 탈퇴되었습니다.');
          navigate('/');
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };
  return (
    <S.Member>
      <div className="w">
        <Logo isAdmin={false} />
        <div>
          <form>
            <input type="text" placeholder="아이디" />
            <input type="text" placeholder="고객명" />
            <input type="password" placeholder="비밀번호" />
            <input type="password" placeholder="비밀번호 확인" />
            <input type="text" placeholder="이메일" />
            <button type="submit">회원정보수정</button>
          </form>
          <p className="link">
            <button onClick={deleteAccount}>탈퇴하기</button>
          </p>
        </div>
      </div>
    </S.Member>
  );
};

export default MyAccount;
