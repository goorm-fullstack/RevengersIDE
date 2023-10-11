import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import Instance from '../../Utils/api/axiosInstance';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import {useAuth} from "../../Utils/api/AuthContext";

const Login = () => {
  const navigate = useNavigate();
  const { setLoggedIn } = useAuth();
  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
  } = useForm();

  const [cookies, setCookie] = useCookies(['JSESSIONID']);

  const onSubmit = (data: any) => {
    Instance.post('/ideApi/api/member/login', data, { headers: { 'Content-Type': 'multipart/form-data' } })
      .then((response) => {
        if (response.status === 200) {
          console.log('React: 로그인 성공');
          setLoggedIn(true);
          localStorage.setItem('isLoggedIn', 'true');
          // console.log('cookie ' + response.headers['jsessionid']);
          setCookie('JSESSIONID', response.headers['jsessionid'], { path: '/ideApi' });
          navigate('/');
        }
      })
      .catch((error) => {
        console.log(error.data);
        alert('아이디 또는 비밀번호를 확인해주세요.');
      });
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
