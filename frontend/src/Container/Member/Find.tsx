import React, { useState } from 'react';
import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';

const Find = () => {
    const {
        register,
        handleSubmit,
        formState: { isSubmitting },
        reset, // 입력 필드를 초기화하기 위해 reset 함수를 사용합니다.
    } = useForm();

    const [isIdFormVisible, setIdFormVisible] = useState(true);
    const [isPasswordFormVisible, setPasswordFormVisible] = useState(false);

    const toggleIdForm = () => {
        setIdFormVisible(true);
        setPasswordFormVisible(false);
        reset(); // 아이디 찾기 폼으로 토글할 때 입력 필드를 초기화합니다.
    };

    const togglePasswordForm = () => {
        setIdFormVisible(false);
        setPasswordFormVisible(true);
        reset(); // 비밀번호 찾기 폼으로 토글할 때 입력 필드를 초기화합니다.
    };

    const findId = (data: any) => {
        Instance.post('/api/member/findId', data, { headers: { 'Content-Type': 'application/json' } })
            .then((response) => {
                console.log(data);
                const userId = response.data.memberId;
                alert(`아이디는 ${userId}입니다.`);
            })
            .catch((e) => {
                console.error(e);
            });
    };

    const findPassword = (data: any) => {
        Instance.post('/api/member/findPassword', data, { headers: { 'Content-Type': 'application/json' } })
            .then((response) => {
                const userPassword = response.data.memberPassword;
                console.log(response.data);
                alert(`비밀번호는 ${userPassword}입니다.`);
            })
            .catch((e) => {
                console.error(e);
            });
    };

    return (
        <S.Find>
            <div className="w">
                <Logo isAdmin={false} />
                <div>
                    <div>
                        <button onClick={toggleIdForm}
                                style={{ fontWeight: isIdFormVisible ? 'bold' : 'normal' }}>
                            아이디 찾기</button>
                        <button onClick={togglePasswordForm}
                                style={{ fontWeight: isPasswordFormVisible ? 'bold' : 'normal' }}>
                            비밀번호 찾기</button>
                    </div>
                    {isIdFormVisible && (
                        <div>
                            <h2>아이디 찾기</h2>
                            <form onSubmit={handleSubmit(findId)}>
                                <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                                <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                                <button type="submit" disabled={isSubmitting}>
                                    아이디 찾기
                                </button>
                            </form>
                        </div>
                    )}
                    {isPasswordFormVisible && (
                        <div>
                            <h2>비밀번호 찾기</h2>
                            <form onSubmit={handleSubmit(findPassword)}>
                                <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
                                <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                                <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                                <button type="submit" disabled={isSubmitting}>
                                    비밀번호 찾기
                                </button>
                            </form>
                        </div>
                    )}
                </div>
            </div>
        </S.Find>
    );
};

export default Find;
