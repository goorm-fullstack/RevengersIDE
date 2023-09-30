import styled from 'styled-components';

export const Footer = styled.footer`
  width: 100%;
  display: flex;
  justify-content: space-between;
  background-color: ${(props) => props.theme.bgPanel};
  border-top: 1px solid ${(props) => props.theme.borderColor};
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 0 20px;
  font-size: 0.875rem;

  button {
    background: transparent;
    border: 0;
    display: flex;
    align-items: center;
    height: 100%;

    svg {
      fill: ${(props) => props.theme.textColor};
      width: 16px;
    }

    &[data-isactive='true'] {
      svg {
        fill: ${(props) => props.theme.primary};
      }
    }
  }

  .right {
    position: relative;
  }

  .chatcontainer {
    display: none;

    &[data-isactive='true'] {
      display: block;
    }
  }
`;
