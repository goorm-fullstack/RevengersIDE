import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';
import axios from 'axios';

const SignUp = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();

  const onSubmit = (data: any) => {
    Instance.post('/ideApi/api/member/signup', data, { headers: { 'Content-Type': 'application/json' } })
      .then(() => {
        alert('회원가입이 완료되었습니다.');
        window.location.href = '/login';
      })
      .catch((error) => {
        console.log(error.data);
        alert('입력하신 아이디 또는 이메일이 있습니다.');
      });
  };

  return (
    <S.Member>
      <div className="w">
        <Logo isAdmin={false} />
        <div>
          <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
            <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
            <input {...register('password')} type="password" name="password" placeholder="비밀번호" required />
            <input {...register('passwordCheck')} type="password" name="passwordCheck" placeholder="비밀번호 확인" required />
            <input {...register('email')} type="text" name="email" placeholder="이메일" required />
            <button type="submit" disabled={isSubmitting}>
              회원가입
            </button>
          </form>
        </div>
      </div>
    </S.Member>
  );
};

export default SignUp;
