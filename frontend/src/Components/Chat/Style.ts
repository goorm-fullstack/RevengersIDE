import styled from 'styled-components';

export const Chat = styled.div`
  position: absolute;
  bottom: 60px;
  right: 0;
  width: 50vw;
  max-width: 350px;
  border: 1px solid ${(props) => props.theme.borderColor};

  h3 {
  
    position: relative;
  
    // 채팅 영역 타이틀
    background-color: ${(props) => props.theme.bgPanel};
    border-bottom: 1px solid ${(props) => props.theme.borderColor};
    padding: 0 20px;
    height: 50px;
    line-height: 50px;

    span {
      // 채팅 인원
      color: ${(props) => props.theme.textPale};
      font-weight: 300;
      padding-left: 4px;
    }
  }
  
  input {
      position: absolute;
      right: 20px;
      top: 50%;
      transform: translateY(-50%);
      height: 30px;
      border: 1px solid ${(props) => props.theme.borderColor};
      background-color: ${(props) => props.theme.bgColor};
      color: ${(props) => props.theme.textColor};
      border-radius: 4px;
      padding-left: 8px;
    }
  }

  .messagew {
    // 채팅 메시지 영역
    height: 40vh;
    max-height: 370px;
    background-color: ${(props) => props.theme.bgColor};
    padding: 14px 20px 6px;
    overflow-y: scroll;

    // 스크롤 css
    &::-webkit-scrollbar {
      width: 5px;
    }

    &::-webkit-scrollbar-thumb {
      background-color: ${(props) => props.theme.bgColor};
      border-radius: 5px;
    }

    &:hover::-webkit-scrollbar-thumb {
      background-color: ${(props) => props.theme.bgPanel};
      border-radius: 5px;
    }

    &::-webkit-scrollbar-track {
      background-color: ${(props) => props.theme.bgColor};
    }

    li {
      // 각 메시지
      line-height: 1.6;
      margin-bottom: 8px;
      word-break: break-all;

      &.enter {
        // 입장, 퇴장 메시지
        text-align: center;
        font-size: 0.8125rem;
        color: ${(props) => props.theme.textPale};
        padding: 2px 0;
      }

      strong {
        // 사람 이름
        font-weight: 500;
      }
    }
  }

  .writewrapper {
    // 채팅 작성 및 삭제 영역
    height: 130px;

    .tab {
      height: 30px;
      line-height: 30px;
      text-align: right;
      display: flex;
      justify-content: flex-end;

      button {
        // 채팅 삭제 버튼
        path,
        line {
          stroke: ${(props) => props.theme.textColor};
        }
      }
    }

    .textareaw {
      // 채팅 입력 영역
      height: calc(100% - 30px);

      textarea {
        width: 100%;
        height: 100%;
        resize: none;
        padding: 10px 16px;
        border: 0;
      }
    }
  }
`;
