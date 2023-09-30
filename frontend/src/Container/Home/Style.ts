import styled from 'styled-components';

export const Home = styled.div`
  padding-bottom: 40px;

  .editor {
    // 코드 편집기 영역
    height: 55vh;
  }

  .terminal {
    // 터미널 세로 크기: 화면 크기 세로 길이 - 코드 편집기 - header, 실행탭, 터미널탭, footer 사이즈 제외
    height: calc(100vh - 55vh - (60px + 40px + 40px + 40px));
  }

  .tab {
    // 터미널 선택 탭 메뉴 영역
    width: 100%;
    padding: 0 20px;
    height: 40px;
    line-height: 40px;
    border-top: 1px solid ${(props) => props.theme.borderColor};
    border-bottom: 1px solid ${(props) => props.theme.borderColor};
    background-color: ${(props) => props.theme.bgPanel};
    font-size: 0.875rem;
  }

  .run {
    .tab {
      text-align: right;
    }
    button {
      border: 0;
      background: transparent;

      &::before {
        content: '▶';
        font-size: 0.7rem;
        margin-right: 6px;
        vertical-align: text-top;
        line-height: 1.25;
      }
    }
  }
`;
