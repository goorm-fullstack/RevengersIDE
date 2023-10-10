import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import Instance from '../../Utils/api/axiosInstance';

const MyAccount = () => {
  const navigate = useNavigate();
  const [memberInfo, setMemberInfo] = useState({
    memberId: '',
    memberName: '',
    email: '',
    password: '',
    passwordCheck: '',
    createMemberDate: [],
  }); // 회원 정보 get

  useEffect(() => {
    // 회원 정보 가져오기
    Instance.get('/ideApi/api/member/myaccount', { withCredentials: true })
      .then((response) => {
        // console.log(response);
        if (response.status === 200) {
          if (response.data === '') {
            // 세션 쿠키 없음
            alert('비회원입니다. \n로그인 페이지로 이동합니다.');
            navigate('/login');
          } else {
            // 세션 쿠키 있음
            console.log('회원입니다.');
            setMemberInfo(response.data);
          }
        }
      })
      .catch((error) => {
        console.log(error.data);
      });
  }, []);

  const deleteAccount = () => {
    if(window.confirm("정말 회원을 탈퇴하시겠습니까?")){
      Instance.delete(`/ideApi/api/member/deleteId/${memberInfo.memberId}`)
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
            <input type="text" name="memberId" placeholder="아이디" value={memberInfo.memberId} readOnly />
            <input type="text" name="memberName" placeholder="고객명" value={memberInfo.memberName} readOnly />
            <input type="password" name="password" placeholder="비밀번호" />
            <input type="password" name="passwordCheck" placeholder="비밀번호 확인" />
            <input type="text" name="email" placeholder="이메일" value={memberInfo.email} />
            <button type="submit">회원정보수정</button>
          </form>
          <p className="link">
            <button className="delBtn" onClick={deleteAccount}>탈퇴하기</button>
          </p>
        </div>
      </div>
    </S.Member>
  );
};

export default MyAccount;
