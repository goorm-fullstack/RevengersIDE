import React from 'react';
import * as S from '../Style';
import AdminSidebar from '../AdminSidebar/AdminSidebar';

const AdminDetail = () => {
  return (
    <S.AdminLayout>
      <AdminSidebar />
      <S.AdminTitle>Member</S.AdminTitle>
      <S.AdminContents>
        <S.TableWrap>
          <form>
            <table className="horizontal">
              <colgroup>
                <col width="240px" />
                <col width="auto" />
              </colgroup>
              <tbody>
                <tr>
                  <th>회원명</th>
                  <td>
                    <input type="text" name="memberName" placeholder="고객명" required />
                  </td>
                </tr>
                <tr>
                  <th>회원 ID</th>
                  <td>
                    <input type="text" name="memberId" placeholder="아이디" required />
                  </td>
                </tr>
                <tr>
                  <th>비밀번호</th>
                  <td>
                    <input type="password" name="password" placeholder="비밀번호" />
                  </td>
                </tr>
                <tr>
                  <th>비밀번호 확인</th>
                  <td>
                    <input type="password" name="passwordCheck" placeholder="비밀번호 확인" />
                  </td>
                </tr>
                <tr>
                  <th>이메일</th>
                  <td>
                    <input type="text" name="email" placeholder="이메일" required />
                  </td>
                </tr>
              </tbody>
            </table>
            <S.BtnWrapper>
              <button type="submit">회원 정보 수정</button>
            </S.BtnWrapper>
          </form>
        </S.TableWrap>
      </S.AdminContents>
    </S.AdminLayout>
  );
};

export default AdminDetail;
