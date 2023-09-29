import styled from 'styled-components';

export const Header = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 0 20px;
  height: 60px;
  line-height: 60px;
  background-color: ${(props) => props.theme.bgPanel};

  .left {
    // 로고 있는 영역
    display: flex;

    h1 {
      // 로고
      font-size: 1.25rem;
      font-family: 'Raleway', sans-serif;
      font-weight: 300;

      strong {
        font-weight: 600;
      }
    }
  }

  .right {
    font-size: 0.9375rem;

    .guest {
      a::before {
        width: 1px;
        height: 10px;
        content: '';
        background: ${(props) => props.theme.borderColor};
        display: inline-block;
        margin: 0 12px;
      }

      a:first-child::before {
        display: none;
      }
    }
  }
`;
