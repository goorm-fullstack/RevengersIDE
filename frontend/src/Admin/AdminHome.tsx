import React, {useEffect, useState} from 'react';
import * as S from './Style';
import AdminSidebar from './AdminSidebar/AdminSidebar';
import { Link, useNavigate } from 'react-router-dom';
import Instance from "../Utils/api/axiosInstance";

const AdminHome = () => {
  const navigate = useNavigate();
  const [todayMemberCount, setTodayMemberCount] = useState(0);
  const [yesterdayMemberCount, setYesterdayMemberCount] = useState(0);
  const [allMemberCount, setAllMemberCount] = useState(0);



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
        })
  }, []);

  useEffect(() => {
    Instance.get(`/ideApi/api/member/yesterdayMember`)
        .then((response) => {
          setYesterdayMemberCount(response.data.length);
        })
        .catch((e) => {
          console.error(e);
        })
  }, []);

  useEffect(() => {
    Instance.get(`/ideApi/api/member/all`)
        .then((response) => {
          setAllMemberCount(response.data.length);
        })
        .catch((e) => {
          console.error(e);
        })
  }, []);

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
              <col width="80px" />
              <col width="100px" />
              <col width="auto" />
              <col width="auto" />
              <col width="auto" />
            </colgroup>
            <thead>
              <tr>
                <th>
                  <input type="checkbox" />
                </th>
                <th>번호</th>
                <th>회원 ID</th>
                <th>회원명</th>
                <th>이메일</th>
              </tr>
            </thead>
            <tbody>
              {/* 반복문 시작 */}
              <tr>
                <td>
                  <input type="checkbox" />
                </td>
                <td>3</td>
                <td>
                  <Link to="/admin/member/detail/memberid">Administrator</Link>
                </td>
                <td>관리자</td>
                <td>test@test.com</td>
              </tr>
              {/* 반복문 끝 */}
              {/* 이하 작업 후 지워도 되는 값, 디자인 확인용 */}
              <tr>
                <td>
                  <input type="checkbox" />
                </td>
                <td>2</td>
                <td>
                  <Link to="/admin/member/detail/memberid">Administrator</Link>
                </td>
                <td>관리자</td>
                <td>test@test.com</td>
              </tr>
              <tr>
                <td>
                  <input type="checkbox" />
                </td>
                <td>1</td>
                <td>
                  <Link to="/admin/member/detail/memberid">Administrator</Link>
                </td>
                <td>관리자</td>
                <td>test@test.com</td>
              </tr>
            </tbody>
          </table>
        </S.TableWrap>
      </S.AdminHome>
    </S.AdminLayout>
  );
};

export default AdminHome;
