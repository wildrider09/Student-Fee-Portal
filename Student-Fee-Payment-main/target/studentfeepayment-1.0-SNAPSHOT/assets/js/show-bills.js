let queryString = decodeURIComponent(window.location.search).substring(1);
let billsList = null, userName, firstName;
let dict = {};
let promiseResponse = start();
let counter = 0;

async function start() {
    let queries = queryString.split('&');
    userName = queries[0].substring(9);
    firstName = queries[1].substring(5);
    console.log(firstName + " " + userName);

    document.getElementById("welcomeMessage").innerHTML = "Welcome " + firstName + " To IIIT Bangalore";
    document.getElementById("rollNumber").innerHTML = "Your Roll Number & User Name is " + userName;


    let response = await fetch('api/bills/show', {
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
        document.getElementById('heading').innerHTML = "Great !!! No Pending bills for your account";
        document.getElementById('show-bills').remove();
        document.getElementById('payment').remove();
    } else {
        document.getElementById('heading').innerHTML = "Following are the bills related to your account";

        let tableBody = document.getElementById('bills');
        tableBody.innerHTML = "";
        for (let i = 0; i < billsList.length; i++) {
            tableBody.innerHTML += '<tr>';
            let temp = "";
            temp += '<td>' + billsList[i].description + '</td>';
            temp += '<td>' + billsList[i].billDate + '</td>';
            temp += '<td>' + billsList[i].deadline + '</td>';
            temp += '<td>' + billsList[i].totalAmount + '</td>';
            temp += '<td>' + billsList[i].paidAmount + '</td>';
            temp += '<td>' + billsList[i].remainingAmount + '</td>';
            temp += '<td> <input type="number" value=0 oninput="calculate()" min=0 onfocusout="calculate()" id="payment' + i + '" class="myclass" name="payment"/>' + '</td>';

            tableBody.innerHTML += temp + '</tr>';
        }
    }

    let tableBody = document.getElementById('notification-drop');
    let today = new Date();
    tableBody.innerHTML = "";
    tableBody.innerHTML += '<li>';

    if (billsList.length === 0) {
        let temp = "";
        temp += 'All bills are paid.';
        tableBody.innerHTML += temp + '</li>';
    } else {
        let temp = "";
        for (let i = 0; i < billsList.length; i++) {
            let billdate = new Date((billsList[i].deadline));
            let diff = billdate.getTime() - today.getTime();
            let msInDay = 1000 * 3600 * 24;
            let remain = Math.ceil(diff / msInDay)
            if (remain < 5) {
                if (remain === 0) {
                    temp += '<a>' + billsList[i].description + ' Deadline overs today' + '</a>';
                } else if (remain > 0) {
                    temp += '<a>' + billsList[i].description + ' Deadline overs in ' + remain + ' Days' + '</a>';
                } else {
                    temp += '<a>' + billsList[i].description + ' Deadline is Overdue' + '</a>';
                }
                counter++;
            }
        }
        tableBody.innerHTML += temp + '</li>';
        if (counter === 0) {
            let temp = "";
            temp += "None  of the bill's deadline overs in 5 days";
            tableBody.innerHTML += temp + '</li>';
        }
    }
    let alertshow = document.getElementById('deadlineAlert');
    // if (counter === 0) {
    //     alertshow.innerHTML = 'Alerts';
    // } else {
    //     alertshow.innerHTML = 'Alerts (' + counter + ')';
    // }

}

let finalSum = 0;

function calculate() {
    for (let i = 0; i < billsList.length; i++) {
        if ((billsList[i].remainingAmount) - (document.getElementById("payment" + i).value) < 0) {
            alert("Please enter the  value less then Remaining amount.")
            document.getElementById("payment" + i).value = 0;
        }
        if ((document.getElementById("payment" + i).value).startsWith("-")) {
            alert("Please enter positive amount.")
            document.getElementById("payment" + i).value = 0;
        }
    }
    let sum = 0;

    for (let i = 0; i < billsList.length; i++) {
        let val = document.getElementById("payment" + i).value;
        const x = parseInt(val === "" ? "0" : val);
        sum += x;
        document.getElementById('totalAmount').value = sum;
        dict[billsList[i].description] = x;
    }
    finalSum = sum;
}


function payment() {
    if (finalSum === 0) {
        alert("Your Total Payment Amount is 0")
    } else {
        let queryString = "?userName=" + userName + "&name=" + firstName;
        for (let key in dict) {
            if (parseInt(dict[key]) !== 0)
                queryString += "&" + key + "=" + dict[key];
        }
        console.log("Query String: " + queryString);
        window.location.href = "BillSummary.html" + queryString;
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