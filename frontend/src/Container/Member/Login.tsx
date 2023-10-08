import React from 'react';
import { Link } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import Instance from '../../Utils/api/axiosInstance';

const Login = () => {
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();

  const onSubmit = (data: any) => {
    Instance.post('/ideApi/api/member/login', data, { headers: { 'Content-Type': 'application/json' } })
      .then((response) => {
        console.log(response.data);
        if (response.status === 200) {
          window.location.href = '/';
        }
      })
      .catch((error) => console.log(error.data));
  };

  return (
    <S.Login>
      <div className="w">
        <Logo isAdmin={false} /> {/* 관리자의 로그인인지 확인 변수 전송 */}
        <div>
          <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
            <input {...register('password')} type="password" name="password" placeholder="비밀번호" required />
            <button type="submit" disabled={isSubmitting}>
              로그인
            </button>
          </form>
          <p className="link">
            계정이 없으신가요? <Link to="/signup">회원가입</Link>
          </p>
          <p className="link">
            아이디/비밀번호를 분실하셨나요? <Link to="/find">아이디/비밀번호 찾기</Link>
          </p>
        </div>
      </div>
    </S.Login>
  );
};

export default Login;
