import React, { useState } from "react";
import { register } from "../Service/AdmissionService";

function AssociateRegistration(){
  const [courseId, setCourseId] = useState("");
  const [associateId, setAssociateId] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      const response = await register(associateId,courseId);
      if (response.status === 200) {
        const data = response.data;
        document.getElementById("message").innerText = `Registered successful, Your registration id:${data.registrationId}`
       
        
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      document.getElementById("message").innerText =  error.response.data;
    }
  };

  //  Create state variableas and event handing functions

  return (
    <div className="text-center">
    <form className="my-auto text-center">
    <h3>Associate Registration Form</h3>
    <div className="form-group row border rounded mt-3">
      <label htmlFor="courseId" className="col-sm-6 col-form-label text-center">
        Course Id
      </label>
      <div className="col-sm-6 my-auto">
        <input
          type="text"
          id="courseId"
          name="courseId"
          className="form-control w-75 border-dark"
          value={courseId}
          onChange={(e) => setCourseId(e.target.value)}
        />
      </div>
    </div>
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
    <button type="button" id="submit" onClick={handleSubmit} className="ml-3">
    Register Now
    </button>
  </form>
   <div id="message"></div>
   </div>
  );
}	 	  	  
  
  
  export default AssociateRegistration;
  	 	  	  		    	   	 	   	 	
