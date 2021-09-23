'use strict';

function handleEmailChange() {

    var email = document.getElementById("email").value;
    var validEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.((com)|(org)|(net)|(ru))$/;

    if (!(validEmail.test(email))) {
        $('#errorEmail').css('visibility', 'visible');
        return false;
    }
    return true;
}

function handlePasswordChange() {

    var password = document.getElementById("passcode").value;
    var validPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

    if (!(validPassword.test(password))) {
        $('#errorPassword').css('visibility', 'visible');
        return false;
    }
    return true;
}

function handleSubmitRegistrationUserForm() {
    var emailCorrect = handleEmailChange();
    var passwordCorrect = handlePasswordChange();
    return emailCorrect && passwordCorrect;
}

    function checkReplenishment() {
    var ammount = document.getElementById("ammountValue").value;
    if ((ammount === 0) || (ammount === null) || (ammount === "") || (ammount === "0")) {
    $('#errorIndicateAmmount').css('visibility', 'visible');
    return false;
}
    return true;
}

function checkPayment() {

    var ammount = document.getElementById("ammountValue").value;
    var total = document.getElementById("total").value;
    if ((ammount === 0) || (ammount === null) || (ammount === "") || (ammount === "0")) {
        $('#errorIndicateAmmount').css('visibility', 'visible');
        $('#errorNotEnoughFundsToPay').css('visibility', 'hidden');
        return false;
    }
    if (total < ammount) {
        $('#errorNotEnoughFundsToPay').css('visibility', 'visible');
        $('#errorIndicateAmmount').css('visibility', 'hidden');
        return false;
    }
    return true;
}
