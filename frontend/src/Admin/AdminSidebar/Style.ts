import styled from 'styled-components';

export const Sidebar = styled.div `
  position: fixed;
  left: 0;
  top: 0;
  width: 250px;
  height: 100%;
  background-color: rgba(0,0,0,0.87);
  color: white;
  padding: 20px;
  
  h2 {
    font-size: 2rem;
    margin-bottom: 20px;
  }
  button{
    margin-bottom: 10px;
    border: none;
    font-size: 1rem;
    background-color: transparent;
  }
  
  h3 {
    font-size: 1.5rem;
    margin-top: 40px;
    margin-bottom: 10px;
  }
  li{
    margin-bottom: 5px;
  }
`