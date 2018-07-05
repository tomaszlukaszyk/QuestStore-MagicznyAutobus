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
function showContent() {
  var temp = document.getElementsByTagName("template")[0];
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