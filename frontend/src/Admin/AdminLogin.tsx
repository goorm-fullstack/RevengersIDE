import React from 'react';
import * as S from '../Container/Member/Style';
import Logo from '../Components/Logo/Logo';
import {Link, useNavigate} from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { useForm } from 'react-hook-form';
import Instance from '../Utils/api/axiosInstance';

const AdminLogin = () => {

  const navigate = useNavigate();
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
          // console.log('cookie ' + response.headers['jsessionid']);
          setCookie('JSESSIONID', response.headers['jsessionid'], { path: '/ideApi' });
          navigate('/admin');
        }
      })
      .catch((error) => {
        console.log(error.data);
        alert('아이디 또는 비밀번호를 확인해주세요.');
      });
  };

  const goHome = () => {
    navigate('/');
  }


  return (
    <S.Login>
      <div className="w">
        <Logo isAdmin={true} /> {/* 관리자 페이지인지 확인 변수 전송 */}
        <div>
          <form onSubmit={handleSubmit(onSubmit)}>
            <input {...register('memberId')} type="text" name='memberId' placeholder="관리자 아이디" />
            <input {...register('password')}type="password" name='password' placeholder="관리자 비밀번호" />
            <button type="submit" disabled={isSubmitting}>로그인</button>
          </form>
        </div>
        <div>
          <div>
            <button className="delBtn" onClick={goHome}>사용자 홈 바로가기</button>
          </div>
        </div>
      </div>
    </S.Login>
  );
};

export default AdminLogin;
