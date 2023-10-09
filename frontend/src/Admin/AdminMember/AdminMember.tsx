import React from 'react';
import AdminSidebar from '../AdminSidebar/AdminSidebar';
import * as S from '../Style';
import { Link } from 'react-router-dom';

const AdminMember = () => {
  return (
    <S.AdminLayout>
      <AdminSidebar />
      <S.AdminTitle>Member</S.AdminTitle>
      <S.AdminContents>
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
      </S.AdminContents>
    </S.AdminLayout>
  );
};

export default AdminMember;
