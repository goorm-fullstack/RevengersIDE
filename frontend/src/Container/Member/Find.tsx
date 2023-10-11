import React, { useState } from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Find = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
    reset,
  } = useForm();

  const [isIdFormVisible, setIdFormVisible] = useState(true);
  const [isPasswordFormVisible, setPasswordFormVisible] = useState(false);
  const navigate = useNavigate();

  const toggleIdForm = () => {
    setIdFormVisible(true);
    setPasswordFormVisible(false);
    reset();
  };

  const togglePasswordForm = () => {
    setIdFormVisible(false);
    setPasswordFormVisible(true);
    reset();
  };

  const findId = (data: any) => {
    Instance.post('/ideApi/api/member/findId', data, { headers: { 'Content-Type': 'application/json' } })
      .then((response) => {
        console.log(data);
        const userId = response.data.memberId;
        alert(`고객님의 아이디는 ${userId}입니다.`);
      })
      .catch((e) => {
        console.error(e);
      });
  };

  const findPassword = (data: any) => {
    Instance.post('/ideApi/api/member/findPassword', data, { headers: { 'Content-Type': 'application/json' } })
      .then((response) => {
        if (response.data.memberId) {
          navigate('/changepassword', { state: { memberId: response.data.memberId } });
        }
        console.log('회원정보 일치, 비밀번호 변경 페이지로 이동.');
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
          <div className="tab">
            <button onClick={toggleIdForm} data-isactive={isIdFormVisible}>
              아이디 찾기
            </button>
            <button onClick={togglePasswordForm} data-isactive={isPasswordFormVisible}>
              비밀번호 찾기
            </button>
          </div>
          {isIdFormVisible && (
            <div>
              <form onSubmit={handleSubmit(findId)}>
                <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                <button type="submit" disabled={isSubmitting}>
                  아이디 찾기
                </button>
              </form>
            </div>
          )}
          {isPasswordFormVisible && (
            <div>
              <form onSubmit={handleSubmit(findPassword)}>
                <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
                <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                <button type="submit" disabled={isSubmitting}>
                  비밀번호 찾기
                </button>
              </form>
            </div>
          )}
        </div>
      </div>
    </S.Find>
  );
};

export default Find;
