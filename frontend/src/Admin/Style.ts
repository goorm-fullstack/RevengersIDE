import styled from 'styled-components';

export const AdminLayout = styled.div`
  // 어드민 레이아웃
  width: 100%;
  min-width: 1200px;
  height: 100%;
  min-height: 100vh;
  padding: 0 0 0 320px;
  background: #f8f8f8;

  .delBtn{
    border: none;
    color: #2673dd;
    text-decoration: underline;
    background-color: transparent;
  }
  .center{
    text-align: center;
  }
`;

export const AdminTitle = styled.h2`
  // 어드민 페이지 타이틀
  width: 100%;
  background: white;
  padding: 40px;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 40px;
`;

export const AdminContents = styled.div`
  // 어드민 각 페이지 콘텐츠 영역
  padding: 0 40px;
`;

export const AdminHome = styled(AdminContents)`
  .membertotal {
    display: flex;
    column-gap: 40px;
    margin-bottom: 40px;
    width: 100%;

    li {
      background: white;
      width: calc(100% / 3);
      border-radius: 20px;
      height: 150px;
      display: flex;
      align-items: center;
      padding: 20px;

      .icow {
        width: 30%;
        min-width: 60px;
        font-size: 60px;
        text-align: center;
      }

      .info {
        padding-left: 20px;

        h3 {
          font-weight: 500;
          margin-bottom: 8px;
        }

        p {
          strong {
            font-size: 28px;
          }
        }
      }
    }
  }
`;

export const TableWrap = styled.div`
  // 어드민 테이블 스타일 공통
  width: 100%;
  background: white;
  border-radius: 20px;
  padding: 20px;

  table {
    width: 100%;

    td,
    th {
      border-top: 1px solid #eee;
      text-align: center;
      padding: 0 10px;
      height: 55px;
      line-height: 55px;
      font-size: 15px;

      input {
        vertical-align: middle;
      }
    }

    &.horizontal td {
      text-align: left;
    }

    thead td,
    thead th,
    &.horizontal tr:first-child td,
    &.horizontal tr:first-child th {
      border-top: 0;
    }

    th {
      font-weight: 500;
    }

    td {
      color: #444;

      a {
        text-decoration: underline;
      }
    }

    input[type='text'],
    input[type='password'],
    input[type='email'] {
      width: 100%;
      max-width: 500px;
      height: 50px;
      border: 0;
      margin: 5px 0;
      padding: 0 16px;
      font-size: 0.875rem;
    }
  }
`;

export const BtnWrapper = styled.div`
  text-align: center;
  padding: 40px 0;

  button[type='submit'] {
    width: 200px;
    height: 50px;
    border: 0;
    background: #121212;
    color: white;
    border-radius: 50px;

    &:hover {
      background: #222;
    }

  `;
