const bnt=document.getElementById("login");

bnt.addEventListener("click", async function login(){
    let email=document.getElementById("email").value;
    let password=document.getElementById("password").value;
    const response=await fetch("http://localhost:8080/login",{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body:JSON.stringify({
            email:email,
            password:password
        })
    });
    const data=await response.json();
    if(response.ok){
        localStorage.setItem("accessToken",data.data.accessToken);
        
        localStorage.setItem("refreshToken",data.data.refreshToken);
        window.location.href="home.html";
        console.log("Đăng nhập thành công: ",data);

    }else{
        console.error("Đăng nhạp thất bại",data.message);
        window.location.href="login.html";
    }
});