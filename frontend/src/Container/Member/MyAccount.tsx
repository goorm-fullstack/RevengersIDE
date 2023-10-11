import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import Instance from '../../Utils/api/axiosInstance';
import { useForm } from 'react-hook-form';
import axios from 'axios';

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
  const {
    register,
    handleSubmit,
    reset,
    formState: { isSubmitting },
  } = useForm({
    defaultValues: memberInfo,
  });

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
            reset(response.data);
          }
        }
      })
      .catch((error) => {
        console.log(error.data);
        navigate('/login');
      });
  }, []);

  // 회원 정보 수정
  const onSubmit = (data: any) => {
    Instance.post('/ideApi/api/member/myaccount', data, { headers: { 'Content-Type': 'application/json' } })
      .then((response) => {
        if (response.status === 200) {
          alert('회원 정보가 수정되었습니다.');
          window.location.reload();
        }
      })
      .catch((error) => {
        console.log(error.data);
        alert('다시 시도해주세요. 모든 값을 입력하셔야 합니다.\n문제가 계속된다면 문의 바랍니다.');
      });
  };

  // 회원 탈퇴
  const deleteAccount = () => {
    if (window.confirm('정말 회원을 탈퇴하시겠습니까?')) {
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
          <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register('createMemberDate')} type="hidden" name="createMemberDate" value={memberInfo.createMemberDate} disabled />
            <input
              {...register('memberId')}
              type="text"
              name="memberId"
              placeholder="아이디"
              value={memberInfo.memberId}
              defaultValue={memberInfo.memberId}
              readOnly
            />
            <input
              {...register('memberName')}
              type="text"
              name="memberName"
              placeholder="고객명"
              value={memberInfo.memberName}
              defaultValue={memberInfo.memberName}
              readOnly
            />
            <input {...register('password')} type="password" name="password" placeholder="비밀번호" required />
            <input {...register('passwordCheck')} type="password" name="passwordCheck" placeholder="비밀번호 확인" required />
            <input {...register('email')} type="text" name="email" placeholder="이메일" defaultValue={memberInfo.email} required />
            <button type="submit" disabled={isSubmitting}>
              회원정보수정
            </button>
          </form>
          <p className="link">
            <button className="delBtn" onClick={deleteAccount}>
              탈퇴하기
            </button>
          </p>
        </div>
      </div>
    </S.Member>
  );
};

export default MyAccount;
