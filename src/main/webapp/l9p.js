function initPage(){
	fetch("https://ipapi.co/json")
	.then(function(response){
		return response.json();
		})
		.then(function(logjson){
			console.log(logjson);
			return logjson;
			})
			.then(function(printresp){
				//var textnode = document.createTextNode(printresp.city)
				document.getElementById("landcode").innerHTML =  "Landcode:" + printresp.country;
				document.getElementById("land").innerHTML = "Land: " + printresp.country_name;
				document.getElementById("regio").innerHTML = "Regio: " + printresp.region;
				document.getElementById("stad").innerHTML = "Stad: " +  printresp.city;
				document.getElementById("postcode").innerHTML = "Postcode: " + printresp.asn;
				document.getElementById("latitude").innerHTML =  "Latitude: " + printresp.latitude;
				document.getElementById("longitude").innerHTML = "Longitude: " + printresp.longitude;
				document.getElementById("ip").innerHTML = "IP:" + printresp.ip;
				showWeather(printresp.latitude, printresp.longitude, printresp.city);
				var mijnLoc = document.getElementById("stad");
				mijnLoc.addEventListener("click", function(){
					console.log("test");
					showWeather(printresp.latitude, printresp.longitude, printresp.city);
				});
				})
			};
initPage();
			
function showWeather(latitude, longitude, city){
	if(window.localStorage.getItem(city)==null || Date.now() - new Date(JSON.parse(window.localStorage.getItem(city)).calltimer) >= 10000 ) {
		console.log("test1");
		fetch("http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=49cc7bbd26a2fbfc7ac2bde6d821fbf7")
		.then(function(response){
			return response.json();
			})
			.then(function(printresp){
				console.log(printresp)
				var currentdate = Date.now();
				printresp.calltimer = currentdate;
				document.getElementById("name").innerHTML = city;
				document.getElementById("temperatuur").innerHTML = "Temperatuur: " + printresp.main.temp;
				document.getElementById("luchtvochtigheid").innerHTML = "Luchtvochtigheid:" + printresp.main.humidity;
				document.getElementById("windsnelheid").innerHTML = "Windsnelheid: " + printresp.wind.speed;
				document.getElementById("windrichting").innerHTML = "Windrichting:" + printresp.wind.deg;
				document.getElementById("zonsopgang").innerHTML = "Zonsopgang: " + printresp.sys.sunrise;
				document.getElementById("zonsondergang").innerHTML = "Zonsondergang:" + printresp.sys.sunset;
				window.localStorage.setItem(city, JSON.stringify(printresp));
				})
	
				
	}else{
		(function(printrespo){
			console.log("test");
			printresp.calltimer = currentdate;
			document.getElementById("name").innerHTML = city;
			document.getElementById("temperatuur").innerHTML = "Temperatuur: " + printresp.main.temp;
			document.getElementById("luchtvochtigheid").innerHTML = "Luchtvochtigheid:" + printresp.main.humidity;
			document.getElementById("windsnelheid").innerHTML = "Windsnelheid: " + printresp.wind.speed;
			document.getElementById("windrichting").innerHTML = "Windrichting:" + printresp.wind.deg;
			document.getElementById("zonsopgang").innerHTML = "Zonsopgang: " + printresp.sys.sunrise;
			document.getElementById("zonsondergang").innerHTML = "Zonsondergang:" + printresp.sys.sunset;
			})
		};
	}
			
function loadCountries(){
	fetch("http://localhost:8081/firstapp/restservices/countries")
	.then(function(response){
			return response.json();
			})
			.then(function(logjson){
				console.log(logjson)
				return logjson;
				})
				.then(function(printresp){
					var bod = document.querySelector("tbody");
					for(const c of printresp){
						var inp = '<input type="submit" id="edit'+c.code+'" value="Wijzig">'
						var inpp = '<input type="submit" id="del'+c.code+'" value="Verwijder">'
						var row = document.createElement("tr");
						row.setAttribute("id", c.code);
						row.innerHTML = "<td>" + c.name  + "</td>" + "<td>" + c.capital  + "</td>" +  "<td>" + c.region  + "</td>" + 
						"<td>" + c.surface  + "</td>" + "<td>" + c.population + "</td>" + "<td>" + inp + inpp + "</td>" ;
						
						 
						row.addEventListener("click", function(){
							var date = new Date();
							showWeather(c.latitude, c.longitude, c.name);
						});
						bod.appendChild(row);
						
						 
						 document.querySelector("#edit"+c.code).addEventListener("click", wijzigFunc);
						 
						 document.querySelector("#del"+c.code).addEventListener("click", verwijderFunc);
						
						}
					});
	}



function wijzigFunc(){
	modal.style.display = "block";
	console.log("Wijzig functie test! " + this.parentElement.parentElement.id);
	fetch("restservices/countries/" + this.parentElement.parentElement.id)
	.then(response => response.json())
	.then(function(myJson){
		console.log(myJson);
		document.getElementById("wijzigGegevens").innerHTML = '<input name="name" type="text" value="'+ myJson.name +  '">LAND<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input name="capital" type="text" value="'+ myJson.capital +  '">CAPITAL<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input name="region" type="text" value="'+ myJson.region +  '">REGION<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input name="surface" type="number" value="'+ myJson.surface + '">SURFACE<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input name="population" type="number" value="'+ myJson.population + '">POPULATION<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input id="countrycode" name="countrycode" type="text" value="'+ myJson.code	 +  '" readonly>CODE<br><br>';
		document.getElementById("wijzigGegevens").innerHTML += '<input id="put" type="button" value="Verzend"><br><br>';
		document.querySelector("#put").addEventListener("click", function(){
			putHandler(myJson.code);
		});
	});
}

var putHandler = function(code) {
	var id = document.getElementById("countrycode").value;
    var formData = new FormData(document.querySelector("#wijzigGegevens"));
    var encData = new URLSearchParams(formData.entries());

    fetch("restservices/countries/" + code, { method: 'PUT', body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")} })
//        .then(response => response.json())
        .then(function (myJson) { 
        	if (myJson.ok)
        		console.log("Country edited!");
        	else console.log("Cannot delete country!"), window.alert("Log in!");
        })
};

function verwijderFunc(){
	var id = this.parentElement.parentElement.id;
	fetch("restservices/countries/" + id, { method: 'DELETE', headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")} })
		.then(function (response) {
			if (response.ok)
				console.log("Country deleted!");
			else if (response.status == 404)
				console.log("Country not found!");
			else console.log("Cannot delete country!"), window.alert("Log in!");
		})
}


var voegToe = document.querySelector("#voegToe");
voegToe.addEventListener("click", addCountryFuncForm);
function addCountryFunc() {
	var formData = new FormData(document.querySelector("#wijzigGegevens"));
    var encData = new URLSearchParams(formData.entries());
    
    fetch("restservices/countries", { method: 'POST', body: encData, headers: {'Authorization': 'Bearer ' + window.sessionStorage.getItem("myJWT")} })
    .then(function (myJson) { 
    	if (myJson.ok)
			console.log("Country added!");
		else if (myJson.status == 404)
			console.log("Country not found!");
		else console.log("Cannot add country!"), window.alert("Log in!");
    })
}

function addCountryFuncForm() {
	modal.style.display = "block";
	document.getElementById("wijzigGegevens").innerHTML += '<input id="countrycode" name="countrycode" type="text" value="XP" required>CODE<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="iso3" type="text" value="XEP" required>ISO3<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="name" type="text" value="WELIT" required>NAME<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="continent" type="text" value="Haha" required>CONTINENT<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="region" type="text" value="Hoho" required>REGION<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="surface" type="number" value="123.00" required>SURFACEAREA<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="indepyear" type="number" value="1990">INDEPYEAR<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="population" type="number" value="12345" required>POPULATION<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="lifeexpectancy" type="text" value="21.00">LIFEEXPECTANCY<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="gnp" type="text" value="12.00">GNP<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="gnpoid" type="text" value="2134.00">GNPOID<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="localname" type="text" value="Habam" required>LOCALNAME<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="governmentform" type="text" value="MyMom" required>GOVERNMENTFORM<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="headofstate" type="text" value="Represent" >HEADOFSTATE<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="latitude" type="number" value="40.00">LATITUDE<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="longitude" type="number" value="50.00" >LONGITUDE<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="capital" type="text" value="Java">CAPITAL<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input id="post" type="button" value="Verzend"><br><br>';
	document.querySelector("#post").addEventListener("click", addCountryFunc);

}

var logIn = document.querySelector("#logIn");
logIn.addEventListener("click", addLoginForm);

function addLoginForm() {
	modal.style.display = "block";
	document.getElementById("wijzigGegevens").innerHTML += '<input name="username" type="text" value="" required>USERNAME<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input name="password" type="password" value="" required>PASSWORD<br><br>';
	document.getElementById("wijzigGegevens").innerHTML += '<input type="button" id="login" value="login"><br><br>';
	document.querySelector("#login").addEventListener("click", login);

}

function login(event) {
	console.log("Login functie");
	var formData = new FormData(document.querySelector("#wijzigGegevens"));
	var encData = new URLSearchParams(formData.entries());
	modal.style.display = "none";

	fetch("restservices/authentication", { method: 'POST', body: encData })
    .then(function(response) {
    	if (response.ok) return response.json();
    	else throw "Wrong username/password";
    	})
    	.then(myJson => window.sessionStorage.setItem("myJWT", myJson.JWT))
    	.catch(error => console.log(error));
}




loadCountries();

//Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}


// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}