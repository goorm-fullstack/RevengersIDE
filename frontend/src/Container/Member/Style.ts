import styled from 'styled-components';

export const Member = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #f0f2f5;

  & > .w {
    width: 90%;
    max-width: 400px;
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
  .delBtn{
    border: none;
    color: #2673dd;
    text-decoration: underline;
  }
`;

export const Login = styled(Member)`
  & > .w {
    padding-top: 30vh;
  }

  p {
    margin-bottom: 10px;
  }

  .delBtn{
    border: none;
    color: #2673dd;
    text-decoration: underline;
  }

`;

export const Find = styled(Login)`
  // 아이디 찾기/비밀번호 찾기 전환 탭
  .tab {
    display: flex;
    margin-bottom: 20px;

    button {
      width: 50%;
      height: 50px;
      background: transparent;
      border: 1px solid #ddd;
      border-bottom-color: #121212;
      color: #888;

      &[data-isactive='true'] {
        background-color: transparent;
        border-color: #121212;
        border-bottom-color: transparent;
        color: #121212;
        font-weight: 500;
      }
    }
  }

  p {
    line-height: 1.4;
  }
  
  .check {
    font-size: 14px;
    color: #ee2c4a;
    margin-top: 10px;
  }
`;
