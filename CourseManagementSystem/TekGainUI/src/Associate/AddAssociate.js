import React, { useState } from "react";
import { addAssociate } from "../Service/AssociateService";

function AddAssociate(){
//  Create state variableas and event handing functions
const [associateId, setAssociateId] = useState("");
const [associateName, setAssociateName] = useState("");
const [associateAddress, setAssociateAddress] = useState("");
const [associateEmailId, setAssociateEmailId] = useState("");

const handleSubmit = async (event) => {
  event.preventDefault();
  const associate = {
   associateId,
   associateName,
   associateAddress,
   associateEmailId
  };

  try {
    const response = await addAssociate(associate);
    if (response.status === 200) {
      const data = response.data;
      document.getElementById("message").innerText = "Associate has been added successfully";
      
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
<h3>Add Associate</h3>
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
          <label htmlFor="associateName" className="col-sm-6 col-form-label text-center">Name</label>
          <div className="col-sm-6 my-auto">
          <input
            type="text"
            id="associateName"
            name="associateName"
            className='form-control w-75 border-dark'
            value={associateName}
            onChange={(e) => setAssociateName(e.target.value)}
          />
          </div>
        </div>

        <div className="form-group row border rounded mt-3">
          <label htmlFor="associateAddress" className="col-sm-6 col-form-label text-center">Address</label>
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

        <div className="form-group row border rounded mt-3">
          <label htmlFor="associateEmailId" className="col-sm-6 col-form-label text-center">Email Id</label>
          <div className="col-sm-6 my-auto">
          <input
            type="text"
            id="associateEmailId"
            name="associateEmailId"
            className='form-control w-75 border-dark'
            value={associateEmailId}
            onChange={(e) => setAssociateEmailId(e.target.value)}
          />
           </div>
        </div>

        
    <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Add</button>
   
</form>
<div id="message"></div>
    </div>
  );
}	 	  	     	   	 	   	 	
  export default AddAssociate;
  