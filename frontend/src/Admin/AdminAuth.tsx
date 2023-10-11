import React, { useEffect, useState } from 'react';
import Instance from '../Utils/api/axiosInstance';
import { useNavigate } from 'react-router';
import axios from 'axios';

const AdminAuth = () => {
  const navigate = useNavigate();

  // 회원 역할 가져오기
  Instance.get('/ideApi/api/member/auth', { withCredentials: true })
    .then((response) => {
      // console.log(response);
      if (response.status === 200) {
        if (response.data !== 'ADMIN') {
          // 관리자가 아닌 경우
          navigate('/admin/login');
        }
      }
    })
    .catch((error) => {
      console.log(error.data);
    });

  return <div></div>;
};

export default AdminAuth;
