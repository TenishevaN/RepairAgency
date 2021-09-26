'use strict';

function handleNameChange() {

    var name = document.getElementById("nameValue").value;

    if ((name === null) || (name === "")) {
        $('#errorName').css('visibility', 'visible');
        return false;
    }

    var validName = /^[a-zA-Z]+$/;

    if (!(validName.test(name))) {
        $('#errorName').css('visibility', 'visible');
        return false;
    }

    return true;
}

function handleLoginChange() {

    var login = document.getElementById("loginValue").value;
    var validLogin = /^[a-zA-Z0-9]+$/;

    if (!(validLogin.test(login))) {
        $('#errorLogin').css('visibility', 'visible');
        return false;
    }
    return true;
}

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

function handleDescriptionChange() {

    var descriptionValue = document.getElementById("descriptionValue").value;

    if ((descriptionValue === null) || (descriptionValue === "")) {
        $('#errorDescription').css('visibility', 'visible');
        return false;
    }
    return true;
}

function handleCommentChange() {

    var commentValue = document.getElementById("commentValue").value;
    commentValue = commentValue.trim();
    if ((commentValue === null) || (commentValue === "")) {
        $('#errorComment').css('visibility', 'visible');

        return false;
    }
    return true;
}

function handleSubmitRegistrationUserForm() {

    var passwordCorrect = handlePasswordChange();
    var emailCorrect = handleEmailChange();
    var loginCorrect = handleLoginChange();
    var nameCorrect = handleNameChange();

    return (passwordCorrect && emailCorrect && loginCorrect && nameCorrect);
}

function handleUserCardChange() {

    var loginCorrect = handleLoginChange();
    var emailCorrect = handleEmailChange();
    var nameCorrect = handleNameChange();

    return (emailCorrect && loginCorrect && nameCorrect);
}

function checkReplenishment() {
    var ammount = Number(document.getElementById("ammountValue").value);
    if ((ammount === 0) || (ammount === null)) {
        $('#errorIndicateAmmount').css('visibility', 'visible');
        return false;
    }
    return true;
}

function checkPayment() {

    var ammount = Number(document.getElementById("ammountValue").value);
    var total = Number(document.getElementById("total").value);
    if ((ammount === 0) || (ammount === null)) {
        $('#errorIndicateAmmount').css('visibility', 'visible');
        $('#errorNotEnoughFundsToPay').css('visibility', 'hidden');
        return false;
    }

    if (Number(total) < Number(ammount)) {
        $('#errorNotEnoughFundsToPay').css('visibility', 'visible');
        $('#errorIndicateAmmount').css('visibility', 'hidden');
        return false;
    }
    return true;
}
