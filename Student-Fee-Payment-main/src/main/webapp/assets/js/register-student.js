let register_form = document.getElementById('register-student');
let student = null;

register_form.addEventListener('submit', async (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (register_form.checkValidity() === true) {
        let response = await fetch('api/register/student', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                firstName: document.getElementById('firstName').value,
                lastName: document.getElementById('lastName').value,
                password: document.getElementById('password').value,
                email: document.getElementById('email').value,
                graduationYear: document.getElementById('graduationYear').value,
            })
        });
        let myStatus = response.status;
        if (myStatus === 404) {
            document.getElementById("dupeEmail").innerHTML = "Given Email id is already registered with us please enter a different Email id !!!";
        } else {
            student = await response.json();
            console.log(student);
            let myStatus = response.status;
            register_form.classList.add('was-validated');
            let queryString = "?userName=" + student.userName + "&name=" + student.firstName;
            window.location.href = "Bills.html" + queryString;
        }
    }
});
