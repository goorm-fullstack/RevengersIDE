import React from 'react';
import * as S from './Style';

const Chat = () => {
  return (
    <S.Chat>
      <h3>
        Chats <span>1{/** 채팅 인원 수 */}</span>
      </h3>
      <ul className="messagew">
        {/** 채팅 메시지 영역 */}
        {/** 입장/퇴장은 enter className 추가 */}
        {/** 사람 이름 strong, 메시지 내용 span */}
        <li className="enter">
          <strong>나</strong> <span>님이 접속했습니다.</span>
        </li>
        <li>
          <strong>보낸사람1: </strong>
          <span>메시지1</span>
        </li>
        <li>
          <strong>보낸사람2: </strong>
          <span>메시지2</span>
        </li>
        <li>
          <strong>나: </strong>
          <span>
            통신·방송의 시설기준과 신문의 기능을 보장하기 위하여 필요한 사항은 법률로 정한다. 대통령은 법률이 정하는 바에 의하여 훈장 기타의 영전을
            수여한다. 국가안전보장회의의 조직·직무범위 기타 필요한 사항은 법률로 정한다. 감사원은 원장을 포함한 5인 이상 11인 이하의 감사위원으로
            구성한다. 훈장등의 영전은 이를 받은 자에게만 효력이 있고, 어떠한 특권도 이에 따르지 아니한다.
          </span>
        </li>
        <li className="enter">
          <strong>보낸사람2</strong> <span>님이 나가셨습니다.</span>
        </li>
        <li>
          <strong>보낸사람3: </strong>
          <span>메시지3</span>
        </li>
        <li>
          <strong>나: </strong>
          <span>
            통신·방송의 시설기준과 신문의 기능을 보장하기 위하여 필요한 사항은 법률로 정한다. 대통령은 법률이 정하는 바에 의하여 훈장 기타의 영전을
            수여한다. 국가안전보장회의의 조직·직무범위 기타 필요한 사항은 법률로 정한다. 감사원은 원장을 포함한 5인 이상 11인 이하의 감사위원으로
            구성한다. 훈장등의 영전은 이를 받은 자에게만 효력이 있고, 어떠한 특권도 이에 따르지 아니한다.
          </span>
        </li>
      </ul>
      <div className="writewrapper">
        <div className="tab">
          {/** 메시지 삭제 버튼 */}
          <button type="button">
            <svg viewBox="0 0 256 256" xmlns="http://www.w3.org/2000/svg">
              <rect fill="none" height="256" width="256" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="216" x2="40" y1="56" y2="56" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="104" x2="104" y1="104" y2="168" />
              <line fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" x1="152" x2="152" y1="104" y2="168" />
              <path d="M200,56V208a8,8,0,0,1-8,8H64a8,8,0,0,1-8-8V56" fill="none" strokeLinecap="round" strokeLinejoin="round" strokeWidth="16" />
              <path
                d="M168,56V40a16,16,0,0,0-16-16H104A16,16,0,0,0,88,40V56"
                fill="none"
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="16"
              />
            </svg>
          </button>
        </div>
        <div className="textareaw">
          <textarea>{/** 채팅 메시지 입력 영역 */}</textarea>
        </div>
      </div>
    </S.Chat>
  );
};

export default Chat;
