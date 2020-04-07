


//$(document).ready(function() {
//    // show the alert
//    setTimeout(function() {
//        $(".alert").alert('close');
//    }, 2000);
//});

//$(document).ready(function() {
//  $("#success-alert").hide();
//  $("#myWish").click(function showAlert() {
//    $("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
//      $("#success-alert").slideUp(500);
//    });
//  });
//});

const inputs = document.querySelectorAll(".input");


function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});