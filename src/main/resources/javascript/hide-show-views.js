function hideElem(elemId) {
    var elem = document.getElementById(elemId);
    elem.style.display = 'none';
}
function showElem(elemId) {
    var elem = document.getElementById(elemId);
    elem.style.display = 'grid';
}

function switchMentor() {
    hideElem("student-view");
    showElem("mentor-view");
}

function switchStudent() {
    hideElem("mentor-view");
    showElem("student-view");
}
