import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';

const SignUp = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();

  const findId = (data: any) => {
    Instance.post('/api/member/findId', data, { headers: { 'Content-Type': 'application/json' } })
        .then((response) => {
          const userId = response.data;
          console.log(userId);
          alert(`찾으시는 아이디는 ${userId} 입니다.`);
          window.location.href = '/login';
        })
        .catch((error) => {
          console.log(error.data);
          alert("아이디를 찾을 수 없습니다.");
        });
  };

  const findPassword = (data: any) => {
    Instance.post('/api/member/findPassword', data, { headers: { 'Content-Type': 'application/json' } })
        .then(() => {
          alert(`비밀번호 재설정 링크를 이메일로 전송하였습니다.`);
          window.location.href = '/login';
        })
        .catch((error) => {
          console.log(error.data);
          alert("비밀번호를 찾을 수 없습니다.");
        });
  };

  return (
    <S.Find>
      <div className="w">
        <Logo isAdmin={false} />
        <div>
          <h2>아이디 찾기</h2>
          <form onSubmit={handleSubmit(findId)}>
            <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
            <input {...register('email')} type="text" name="email" placeholder="이메일" required />
            <button type="submit" disabled={isSubmitting}>
              아이디 찾기
            </button>
          </form>
          <h2>비밀번호 찾기</h2>
          <form onSubmit={handleSubmit(findPassword)}>
            <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
            <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
            <input {...register('email')} type="text" name="email" placeholder="이메일" required />
            <button type="submit" disabled={isSubmitting}>
              비밀번호 찾기
            </button>
          </form>
        </div>
      </div>
    </S.Find>
  );
};

export default SignUp;
