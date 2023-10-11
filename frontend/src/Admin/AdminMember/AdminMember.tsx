import React, {useEffect, useState} from 'react';
import AdminSidebar from '../AdminSidebar/AdminSidebar';
import * as S from '../Style';
import { Link } from 'react-router-dom';
import Instance from "../../Utils/api/axiosInstance";
import axios from 'axios';

const AdminMember = () => {
  const [allMember, setAllMember] = useState<Member[]>([]);
  interface Member {
    id: number;
    memberId: string;
    memberName: string;
    email: string;
  }

  useEffect(() => {
    Instance.get(`/ideApi/api/member/all`)
        .then((response) => {
          setAllMember(response.data);
        })
        .catch((e) => {
          console.error(e);
        })
  }, []);

  const printMember = () => {
    if (allMember.length === 0) {
      return (
          <tr>
            <td colSpan={5} style={{ textAlign: "center" }}>
              회원가입된 아이디가 없습니다.
            </td>
          </tr>
      );
    } else {
      return (
          allMember.map((member, index: number) => (
              <tr key={member.id}>
                <td>{member.id}</td>
                <td>
                  <Link to={`/admin/member/detail/${member.memberId}`}>
                    {member.memberId}
                  </Link>
                </td>
                <td>{member.memberName}</td>
                <td>{member.email}</td>
              </tr>
          ))
      );
    }
  };

  return (
    <S.AdminLayout>
      <AdminSidebar />
      <S.AdminTitle>Member</S.AdminTitle>
      <S.AdminContents>
        <S.TableWrap>
          <table>
            <colgroup>
              <col width="140px" />
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
            <tbody>
            {printMember()}
              {/* 반복문 시작 */}
              {/* 반복문 끝 */}
              {/* 이하 작업 후 지워도 되는 값, 디자인 확인용 */}
            </tbody>
          </table>
        </S.TableWrap>
      </S.AdminContents>
    </S.AdminLayout>
  );
};

export default AdminMember;
