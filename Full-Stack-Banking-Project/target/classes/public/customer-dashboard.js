
 console.log("hello!");
const url = "customer";

window.onload = function(){
    console.log("Inside onload function!");
    grabBankAccount();
}

function grabBankAccount(){
    let xhr = new XMLHttpRequest();
    
    const url = "customer";
    
    xhr.onreadystatechange = function(){

        if(xhr.readyState == 4) {
            if(xhr.status == 201){
                console.log(xhr.responseText);
                
                let bankList = JSON.parse(xhr.responseText);
                
                console.log(bankList);

                bankList.forEach(
                    element => {
                        addRow(element);
                    }
                    
                )
                
            }
        }
		
	}
	
	xhr.open("GET",url);
		xhr.send();
}

function addRow(bank){

    document.getElementById("customerName-cutsomerDashbaord").innerHTML = (" Welcome back, " + bank.firstName+ " " +bank.lastName );




	let table = document.getElementById("bank-table")
	
	let tableRow = document.createElement("tr");
	let accountCol = document.createElement("td");
    let accountTypeCol = document.createElement("td");
	let balanceCol = document.createElement("td");
    let accountApprovedCol = document.createElement("td");
	
	
	tableRow.appendChild(accountCol);
	tableRow.appendChild(accountTypeCol);
    tableRow.appendChild(balanceCol);
    tableRow.appendChild(accountApprovedCol);
	
	table.appendChild(tableRow);
	
	accountCol.innerHTML = bank.accountNumber;
    accountTypeCol.innerHTML = bank.accountType;
	balanceCol.innerHTML = bank.accountBalance;
    accountApprovedCol.innerHTML = bank.accountApproved;

	};


/** 
    function deposite(){

        let xhr = new XMLHttpRequest(); 
    
        
        //let name = document.getElementById(event.srcElement.id + "name").innerHTML;
    
        const depositForm = document.getElementById("customer-deposit-form")
        const depositFormSubmit = document.getElementById("deposit-form-submit");
    
    
        depositFormSubmit.addEventListener("click", (event) => {
            event.preventDefault();
    
            const depositAmount = depositForm.depositAmount.value;
            const accountNumber = depositForm.accountNumber.value;
    
            console.log(" depositAmount : " + depositAmount );
    
            const url = "/customerDeposit";
            
            xhr.open("POST", url);
                        
            let formData = new FormData();
            formData.append("depositAmount", depositAmount);
            formData.append("accountNumber", accountNumber);

            xhr.open("POST",url)
            xhr.send(formData);
    
            console.log(formData);
    
            xhr.onreadystatechange = function(){
                if (xhr.readyState == 4) {
                    if(xhr.status == 201) {
                        window.location.href = "http://localhost:7000/customerDashboard.html"; 
                    }
                    else if(xhr.status == 401 ){
                        console.log("401 - invalid login")
                        window.alert("Unauthorized User, please login");
                    }
                    else if(xhr.status == 405) {
                        window.alert("Please enter correct deposite amount or account number");
                    }else{
                        window.location.href = "http://localhost:7000/customerDashboard.html"; 
                    }
                }
            }
    
    
        });
     
    
    } 
    
    */