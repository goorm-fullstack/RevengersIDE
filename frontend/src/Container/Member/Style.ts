import styled from 'styled-components';

const Member = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #f0f2f5;

  & > .w {
    width: 400px;
    margin: 0 auto;
    padding-top: 22vh;
    text-align: center;

    h1 {
      font-size: 2rem;
      margin-bottom: 40px;
    }

    input {
      width: 100%;
      height: 50px;
      border: 0;
      margin: 5px 0;
      padding: 0 16px;
      font-size: 0.875rem;
    }

    button[type='submit'] {
      width: 40%;
      height: 50px;
      border: 0;
      background: #121212;
      color: white;
      border-radius: 50px;
      margin: 20px 0 40px;

      &:hover {
        background: #222;
      }
    }

    .link a {
      color: #2673dd;
      text-decoration: underline;
      margin: 0 6px;
    }
  }
`;

export const Login = styled(Member)`
  & > .w {
    padding-top: 30vh;
  }
  
  p{
    margin-bottom: 10px;
  }
`;

export const SignUp = styled(Member)``;

export const MyAccount = styled(Member)``;

export const Find = styled(Member)`
  div div h2{
    text-align: left;
    font-size: 1rem;
  }
  div div div button{
    border: none;
    margin-bottom: 30px;
    margin-right: 10px;
    margin-left: 10px;
  }
  form{
    text-align: right;
  }
`;
