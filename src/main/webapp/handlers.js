document.querySelector("#post").addEventListener("click", function () {
    var formData = new FormData(document.querySelector("#POSTcustomerForm"));
    var encData = new URLSearchParams(formData.entries());

    fetch("restservices/customers", { method: 'POST', body: encData })
        .then(response => response.json())
        .then(function (myJson) { console.log(myJson); });
});



document.querySelector("#put").addEventListener("click", function () {
    var id = document.querySelector("#put_id").value;
    var formData = new FormData(document.querySelector("#PUTcustomerForm"));
    var encData = new URLSearchParams(formData.entries());

    fetch("restservices/customers/" + id, { method: 'PUT', body: encData })
        .then(response => response.json())
        .then(function (myJson) { console.log(myJson); })
});



document.querySelector("#delete").addEventListener("click", function () {
    var id = document.querySelector("#delete_id").value;

    fetch("restservices/customers/" + id, { method: 'DELETE' })
        .then(function (response) {
            if (response.ok) // response-status = 200 OK
                console.log("Customer deleted!");
            else if (response.status == 404)
                console.log("Customer not found!");
            else console.log("Cannot delete customer!");
        })
});



document.querySelector("#get").addEventListener("click", function () {
    fetch("restservices/customers/")
        .then(response => Promise.all([response.status, response.json()]))
        .then(function ([status, myJson]) {
            if (status == 200) {
                console.log(myJson[0].name);
            } else {
                console.log(myJson.error);
            }
        })
        .catch(error => console.log(error.message));
});



document.querySelector("#upload").addEventListener("click", function () {
	var formData = new FormData(document.querySelector("#POSTuploadForm"));
    
    fetch("restservices/files", { method: 'POST', body: formData })
        .then(response => console.log(response.ok))
        .catch(error => console.log(error.message));
});