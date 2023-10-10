import React, { createContext, useContext, useState, useEffect } from 'react';

interface AuthContextProps {
    isLoggedIn: boolean;
    setLoggedIn: React.Dispatch<React.SetStateAction<boolean>>;
}

interface AuthProviderProps {
    children: React.ReactNode;
}

const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
    // 로컬 스토리지에서 초기 상태를 가져옴
    const initialLoginState = localStorage.getItem('isLoggedIn') === 'true';

    const [isLoggedIn, setLoggedIn] = useState(initialLoginState);

    // 로그인 상태가 변경될 때마다 로컬 스토리지를 업데이트
    useEffect(() => {
        localStorage.setItem('isLoggedIn', String(isLoggedIn));
    }, [isLoggedIn]);

    return (
        <AuthContext.Provider value={{ isLoggedIn, setLoggedIn }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};