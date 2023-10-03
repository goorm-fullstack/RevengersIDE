import React from "react";
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import Footer from "../../Components/Footer/Footer";

const AdminMember = () => {
    return <div>
        <AdminSidebar />

            <table>
                <th><input type="checkbox" /></th>
                <th>번호</th>
                <th>아이디</th>
                <th>이름</th>
                <th>비밀번호</th>
                <th>정보 변경</th>
                {/* 반복문 필요 */}
                <tr>
                    <td><input type="checkbox" /></td>
                    <td>1</td>
                    <td>Administrator</td>
                    <td>관리자</td>
                    <td>Adminpassword</td>
                    <button>어디로??</button>
                </tr>
            </table>

        <Footer />
    </div>
};

export default AdminMember;