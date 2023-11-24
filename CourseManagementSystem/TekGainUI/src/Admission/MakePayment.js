import React,{useState} from 'react';
import { makePayment } from '../Service/AdmissionService';

function MakePayment(){
   //  Create state variableas and event handing functions
   const [registrationId, setRegistrationId] = useState("");
   const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      const response = await makePayment(registrationId);
      if (response.status === 200) {
        // Payment successful
        const data = response.data;
        const redirectUrl = data.split("redirect:")[1];
        if (redirectUrl) {
          
          window.open(redirectUrl, '_blank'); 
        } else {
          throw new Error("Invalid redirect link in the response data.");
        }
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      document.getElementById("message").innerText = error.response.data;
    }
  };


  return (
    <div className="text-center">
    <form className="my-auto text-center">
    <h3>Make Payment for Registered Course</h3>
    <div className="form-group row border rounded mt-3">
      <label htmlFor="registrationId" className="col-sm-6 col-form-label text-center">
      Register Number
      </label>
      <div className="col-sm-6 my-auto">
        <input
          type="text"
          id="registrationId"
          name="registrationId"
          className="form-control w-75 border-dark"
          value={registrationId}
          onChange={(e) => setRegistrationId(e.target.value)}
        />
      </div>
    </div>
    <button type="button" id="submit" onClick={handleSubmit} className="ml-3">
    Pay Now
    </button>
    </form>
    <div id="message"></div>
    </div>
  );
}	 	  	  	    	   	 	   	 	
  export default MakePayment;
  