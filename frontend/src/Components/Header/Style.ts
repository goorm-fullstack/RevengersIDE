import styled from 'styled-components';

export const Header = styled.header`
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

    .darkmodew {
      display: flex;
      align-items: center;
    }

    button {
      // 테마 전환 버튼
      background: ${(props) => props.theme.primary};
      border: 0;
      border-radius: 50px;
      width: 45px;
      height: 16px;
      position: relative;
      margin-left: 12px;

      &.dark {
        background: ${(props) => props.theme.textColor};
      }

      span {
        // 달 아이콘
        position: absolute;
        width: 25px;
        height: 25px;
        top: 50%;
        right: 0;
        border-radius: 50%;
        background-color: white;
        background-image: url('data:image/svg+xml;utf8,<svg fill="orange" viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg"><rect fill="none" height="256" width="256"/><path d="M240,80H224V64a8,8,0,0,0-16,0V80H192a8,8,0,0,0,0,16h16v16a8,8,0,0,0,16,0V96h16a8,8,0,0,0,0-16Z"/><path d="M152,48h8v8a8,8,0,0,0,16,0V48h8a8,8,0,0,0,0-16h-8V24a8,8,0,0,0-16,0v8h-8a8,8,0,0,0,0,16Z"/><path d="M216.5,144.6l-2.2.4A84,84,0,0,1,111,41.6a5.7,5.7,0,0,0,.3-1.8,8,8,0,0,0-5-7.9,7.8,7.8,0,0,0-5.2-.2A100,100,0,1,0,224.3,154.9a7.9,7.9,0,0,0,0-4.8A8.2,8.2,0,0,0,216.5,144.6Z"/></svg>');
        transform: translate(0, -50%);
        box-shadow: 0 0 5px 2px rgba(0, 0, 0, 0.1);
        background-size: 70%;
        background-position: center;
        background-repeat: no-repeat;
      }

      &.dark span {
        // 해 아이콘
        left: 0;
        transform: translate(0, -50%);
        background-image: url('data:image/svg+xml;utf8,<svg id="Layer_1_1_" fill="orange" style="enable-background:new 0 0 16 16;" version="1.1" viewBox="0 0 16 16" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"><circle cx="8.5" cy="7.5" r="4.5"/><rect height="2" width="1" x="8"/><rect height="2" width="1" x="8" y="13"/><rect height="1" width="2" x="14" y="7"/><rect height="1" width="2" x="1" y="7"/><rect height="2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -4.7175 12.8033)" width="1" x="12.596" y="11.096"/><rect height="2" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -0.9099 3.6109)" width="1" x="3.404" y="1.904"/><rect height="1" transform="matrix(0.7071 -0.7071 0.7071 0.7071 -7.4099 6.3033)" width="2" x="2.904" y="11.596"/><rect height="1" transform="matrix(0.7071 -0.7071 0.7071 0.7071 1.7823 10.1107)" width="2" x="12.096" y="2.404"/></svg>');
      }
    }
  }

  .right {
    font-size: 0.9375rem;

    .guest {
      // 로그인 안 했을 때
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

    .member {
      // 로그인한 경우
      button {
        background: transparent;
        border: 0;
        font-size: 15px;

        &::before {
          width: 1px;
          height: 10px;
          content: '';
          background: ${(props) => props.theme.borderColor};
          display: inline-block;
          margin: 0 12px;
        }
      }
    }
  }
`;
