import { FEED_URL } from "./feed-list.js";

export async function fetchFeedList(pageNo=1) {


  const res = await fetch(`${FEED_URL}/list`);
  const feedList = await res.json();
  console.log(list);
}

export {fetchFeedList}