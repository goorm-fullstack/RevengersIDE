import React from 'react';
import * as S from './Style';
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import Footer from "../../Components/Footer/Footer";
import {useNavigate} from "react-router-dom";

const AdminHome = () => {
  const navigate = useNavigate();

  const detail = () => {
    navigate('/admin/detail');
  }

  return(
   <S.AdminHome>
    <AdminSidebar />
    <S.Statistics>
      <h1>관리자 페이지</h1>
      <S.Items>
        <div>
          <h2>회원 가입자 수</h2>
          <h3>😄</h3>
          <p>n명</p>
        </div>
        <div>
          <h2>오늘 가입자 수</h2>
          <h3>🌞</h3>
          <p>n명</p>
        </div>
        <div>
          <h2>어제 가입자 수</h2>
          <h3>🌚</h3>
          <p>n명</p>
        </div>
      </S.Items>
    </S.Statistics>
     <table>
       <th><input type="checkbox" /></th>
       <th>번호</th>
       <th>아이디</th>
       <th>이름</th>
       <th>이메일</th>
       <th>정보 변경</th>
       {/* 반복문 필요 */}
       <tr>
         <td><input type="checkbox" /></td>
         <td>3</td>
         <td>Administrator</td>
         <td>관리자</td>
         <td>test@test.com</td>
         <td><button>어디로??</button></td>
       </tr>
       <tr>
         <td><input type="checkbox" /></td>
         <td>2</td>
         <td>Administrator</td>
         <td>관리자</td>
         <td>test@test.com</td>
         <td><button>어디로??</button></td>
       </tr>
       <tr>
         <td><input type="checkbox" /></td>
         <td>1</td>
         <td>Administrator</td>
         <td>관리자</td>
         <td>test@test.com</td>
         <td><button onClick={detail}>어디로??</button></td>
       </tr>
     </table>
    <Footer />
  </S.AdminHome>
  );
};

export default AdminHome;
