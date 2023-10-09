import React from 'react';
import * as S from '../Style';
import AdminSidebar from '../AdminSidebar/AdminSidebar';
import { useParams } from "react-router-dom";
import Instance from "../../Utils/api/axiosInstance";
import {useForm} from "react-hook-form";

const AdminDetail = () => {
  const { memberId } = useParams();

  const {
    register,
    handleSubmit,
    formState: { isSubmitting },
    watch,
    reset,
  } = useForm();

  const password = watch('password');
  const newPassword = watch('newPassword');
  const newEmail = watch('newEmail');

  const updateMember = (data:any) => {
    if(password !== newPassword){
      alert('입력하신 비밀번호가 일치하지 않습니다.');
      return;
    }

    if (newEmail === "") {
      alert('이메일을 입력해주세요.');
      return;
    }

    const formData = {
      memberId: memberId,
      newPassword: data.newPassword,
      email: data.newEmail,
    };

    Instance.post(`/ideApi/api/member/updateMember`, formData, {
      headers: {'Content-Type': 'application/json'},
    })
        .then((response) => {
          if(response.data){
            alert('회원정보가 변경되었습니다.');
            reset();
          }
        })
        .catch((e) => {
          console.error(e);
        });
  };

  const passwordCheck = () => {
    if (password !== newPassword) {
      return <p className="check">입력하신 비밀번호가 일치하지 않습니다.</p>;
    }
    return null;
  };


  return (
      <S.AdminLayout>
        <AdminSidebar />
        <S.AdminTitle>Member</S.AdminTitle>
        <S.AdminContents>
          <S.TableWrap>
            <form onSubmit={handleSubmit(updateMember)}>
              <table className="horizontal">
                <colgroup>
                  <col width="240px" />
                  <col width="auto" />
                </colgroup>
                <tbody>
                <tr>
                  <th>회원명</th>
                  <td>
                    <input type="text" name="memberName" placeholder="회원명" />
                  </td>
                </tr>
                <tr>
                  <th>회원 ID</th>
                  <td>
                    <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required value={memberId} readOnly />
                  </td>
                </tr>
                <tr>
                  <th>비밀번호</th>
                  <td>
                    <input {...register('password')} type="password" name="password" placeholder="비밀번호" />
                  </td>
                </tr>
                <tr>
                  <th>비밀번호 확인</th>
                  <td>
                    <input {...register('newPassword')} type="password" name="newPassword" placeholder="비밀번호 확인" />
                    {passwordCheck()}
                  </td>
                </tr>
                <tr>
                  <th>이메일</th>
                  <td>
                    <input {...register('newEmail')} type="email" name="newEmail" placeholder="이메일" required />
                  </td>
                </tr>
                </tbody>
              </table>
              <S.BtnWrapper>
                <button type="submit" disabled={isSubmitting || password !== newPassword}>회원 정보 수정</button>
              </S.BtnWrapper>
            </form>
          </S.TableWrap>
        </S.AdminContents>
      </S.AdminLayout>
  );
};

export default AdminDetail;
