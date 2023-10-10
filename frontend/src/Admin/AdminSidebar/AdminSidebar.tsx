import React from 'react';
import * as S from './Style';
import { Link, useNavigate, NavLink } from 'react-router-dom';
import Logo from '../../Components/Logo/Logo';
import LogoutBtn from '../../Components/LogoutBtn';
import AdminAuth from '../AdminAuth';

const AdminSidebar = () => {
  const navigate = useNavigate();

  return (
    <S.Sidebar>
      <Logo isAdmin={true} />
      <div className="adminstate">
        <p className="svgwrap">
          <svg version="1.1" viewBox="0 0 24 24" width="20px">
            <g id="info" />
            <g id="icons">
              <g id="user">
                <ellipse cx="12" cy="8" rx="5" ry="6" />
                <path d="M21.8,19.1c-0.9-1.8-2.6-3.3-4.8-4.2c-0.6-0.2-1.3-0.2-1.8,0.1c-1,0.6-2,0.9-3.2,0.9s-2.2-0.3-3.2-0.9    C8.3,14.8,7.6,14.7,7,15c-2.2,0.9-3.9,2.4-4.8,4.2C1.5,20.5,2.6,22,4.1,22h15.8C21.4,22,22.5,20.5,21.8,19.1z" />
              </g>
            </g>
          </svg>
          <strong>관리자</strong>&nbsp;님, 반갑습니다.
        </p>
        <LogoutBtn />
      </div>
      <ul className="nav">
        <li>
          <NavLink to="/admin" end className="svgwrap">
            <svg height="19px" version="1.1" viewBox="0 0 20 19" width="20px">
              <g fill="none" fillRule="evenodd" id="Page-1" stroke="none" strokeWidth="1">
                <g id="Core" transform="translate(-506.000000, -255.000000)">
                  <g id="home" transform="translate(506.000000, 255.500000)">
                    <path d="M8,17 L8,11 L12,11 L12,17 L17,17 L17,9 L20,9 L10,0 L0,9 L3,9 L3,17 L8,17 Z" id="Shape" />
                  </g>
                </g>
              </g>
            </svg>
            관리자 홈
          </NavLink>
        </li>
        <li>
          <NavLink to="/admin/member/" className="svgwrap">
            <svg version="1.1" viewBox="0 0 24 24" width="20px">
              <g id="info" />
              <g id="icons">
                <path
                  d="M12,0C5.4,0,0,5.4,0,12c0,6.6,5.4,12,12,12s12-5.4,12-12C24,5.4,18.6,0,12,0z M12,4c2.2,0,4,2.2,4,5s-1.8,5-4,5   s-4-2.2-4-5S9.8,4,12,4z M18.6,19.5C16.9,21,14.5,22,12,22s-4.9-1-6.6-2.5c-0.4-0.4-0.5-1-0.1-1.4c1.1-1.3,2.6-2.2,4.2-2.7   c0.8,0.4,1.6,0.6,2.5,0.6s1.7-0.2,2.5-0.6c1.7,0.5,3.1,1.4,4.2,2.7C19.1,18.5,19.1,19.1,18.6,19.5z"
                  id="user2"
                />
              </g>
            </svg>
            회원 관리
          </NavLink>
        </li>
      </ul>
      <div className="foo">
        <Link to="/" className="svgwrap">
          <svg height="19px" version="1.1" viewBox="0 0 20 19" width="20px">
            <g fill="none" fillRule="evenodd" id="Page-1" stroke="none" strokeWidth="1">
              <g id="Core" transform="translate(-506.000000, -255.000000)">
                <g id="home" transform="translate(506.000000, 255.500000)">
                  <path d="M8,17 L8,11 L12,11 L12,17 L17,17 L17,9 L20,9 L10,0 L0,9 L3,9 L3,17 L8,17 Z" id="Shape" />
                </g>
              </g>
            </g>
          </svg>{' '}
          사용자 홈 바로가기
        </Link>
      </div>
      <AdminAuth />
    </S.Sidebar>
  );
};

export default AdminSidebar;
