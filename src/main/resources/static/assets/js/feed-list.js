const ROOT_PATH = 'http://localhost:8181'

const fetchFeedList = async () => {
    const res = await fetch(ROOT_PATH+'/feed/list');
    const list = res.json();
    console.log(list);
}
fetchFeedList();