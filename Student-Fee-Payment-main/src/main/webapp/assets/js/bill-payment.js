let queryString = decodeURIComponent(window.location.search).substring(1);
let billsList = null, billsList_alert = null, userName, firstName;
let promiseResponse = start();
let counter = 0;

async function start() {
    let queries = queryString.split('&');
    userName = queries[0].substring(9);
    firstName = queries[1].substring(5);
    console.log(firstName + " " + userName);

    document.getElementById("welcomeMessage").innerHTML = "Welcome " + firstName + " To IIIT Bangalore";
    document.getElementById("rollNumber").innerHTML = "Your Roll Number & User Name is " + userName;

    let response2 = await fetch('api/bills/show', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            userName: userName
        })
    });

    billsList_alert = await response2.json();

    let table = document.getElementById('notification-drop');
    let today = new Date();
    table.innerHTML = "";
    table.innerHTML += '<li>';

    if (billsList_alert.length === 0) {
        let temp = "";
        temp += 'All bills are paid.';
        table.innerHTML += temp + '</li>';
    } else {
        let temp = "";
        for (let i = 0; i < billsList_alert.length; i++) {
            let billdate = new Date((billsList_alert[i].deadline));
            let diff = billdate.getTime() - today.getTime();
            let msInDay = 1000 * 3600 * 24;
            let remain = Math.ceil(diff / msInDay)
            if (remain < 5) {
                if (remain === 0) {
                    temp += '<a>' + billsList_alert[i].description + ' Deadline overs today' + '</a>';
                } else if (remain > 0) {
                    temp += '<a>' + billsList_alert[i].description + ' Deadline overs in ' + remain + ' Days' + '</a>';
                } else {
                    temp += '<a>' + billsList_alert[i].description + ' Deadline is Overdue' + '</a>';
                }
                counter++;
            }
        }


        table.innerHTML += temp + '</li>';
        if (counter === 0) {
            let temp = "";
            temp += "None  of the bill's deadline overs in 5 days";
            table.innerHTML += temp + '</li>';
        }
    }
    let alertshow = document.getElementById('deadlineAlert');
    if (counter === 0) {
        alertshow.innerHTML = 'Alerts';
    } else {
        alertshow.innerHTML = 'Alerts (' + counter + ')';
    }

    let response = await fetch('api/bills/paid', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            userName: userName
        })
    });


    billsList = await response.json();
    if (billsList.length === 0) {
        document.getElementById('heading').innerHTML = "You have not paid any of yours bills yet !!";
        document.getElementById('show-payments').remove();

    } else {
        document.getElementById('heading').innerHTML = "Following are the Paid bills related to your account";
        let tableBody = document.getElementById('paidbills');
        tableBody.innerHTML = "";
        for (let i = 0; i < billsList.length; i++) {
            tableBody.innerHTML += '<tr>';
            let temp = "";
            temp += '<td>' + billsList[i].description + '</td>';
            temp += '<td>' + billsList[i].paymentDate + '</td>';
            temp += '<td>' + billsList[i].amount + '</td>';
            tableBody.innerHTML += temp + '</tr>';
        }
    }
}

function functionNotify() {
    // let alertshow = document.getElementById('deadlineAlert');
    // alertshow.innerHTML = 'Alerts';
}

async function gotoshowPayment() {
    window.location.href = "BillPayment.html?" + queryString;
}

async function goToHome() {
    window.location.href = "Bills.html?" + queryString;
}

async function goToAbout() {
    window.location.href = "about.html?" + queryString;
}

async function goToContactUs() {
    window.location.href = "contactus.html?" + queryString;
}