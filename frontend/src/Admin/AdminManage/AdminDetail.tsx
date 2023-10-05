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

                    </table>
                </S.Items>
            </HS.Statistics>
            <Footer />
        </HS.AdminHome>
    );
}

export default AdminDetail;