import React, { useState } from "react";
import { updateAssociate } from "../Service/AssociateService";

function UpdateAssociate(){
  //  Create state variableas and event handing functions
  const [associateId, setAssociateId] = useState("");
  const [associateAddress, setAssociateAddress] = useState("");
  const handleSubmit = async () => {
    try {
      const response = await updateAssociate(associateId,associateAddress);
      if (response.status === 200) {
        document.getElementById("message").innerText = "Associate address updated successfully";
        
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      document.getElementById("message").innerText =  error.response.data;
    }
  };

  return (

    <div className="text-center">
    <form className="my-auto text-center" >
    <h3>Update Associate Information</h3>
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
            <div className="form-group row border rounded mt-3">
              <label htmlFor="associateAddress" className="col-sm-6 col-form-label text-center">Update Address</label>
              <div className="col-sm-6 my-auto">
              <input
                type="text"
                id="associateAddress"
                name="associateAddress"
                className='form-control w-75 border-dark'
                value={associateAddress}
                onChange={(e) => setAssociateAddress(e.target.value)}
              />
              </div>
            </div>
    
            
    
            
        <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Update Address</button>
       
    </form>
    <div id="message"></div>
        </div>
  );
}	 	  	  	    	   	 	   	 	
  export default UpdateAssociate;
  