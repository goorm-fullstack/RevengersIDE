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

  table{
    text-align: left;
    border-top: black 1px solid;
    border-bottom: black 1px solid;
    min-width: 720px;
    margin-left: 0;
    margin-top: 0;
  }
  td{
    padding-top: 10px;
    padding-bottom: 10px;
  }
  td button{
    border: none;
    background-color: transparent;
  }

`