function displaynum(num){
	document.getElementById("d").value += num;
}

function c(val){
	document.getElementById("d").value = val;
}

function ans(){
	c(eval(document.getElementById("d").value))
}