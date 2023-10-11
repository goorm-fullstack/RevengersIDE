import React, { useEffect, useState } from 'react';
import * as S from './Style';
import AdminSidebar from './AdminSidebar/AdminSidebar';
import { Link, useNavigate } from 'react-router-dom';
import Instance from '../Utils/api/axiosInstance';
import axios from 'axios';


const AdminHome = () => {
  const navigate = useNavigate();
  const [todayMemberCount, setTodayMemberCount] = useState(0);
  const [yesterdayMemberCount, setYesterdayMemberCount] = useState(0);
  const [allMemberCount, setAllMemberCount] = useState(0);
  const [allMember, setAllMember] = useState<Member[]>([]);

  interface Member {
    id: number;
    memberId: string;
    memberName: string;
    email: string;
  }

  const detail = () => {
    navigate('/admin/detail');
  };

  useEffect(() => {
    Instance.get(`/ideApi/api/member/todayMember`)
      .then((response) => {
        setTodayMemberCount(response.data.length);
      })
      .catch((e) => {
        console.error(e);
      });
  }, []);

  useEffect(() => {
    Instance.get(`/ideApi/api/member/yesterdayMember`)
      .then((response) => {
        setYesterdayMemberCount(response.data.length);
      })
      .catch((e) => {
        console.error(e);
      });
  }, []);

  useEffect(() => {
    Instance.get(`/ideApi/api/member/all`)
      .then((response) => {
        setAllMemberCount(response.data.length);
        setAllMember(response.data);
      })
      .catch((e) => {
        console.error(e);
      });
  }, []);

  const printMember = () => {
    if (allMember.length === 0) {
      return (
        <tr>
          <td colSpan={5} style={{ textAlign: 'center' }}>
            회원가입된 아이디가 없습니다.
          </td>
        </tr>
      );
    } else {
      return allMember.map((member, index: number) => (
        <tr key={member.id}>
          <td>{member.id}</td>
          <td>
            <Link to={`/admin/member/detail/${member.memberId}`}>{member.memberId}</Link>
          </td>
          <td>{member.memberName}</td>
          <td>{member.email}</td>
        </tr>
      ));
    }
  };

  return (
    <S.AdminLayout>
      <AdminSidebar />
      <S.AdminTitle>Admin Home</S.AdminTitle>
      <S.AdminHome>
        <ul className="membertotal">
          <li>
            <div className="icow">😄</div>
            <div className="info">
              <h3>전체 회원 수</h3>
              <p>
                <strong>{allMemberCount}</strong> 명
              </p>
            </div>
          </li>
          <li>
            <div className="icow">🌞</div>
            <div className="info">
              <h3>오늘 가입 회원 수</h3>
              <p>
                <strong>{todayMemberCount}</strong> 명
              </p>
            </div>
          </li>
          <li>
            <div className="icow">🌚</div>
            <div className="info">
              <h3>전일 가입 회원 수</h3>
              <p>
                <strong>{yesterdayMemberCount}</strong> 명
              </p>
            </div>
          </li>
        </ul>
        <S.TableWrap>
          <table>
            <colgroup>
              <col width="120px" />
              <col width="auto" />
              <col width="auto" />
              <col width="auto" />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>회원 ID</th>
                <th>회원명</th>
                <th>이메일</th>
              </tr>
            </thead>
            <tbody>{printMember()}</tbody>
          </table>
        </S.TableWrap>
      </S.AdminHome>
    </S.AdminLayout>
  );
};

export default AdminHome;
