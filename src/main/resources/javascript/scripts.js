function includeHTML() {
    var z, i, elmnt, file, xhttp;
    /*loop through a collection of all HTML elements:*/
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
      elmnt = z[i];
      /*search for elements with a certain atrribute:*/
      file = elmnt.getAttribute("include");
      if (file) {
        /*make an HTTP request using the attribute value as the file name:*/
        xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4) {
            if (this.status == 200) {elmnt.innerHTML = this.responseText;}
            if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
            /*remove the attribute, and call this function once more:*/
            elmnt.removeAttribute("include");
            includeHTML();
          }
        }      
        xhttp.open("GET", file, true);
        xhttp.send();
        /*exit the function:*/
        return;
      }
    }
  };

// show information in student header on site loading
function showWallet() {
  var walletTemplate = 0;
  var temp = document.getElementsByTagName("template")[walletTemplate];
  var clon = temp.content.cloneNode(true);
  document.getElementById("informations").appendChild(clon);
};

function showStudents(id){

    var writeTo = document.getElementById("insert-student");
    
    if(id == "2018.1"){
        writeTo.innerHTML = "2018.1<br>Ala Kot";
    } else if (id == "2018.2"){
        writeTo.innerHTML = "2018.2<br>Bob Budowniczy";
    } else if (id == "2018.3"){
        writeTo.innerHTML = "2018.3<br>Adam Małysz<br>Czaka Laka";
    }
};
  

function adminViewMentors() {
  var mentorformtemplate = 2;
  var temp = document.getElementsByTagName("template")[mentorformtemplate];
  var clon = temp.content.cloneNode(true);
  var content = document.getElementById("contentdiv");
  content.setAttribute("style","grid-template-columns: 5fr 5fr; grid-template-areas: 'a b';");
  content.appendChild(clon);
}

function adminViewPanel() {
  var panelViewTemplate = 1;
  var temp = document.getElementsByTagName("template")[panelViewTemplate];
  var clon = temp.content.cloneNode(true);
  var content = document.getElementById("nav");

  content.appendChild(clon);

}


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

function testMentorName() {
  var name, pattern;
  name = document.getElementById("mentorname").value;
  pattern = /^([A-Za-z ]{2,30})$/;
  return pattern.test(name);
}

function testMentorEmail() {
  var email, pattern;
  email = document.getElementById("mentoremail").value;
  pattern = /^[^\s]([A-Za-z0-9_.]{2,20})@([A-Za-z0-9]{2,20}).([a-z]{2,13})$/;
  return pattern.test(email);

}

function testMentorSurname() {
  var surname, pattern;
  surname = document.getElementById("mentorsurname").value;
  pattern = /^([A-Za-z ]{2,30})$/;
  return pattern.test(surname);

}

function checkMentorInputs() {
    if (!testMentorSurname()) {
      alert("Name must contain only letters and spaces and can't be longer than 30 chars!");
      return false;
    } if (!testMentorName()) {
        alert("Name must contain only letters and spaces and can't be longer than 30 chars!")
        return false;
    } if (!testMentorEmail()) {
        alert("Wrong email!");
        return false;
    }
    alert("Sent!")
    return true;
}

function showDetails(id) {
    var className = document.getElementById(id);
    var mentors = document.getElementById("list-mentor");
    var students = document.getElementById("list-student");

    var class_ = className.textContent;

    if (class_ == "2018-1") {
        students.innerHTML = "<ul class='list'><li>Barbara Kat</li><li>Jakub Marmol</li><li>Pawel Laska</li></ul>";
        mentors.innerHTML = "<ul class='list'><li>Bartosz Baron</li></ul>"
    } else if (class_ == "2018-2") {
        students.innerHTML = "<ul class='list'><li>Katarzyna Brzydal</li><li>Ludwik Fajka</li></ul>";
        mentors.innerHTML = "<ul class='list'><li>Bartosz Baron</li></ul>"
    } else if (class_ == "2018-3") {
        students.innerHTML = "<ul class='list'><li>Ala Kot</li><li>Jan Kowalski</li><li>Janusz Nowak</li></ul>";
        mentors.innerHTML = "<ul class='list'><li>Bartosz Baron</li></ul>"
    }
}

    function mentorIsHere(){
        var content = document.getElementById("content");
        var mentorVisible = document.getElementsByClassName("mentor");

        for(var i=0; i < mentorVisible.length; i++){
            mentorVisible[i].style.display = "flex";
        }
            content.setAttribute("style", "grid-template-areas:'name form';grid-template-columns: 2fr 2fr;");
    };

    function studentIsHere(){
        var content = document.getElementById("content");
        var mentorVisible = document.getElementsByClassName("mentor");

        for(var i=0; i < mentorVisible.length; i++){
            mentorVisible[i].style.display = "none";
        }
            content.setAttribute("style", "grid-template-areas:'name';grid-template-columns: 2fr;");
    };


function test() {
  alert("Logged in!");
  document.location.href='profile.html';
}



