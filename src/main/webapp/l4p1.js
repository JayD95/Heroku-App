function myIntervalFunction() {
	var x = document.getElementById("txt1").value;
	var y = document.getElementById("test").innerHTML = x;
	console.log(y);
};



var intervalID = setInterval(myIntervalFunction, 5000);


