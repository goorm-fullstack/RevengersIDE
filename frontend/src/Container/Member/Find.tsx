import * as S from './Style';
import Logo from '../../Components/Logo/Logo';
import { useForm } from 'react-hook-form';
import Instance from '../../Utils/api/axiosInstance';

const Find = () => {

  const {
    register,
    handleSubmit,
    formState: {isSubmitting},
  } = useForm();

  const findId = (data: any) => {
    Instance.post('/api/member/findId', data, {headers : {'Content-Type' : 'application/json'}})
    .then((response) => {
      const userId = response.data.memberId;
      alert(`아이디는 ${userId}입니다.`);
    })
    .catch((e) => {
      console.error(e);
    })
  }

  const findPassword = (data: any) => {
    Instance.post('/api/member/findId', data, {headers : {'Content-Type' : 'application/json'}})
    .then((response) => {
      const userPassword = response.data.memberPassword;
      console.log(response.data);
      alert(`비밀번호는 ${userPassword}입니다.`);
    })
    .catch((e) => {
      console.error(e);
    })
  }

  return (
      <S.Find>
          <div className="w">
              <Logo isAdmin={false} />
              <div>
                  <h2>아이디 찾기</h2>
                  <form onSubmit={handleSubmit(findId)}>
                    <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                    <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                    <button type="submit">아이디 찾기</button>
                  </form>

                <h2>비밀번호 찾기</h2>
                <form onSubmit={handleSubmit(findPassword)}>
                  <input {...register('memberId')} type="text" name="memberId" placeholder="아이디" required />
                  <input {...register('memberName')} type="text" name="memberName" placeholder="고객명" required />
                  <input {...register('email')} type="text" name="email" placeholder="이메일" required />
                  <button type="submit">비밀번호 찾기</button>
                </form>
                
              </div>
          </div>
      </S.Find>
  );
};

export default Find;
