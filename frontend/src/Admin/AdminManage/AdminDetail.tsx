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
                        <tbody>
                            <tr>
                                <td>이름</td>
                                <td>테스트</td>
                            </tr>
                            <tr>
                                <td>비밀번호</td>
                                <td>testpassword</td>
                                <td><button>비밀번호 변경</button></td>
                            </tr>
                            <tr>
                                <td>이메일</td>
                                <td>test@test.com</td>
                            </tr>
                        </tbody>
                    </table>
                </S.Items>
            </HS.Statistics>
            <Footer />
        </HS.AdminHome>
    );
}

export default AdminDetail;