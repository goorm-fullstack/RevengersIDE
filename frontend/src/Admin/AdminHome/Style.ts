import styled from 'styled-components';

export const AdminHome = styled.div`
  width: 100%;
  height: 100%;
  height: 100vh;
  
  table{
    margin-left: 350px;
    margin-top: 100px;
    min-width: 1200px;
    font-size: 1rem;
  }
  th{
    border-bottom: lightgray 0.5px solid;
    text-align: left;
  }
  td{
    padding-top: 5px;
    padding-bottom: 5px;
  }
  .center {
    text-align: center;
  }
`;

export const Statistics = styled.div`
  display: flex;
  flex-direction: column;
  //margin: 2% 10% 0 10%;
  margin-left: 350px;
  margin-top: 100px;
  min-width: 1100px;
  
  h1{
    font-size: 3rem;
    margin-bottom: 70px;
  }
`

export const Items = styled.div`
  display: flex;
  flex-direction: row;
  
  div{
    width: 300px;
    background-color: white;
    margin-right: 60px;
  }
  
  h2{
    font-size: 2rem;
  }
  p{
    font-size: 1.25rem;
  }
  h3{
    margin-top: 20px;
    margin-bottom: 20px;
    font-size: 100px;
  }
`