const $updateButton = document.querySelector('.update-button');

$updateButton.addEventListener('click', async e => {
    fetchUpdate();
});

async function fetchUpdate() {

    const payload = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        nickname: document.getElementById('nickname').value,
    };
    console.log('payload: ', payload);

    await callApi('http://localhost:8181/mypage/update', 'POST', payload);

};