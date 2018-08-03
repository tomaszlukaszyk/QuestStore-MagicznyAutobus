function testIfFormIsEmpty(className, buttonId) {
  var inputs = document.getElementsByClassName(className);
  var button = document.getElementById(buttonId);
  var x = 0;

  for (var i = 0; i < inputs.length ; i++) {
    if (!inputs[i].value.trim() == "") {
        x += 1;
    }
  }
  if (x == inputs.length) {
    button.style.visibility = "visible";
    button.disabled = false;
  }
}

function testUserName() {
  var name, pattern;
  name = document.getElementById("username").value;
  pattern = /^([A-Za-z ]{2,30})$/;
  return pattern.test(name);
}

function testUserEmail() {
  var email, pattern;
  email = document.getElementById("useremail").value;
  pattern = /^[^\s]([A-Za-z0-9_.]{2,20})@([A-Za-z0-9]{2,20}).([a-z]{2,13})$/;
  return pattern.test(email);

}

function testUserSurname() {
  var surname, pattern;
  surname = document.getElementById("usersurname").value;
  pattern = /^([A-Za-z ]{2,30})$/;
  return pattern.test(surname);

}

function checkUserInputs() {
    if (!testUserSurname()) {
      alert("Name must contain only letters and spaces and can't be longer than 30 chars!");
      return false;
    } if (!testUserName()) {
        alert("Name must contain only letters and spaces and can't be longer than 30 chars!")
        return false;
    } if (!testUserEmail()) {
        alert("Wrong email!");
        return false;
    }
    return true;
}
function checkUserEmail() {
    if (!testUserEmail()){
        alert("Wrong email!");
        return false;
    }
    return true;
}

    function showAddForm() {
        document.getElementById("contentdiv").className = "content-list-form";
        document.getElementById("form").style.display = "block";
    }