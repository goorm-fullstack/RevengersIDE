import React from "react";
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import Footer from "../../Components/Footer/Footer";
import * as S from './Style';


const AdminMember = () => {
    return(
    <S.AdminManage>
        <AdminSidebar />
        <h1>회원 관리</h1>
            <table>
                <th><input type="checkbox" /></th>
                <th>번호</th>
                <th>아이디</th>
                <th>이름</th>
                <th>비밀번호</th>
                <th>정보 변경</th>
                {/* 반복문 필요 */}
                <tr>
                    <td className="center"><input type="checkbox" /></td>
                    <td className="center">1</td>
                    <td>Administrator</td>
                    <td>관리자</td>
                    <td>Adminpassword</td>
                    <td className="center"><button>어디로??</button></td>
                </tr>
                <tr>
                    <td className="center"><input type="checkbox" /></td>
                    <td className="center">2</td>
                    <td>Administrator</td>
                    <td>관리자</td>
                    <td>Adminpassword</td>
                    <td className="center"><button>어디로??</button></td>
                </tr>
                <tr>
                    <td className="center"><input type="checkbox" /></td>
                    <td className="center">3</td>
                    <td>Administrator</td>
                    <td>관리자</td>
                    <td>Adminpassword</td>
                    <td className="center"><button>어디로??</button></td>
                </tr>
            </table>
        <Footer />
    </S.AdminManage>
    );
};

export default AdminMember;