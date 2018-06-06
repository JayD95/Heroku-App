var person = {
    firstName: "William",
    lastName: "Johnson",
    figures: {},
    
    addFigures : function(v,c){
    	this.figures[v]=c;
    }
    
}
person.addFigures("OODC", 7);

function myFunc(x) {
	var z = x + y;
	if (x === "a") {
		var y = "b";
		
	}
	return z ;
	
}
console.log(myFunc("a"));

console.log(person.figures);

