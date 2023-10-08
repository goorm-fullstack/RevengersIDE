import React from "react";
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import Footer from "../../Components/Footer/Footer";

const AdminGroup = () => {
    return <div>
        <AdminSidebar />
        <table>
            <th><input type="checkbox" /></th>
            <th>번호</th>
            <th>그룹 이름</th>
            <th>현재 인원</th>
            <th>정보 변경</th>
            {/* 반복문 필요 */}
            <tr>
                <td><input type="checkbox" /></td>
                <td>1</td>
                <td>관리자 그룹</td>
                <td>3명</td>
                <button>어디로??</button>
            </tr>
        </table>
        <Footer />
    </div>
};

export default AdminGroup;