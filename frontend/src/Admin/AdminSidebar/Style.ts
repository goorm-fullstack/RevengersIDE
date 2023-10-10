import styled from 'styled-components';

export const Sidebar = styled.div`
  position: fixed;
  left: 0;
  top: 0;
  width: 320px;
  height: 100%;
  background-color: #1d1d42;
  color: rgba(255, 255, 255, 0.8);
  padding: 40px 0 20px;
  font-size: 15px;

  h1 {
    // 로고
    color: white;
    padding: 0 20px;
  }

  .svgwrap {
    // svg 세로 정렬 클래스
    display: flex;
    align-items: center;
  }

  svg {
    // 아이콘 색상 설정
    fill: white;
    margin-right: 8px;

    g {
      fill: inherit;
    }
  }

  .adminstate {
    // 관리자 로그인 상태
    margin: 40px 0;
    font-size: 14px;
    padding: 0 20px;

    p {
      strong {
        color: white;
        font-size: 16px;
      }
    }

    button {
      // 로그아웃 버튼
      background: transparent;
      border: 1px solid #fff;
      margin-top: 12px;
      width: 80px;
      height: 32px;
      line-height: 32px;
      font-size: 12px;
      border-radius: 32px;

      &:hover {
        background: white;
        color: #1d1d42;
      }
    }
  }

  .foo {
    // 사용자 홈 바로가기
    position: absolute;
    bottom: 20px;
    padding: 0 20px;

    svg {
      fill: rgba(255, 255, 255, 0.8);
    }
  }

  .nav {
    li {
      a {
        width: 100%;
        padding: 16px 20px;

        &:hover,
        &.active {
          background-color: rgba(255, 255, 255, 0.1);
        }
      }
    }
  }
`;
