/**
 * 
 */
 
 console.log("hello!");
 
window.onload = function(){
    console.log("Inside onload function!");
    customerLogin();
    //grabBankAccounts();

}


// function customerLogin(){

//     document.getElementById(customer-login-form)
//     let xhr = new XMLHttpRequest();
    
//     const url = "customerLogin";
    
//     xhr.onreadystatechange = function(){

//         if(xhr.readyState == 4) {
            // if(xhr.status == 200) {
            //     window.location.href = "http://localhost:7000/customerDashboard.html"; 
            // }
            // else if(xhr.status == 401 ){
            //     console.log("401 - invalid login")
            //     window.location.href = "http://localhost:7000/mainpage.html";
            // }
//         }
//     }
//     xhr.open("POST", url);
//     xhr.send();
// }

function customerLogin(){

	let xhr = new XMLHttpRequest(); 

    
    //let name = document.getElementById(event.srcElement.id + "name").innerHTML;

    const customerLoginForm = document.getElementById("customer-login-form")
    const customerLoginButton = document.getElementById("customer-login-form-submit");


    customerLoginButton.addEventListener("click", (event) => {
        event.preventDefault();

        const customerUsername = customerLoginForm.username.value;
        const customerPswd = customerLoginForm.password.value;

        console.log(" username : " + customerUsername );

        const url = "/customerlogin";
        
        xhr.open("POST", url);
                    
        let formData = new FormData();
        formData.append("username", customerUsername);
        formData.append("password", customerPswd);
        xhr.open("POST",url)
        xhr.send(formData);

        console.log(formData);

        xhr.onreadystatechange = function(){
            if (xhr.readyState == 4) {
                if(xhr.status == 200) {
                    window.location.href = "http://localhost:7000/customerDashboard.html"; 
                }
                else if(xhr.status == 401 ){
                    console.log("401 - invalid login")
;

                //     document.getElementById("alert-warning-h5").innerHTML = "Login Failed !! Wrong Username/Password";

                //     let alert = document.getElementById("login-alert");
                //    alert.style.display = "block";

                window.alert("Login Failed !! Wrong Username/Password");
                }
            }
        }


    });
 

} // customerLogin function ends here





