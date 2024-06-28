import {FEED_URL} from "../feed-list.js";

export async function fetchFeedDelete(boardId,payload) {

  const url = FEED_URL + '/v1/'+boardId;
  const res = await fetch(url, payload);
  if(!res.ok) {
    throw new Error(`HTTP error! Status: ${res.status}`);
  }
  const result = await res.json()
  console.log('피드 삭제 결과: ', result);

}