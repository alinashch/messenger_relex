function sendJSON(){

    let result = document.querySelector('.result');
    let login = document.querySelector('#login');
    let password = document.querySelector('#password');
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/auth/login";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            result.innerHTML = this.responseText;
        }
    };
    var data = JSON.stringify({
        "login": login.value,
        "password": password.value
    });


    fetch(url, {
        method: 'post',
        body: data
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('my api returned an error')
            }
            return response.json()
        })
        .then(user => {
            window.location.href = "chat.html";
            console.log(user)
        })
}
