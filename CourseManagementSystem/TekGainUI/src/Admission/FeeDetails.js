import React,{useState} from 'react';
import { calculateFees } from '../Service/AdmissionService';

function TotalFees(){
   //  Create state variableas and event handing functions
   const [associateId, setAssociateId] = useState("");

   const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      const response = await calculateFees(associateId);
      if (response.status === 200) {
        const data = response.data;
        document.getElementById("message").innerText = `Total Fee : ${data}`
       
        
      } else {
        
        throw new Error(response.data.message);
      }
    } catch (error) {
      
      document.getElementById("message").innerText = error.response.data;
    }
  };

  return (
<div className="text-center">
    <form className="my-auto text-center" >
    <h3>Total Fee Details</h3>
    <div className="form-group row border rounded mt-3">
              <label htmlFor="associateId" className="col-sm-6 col-form-label text-center">Associate Id</label>
              <div className="col-sm-6 my-auto">
              <input
                type="text"
                id="associateId"
                name="associateId"
                className='form-control w-75 border-dark'
                value={associateId}
                onChange={(e) => setAssociateId(e.target.value)}
               
              />
              </div>
            </div>
        <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Submit</button>
       
    </form>
    <div id="message"></div>
    </div>

  );
}	 	  	     	   	 	   	 	
  export default TotalFees;
  