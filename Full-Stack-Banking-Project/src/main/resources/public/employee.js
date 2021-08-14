

const url = "employee";
 
window.onload = function(){

	grabUnapprovedAccounts();
	grabAllAccounts();
	
	// let updateButton = document.getElementById("updateButton");
	// updateButton.addEventListener('click',updateAccount);

	// let updateButton = document.getElementsByClassName("approveButton");
	// updateButton.addEventListener('click',updateAccount());
}

function updateButton(event){
	console.log(event);
	console.log(event.srcElement.id);
	
	let accountNumber = document.getElementById(event.srcElement.id + "accountNumber").innerHTML;
	let accountApproved = document.getElementById(event.srcElement.id + "accountApproved").innerHTML;
	
	console.log(accountNumber);
	console.log(accountApproved);

	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		
		switch(xhr.readyState){
			
			case 0:
				console.log("nothing, not initalized yet!");
				break;
			case 1: 
				console.log("connection established");
				break;
			case 2:
				console.log("request sent");
				break;
			case 3:
				console.log("awaiting request");
				break;
			case 4: 
				console.log(xhr.status)
				
				if(xhr.status == 200){
					removeRow();
					//grabUnapprovedAccounts();
				}
				else{
					//grabUnapprovedAccounts();
				}
		}
		
		
		
	}
	const url = "employeeApprovel";
	
	xhr.open("PUT",url);
	
	let bankAccount = {};
	bankAccount.accountNumber = accountNumber;
	bankAccount.accountApproved = (!accountApproved);
	xhr.send(
		JSON.stringify(bankAccount)
	);


	
	//grabUnapprovedAccounts();
}


function grabUnapprovedAccounts(){
	
	let xhr = new XMLHttpRequest();
	
	//const url = "localhost:9000/planet"
	const url = "employee";
	
	xhr.onreadystatechange = function(){
		
		console.log("hi!");
		
		switch(xhr.readyState){
			
			case 0:
				console.log("nothing, not initalized yet!");
				break;
			case 1: 
				console.log("connection established");
				break;
			case 2:
				console.log("request sent");
				break;
			case 3:
				console.log("awaiting request");
				break;
			case 4: 
				console.log(xhr.status)
				
				if(xhr.status == 200){
					console.log(xhr.responseText);
					
					let unapprovedAccountList = JSON.parse(xhr.responseText);
					
					console.log(unapprovedAccountList);
					
					unapprovedAccountList.forEach(
						element => {
							addRow(element);
						}
						
					)
					
					let arrayOfButtons = document.getElementsByClassName("btn btn-primary");
	

					for(let i = 0; i < arrayOfButtons.length; i++){
						arrayOfButtons[i].addEventListener('click',updateButton);
						console.log(arrayOfButtons[i]);
					}
					
				}
		}
		
		
		
	}
	
	xhr.open("GET",url);
		xhr.send();
}

function addRow(unapprovedAccount){
	
	
	let table = document.getElementById("unapproved-table")
	
	let tableRow = document.createElement("tr");

	let customerIdCol = document.createElement("td");
	let firstNameCol = document.createElement("td");
	let lastNameCol = document.createElement("td");
    let accountTypeCol = document.createElement("td");
	let accountNumberCol = document.createElement("td");
	let accountBalanceCol = document.createElement("td");
	let accountApprovedCol = document.createElement("td");

    let updateCol = document.createElement("td");

	
	tableRow.appendChild(customerIdCol);
	tableRow.appendChild(firstNameCol);
	tableRow.appendChild(lastNameCol);
	tableRow.appendChild(accountTypeCol);
	tableRow.appendChild(accountNumberCol);
	tableRow.appendChild(accountBalanceCol);
	tableRow.appendChild(accountApprovedCol);

    tableRow.appendChild(updateCol);
	
	table.appendChild(tableRow);

	customerIdCol.innerHTML = unapprovedAccount.customerID;
    firstNameCol.innerHTML = unapprovedAccount.firstName;
    lastNameCol.innerHTML = unapprovedAccount.lastName;
    accountTypeCol.innerHTML = unapprovedAccount.accountType;
    accountNumberCol.innerHTML = unapprovedAccount.accountNumber;
	accountBalanceCol.innerHTML = unapprovedAccount.accountBalance;
    accountApprovedCol.innerHTML = unapprovedAccount.accountApproved;
	
	
	tableRow.setAttribute("class", "unapproved-table-row");

	accountNumberCol.setAttribute("id",unapprovedAccount.accountNumber +"accountNumber");
	accountApprovedCol.setAttribute("id", unapprovedAccount.accountNumber + "accountApproved")

   updateCol.innerHTML = `<button id = "${unapprovedAccount.accountNumber}" type="submit"  class="btn btn-primary approveButton">Approve Account</button>`;
	
};

function removeRow() {
	//document.getElementsByClassName("unapproved-table-row").remove();

	var row = document.getElementsByClassName("unapproved-table-row");
	console.log(row);
	for (var i = 0; i < row.length; i++) {
		console.log(row[i]);
    	row[i].parentElement.removeChild(row[i]);
	
	}
	//grabUnapprovedAccounts();
	window.location.reload()
}



function grabAllAccounts(){
	
	let xhr = new XMLHttpRequest();
	
	const url = "employeeAllAccount";
	
	xhr.onreadystatechange = function(){
		
		switch(xhr.readyState){
			
			case 0:
				console.log("nothing, not initalized yet!");
				break;
			case 1: 
				console.log("connection established");
				break;
			case 2:
				console.log("request sent");
				break;
			case 3:
				console.log("awaiting request");
				break;
			case 4: 
				console.log(xhr.status)
				
				if(xhr.status == 200){
					console.log(xhr.responseText);
					
					let allAccountList = JSON.parse(xhr.responseText);
					
					console.log(allAccountList);
					
					allAccountList.forEach(
						element => {
							addRowAllAccount(element);
						}
						
					)
					
					let arrayOfButtons = document.getElementsByClassName("btn btn-primary");
	

					for(let i = 0; i < arrayOfButtons.length; i++){
						arrayOfButtons[i].addEventListener('click',updateButton);
						console.log(arrayOfButtons[i]);
					}
					
				}
		}	
		
	}
	
	xhr.open("GET",url);
		xhr.send();
}

function addRowAllAccount(allAccount){
	
	
	let table = document.getElementById("allAccount-table")
	
	let tableRow = document.createElement("tr");

	let customerIdCol = document.createElement("td");
	let firstNameCol = document.createElement("td");
	let lastNameCol = document.createElement("td");
    let accountTypeCol = document.createElement("td");
	let accountNumberCol = document.createElement("td");
	let accountBalanceCol = document.createElement("td");
	let accountApprovedCol = document.createElement("td");
	
	tableRow.appendChild(customerIdCol);
	tableRow.appendChild(firstNameCol);
	tableRow.appendChild(lastNameCol);
	tableRow.appendChild(accountTypeCol);
	tableRow.appendChild(accountNumberCol);
	tableRow.appendChild(accountBalanceCol);
	tableRow.appendChild(accountApprovedCol);
	
	table.appendChild(tableRow);

	customerIdCol.innerHTML = allAccount.customerID;
    firstNameCol.innerHTML = allAccount.firstName;
    lastNameCol.innerHTML = allAccount.lastName;
    accountTypeCol.innerHTML = allAccount.accountType;
    accountNumberCol.innerHTML = allAccount.accountNumber;
	accountBalanceCol.innerHTML = allAccount.accountBalance;
    accountApprovedCol.innerHTML = allAccount.accountApproved;
	
};