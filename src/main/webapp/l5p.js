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
					var inp = "<input type=button id=wijzig value=Wijzig! />"
					var inpp = "<input type=button id=delete value=Delete! />"

					for(const c of printresp){
						var row = document.createElement("tr");
						row.setAttribute("id", c.Name);
						row.innerHTML = "<td>" + c.Name  + "</td>" + "<td>" + c.Hoofdstad  + "</td>" +  "<td>" + c.Regio  + "</td>" + 
						"<td>" + c.Oppervlakte  + "</td>" + "<td>" + c.Inwoners + "</td>" + "<td>" + inp + inpp + "</td>" ;
						
						row.addEventListener("click", function(){
							var date = new Date();
							showWeather(c.Latitude, c.Longitude, c.Name);
						});
						bod.appendChild(row);
						
						}
					});
	}

loadCountries();