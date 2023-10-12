function sendJSON(){

    let result = document.querySelector('.result');
    let nickname = document.querySelector('#nickname');
    let email = document.querySelector('#email');
    let login = document.querySelector('#login');
    let firstName = document.querySelector('#firstName');
    let lastName = document.querySelector('#lastName');

    let password = document.querySelector('#password');
    let repeatPassword = document.querySelector('#repeatPassword');
    let xhr = new XMLHttpRequest();
    let url = "http://localhost:8080/auth/register";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            result.innerHTML = this.responseText;
        }
    };
    var data = JSON.stringify({
        "nickname": nickname.value,
        "email": email.value,
        "login": login.value,
        "firstName": firstName.value,
        "lastName": lastName.value,
        "password": password.value,
        "repeatPassword": repeatPassword.value

    });
    xhr.send(data);
    alert( xhr.status );

    if (xhr.status === 201) {
        // обработать ошибку
        window.location.href = "chat.html";
        alert( xhr.responseText );
    } else {
        alert( xhr.responseText );

        // вывести результат
    }


}
