import {FEED_URL} from "../feed-list.js";
// import {fetchFeedDetail} from "./feed-detail";

// 수정한 내용 api로 fetch
export async function fetchFeedModify(boardId, payload) {

  const url = `${FEED_URL}/v1/${boardId}`;
  const res = await fetch(url, payload);
  if(!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const result = await res.json();
  console.log('수정 fetch 이후: ', result);

  // fetchFeedDetail(boardId); 컨트롤러에서 리다이렉트

}
