import React from 'react';
import * as HS from '../AdminHome/Style'
import AdminSidebar from "../AdminSidebar/AdminSidebar";
import * as S from "./Style";
import Footer from "../../Components/Footer/Footer";

const AdminDetail = () => {
    return (
        <HS.AdminHome>
            <AdminSidebar />
            <HS.Statistics>
                {/*회원 정보 넣기 */}
                <h1>회원 정보 상세</h1>
                <S.Items>
                    <table>
                        <tr>
                            <th>번호</th>
                            <td>1</td>
                            <td>2</td>
                            <td>3</td>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>John</td>
                            <td>Jane</td>
                            <td>Smith</td>
                        </tr>
                        <tr>
                            <th>나이</th>
                            <td>25</td>
                            <td>30</td>
                            <td>22</td>
                        </tr>
                    </table>
                </S.Items>
            </HS.Statistics>
            <Footer />
        </HS.AdminHome>
    );
}

export default AdminDetail;