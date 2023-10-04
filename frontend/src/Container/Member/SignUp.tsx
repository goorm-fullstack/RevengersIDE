import React from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { response } from 'express';
import { error } from 'console';

const SignUp = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();

  const onSubmit = (data: any) => {
    axios
      .post('http://localhost:8080/api/member/signup', data, { headers: { 'Content-Type': 'application/json' } })
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => console.log(error.data));
  };

  return (
    <S.SignUp>
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
    </S.SignUp>
  );
};

export default SignUp;
