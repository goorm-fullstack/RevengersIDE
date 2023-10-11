import React, { useState } from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ChangePassword = () => {
  const location = useLocation();
  const memberId = location.state?.memberId || '';
  const navigate = useNavigate();

  const passwordCheck = () => {
    return password !== newPassword && <p className="check">입력하신 비밀번호가 일치하지 않습니다.</p>;
  };

  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
    watch,
  } = useForm();

  const password = watch('password');
  const newPassword = watch('newPassword');

  const changePassword = (data: any) => {
    if (password !== newPassword) {
      alert('입력하신 비밀번호가 일치하지 않습니다.');
      return;
    }

    const formData = {
      memberId: memberId,
      newPassword: data.newPassword,
    };

    Instance.post('/ideApi/api/member/changePassword', formData, {
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => {
        if (response.data) {
          alert('비밀번호가 변경되었습니다. 다시 로그인해주세요.');
          navigate('/login');
        }
      })
      .catch((e) => {
        console.error(e);
      });
  };

  return (
    <S.Find>
      <div className="w">
        <Logo isAdmin={false} />
        <div>
          <div>
            <p>
              비밀번호를 변경합니다.
              <br />
              새로 사용하실 비밀번호를 입력해주세요.
            </p>
            <form onSubmit={handleSubmit(changePassword)}>
              <input {...register('memberId')} type="hidden" name="memberId" placeholder="아이디" required value={memberId} disabled />
              <input {...register('password')} type="password" name="password" placeholder="비밀번호" required />
              <input {...register('newPassword')} type="password" name="newPassword" placeholder="비밀번호 확인" required />
              {passwordCheck()}
              <button type="submit" disabled={isSubmitting || password !== newPassword}>
                비밀번호 변경
              </button>
            </form>
          </div>
        </div>
      </div>
    </S.Find>
  );
};

export default ChangePassword;
