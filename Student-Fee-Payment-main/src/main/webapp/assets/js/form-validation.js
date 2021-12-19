let login_form = document.getElementById('login-validation');
let student = null;

login_form.addEventListener('submit', async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (login_form.checkValidity() === true) {
        let response = await fetch('api/login/validate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                userName: document.getElementById('userName').value,
                password: document.getElementById('password').value
            })
        });
        let myStatus = response.status;
        if (myStatus === 404) {
            document.getElementById("incorrectUser").innerHTML = "Incorrect User Name/Password !!!";
        } else {
            student = await response.json();
            console.log(student);
            login_form.classList.add('was-validated');
            let queryString = "?userName=" + student.userName + "&name=" + student.firstName;
            window.location.href = "Bills.html" + queryString;
        }
    }
});