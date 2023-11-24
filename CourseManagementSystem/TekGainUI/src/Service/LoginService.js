export let logStatus=false;
export  default async function validateUser(userName,pass) {
   let headers = new Headers();

   headers.append('Content-Type', 'application/json');
   headers.append('Accept', 'application/json');
   headers.append('Origin', 'http://localhost:3000');
 
   let obj = {
     username: userName,
     password: pass
   };
 
   try {
     const response = await fetch("http://localhost:9098/app/signin", {
       method: 'POST',
       headers: headers,
       body: JSON.stringify(obj)
     });
 
     if (response.ok) {
       const data = await response.json();
       let token = data.accessToken;
       logStatus = true;
       localStorage.setItem("token", "Bearer "+token);
       localStorage.setItem("username",data.username)
       return true;
     } else {
       logStatus = false;
       return false;
     }
   } catch (error) {
     console.log(error);
     logStatus = false;
     return false;
   }
 }

       
    
      
  
      
  	 	  	  		    	   	 	   	 	
