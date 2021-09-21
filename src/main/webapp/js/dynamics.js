'use strict';

function handleEmailChange() {

    var email = document.getElementById("email").value;
    var validEmail = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+.((com)|(org)|(net)|(ru))$/;

    if (!(validEmail.test(email))) {
        document.getElementById('errorEmail').innerHTML="Invalid email address!";
        return false;
    }
    return true;
}

function handlePasswordChange() {

    var password = document.getElementById("passcode").value;
    var validPassword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;

    if (!(validPassword.test(password))) {
        document.getElementById('errorPassword').innerHTML="Password must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters!";
       return false;
    }
    return true;
}

function handleSubmitRegistrationUserForm() {
    var emailCorrect =  handleEmailChange();
    var passwordCorrect =  handlePasswordChange();
    return emailCorrect && passwordCorrect;
}
