import styled from 'styled-components';

export const AdminManage = styled.div`
  width: 100%;
  height: 100%;
  margin: auto;
  
  h1{
    margin-left: 20%;
    font-size: 2.5rem;
    margin-top: 150px;
    font-weight: bold;
  }
  table{
    margin-top: 100px;
    margin-left: 20%;
    min-width: 760px;
    width: 70%;
  }
  th{
    font-size: 1.5rem;
    background-color: lightgray;
    padding-top: 15px;
    padding-bottom: 15px;
  }
  tr{
    font-size: 1.5rem;
    
  }
  td{
    padding-bottom: 15px;
    padding-top: 15px;
  }
  
  .center {
    text-align: center;
  }
  .leftborder {
    border-left: lightgray 1px solid;
  }
`;
export const Items = styled.div`
  table {
    border-collapse: collapse;
    width: 100%;
  }

  th {
    writing-mode: vertical-rl; /* 세로로 출력 */
    transform: rotate(180deg); /* 180도 회전하여 세로 텍스트 정렬 */
    background-color: lightgray;
    width: 20px; /* 필요한 너비 설정 */
  }

  th, td {
    border: 1px solid black;
    padding: 8px;
    text-align: center;
  }

  tr:first-child th {
    writing-mode: horizontal-tb; /* 첫 번째 열은 가로로 출력 */
    transform: none;
  }
`