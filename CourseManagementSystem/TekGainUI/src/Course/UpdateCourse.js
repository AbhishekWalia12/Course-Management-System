import React, { useState } from "react";

import {updateCourse} from "../Service/CourseService"

function UpdateCourse(){
  //  Create state variableas and event handing functions
  const [fees, setFees] = useState("");
  const [courseId, setCourseId] = useState("");

  const handleSubmit = async () => {
    try {
      const response = await updateCourse(courseId, fees);
      console.log(response.status)
      if (response.status===200) {
        document.getElementById("message").innerText = "Course updated successfully";
        
      } else {
        const error = response.data.message;
        throw new Error(error);
      }
    } catch (error) {
      document.getElementById("message").innerText =  error.response.data;
    }
  };

  return (
    <form className="my-auto text-center" >
            <h3>Update Course Fee</h3>
            <div className="form-group row border rounded mt-3">
          <label htmlFor="courseId" className="col-sm-6 col-form-label text-center">Course Id</label>
          <div class="col-sm-6 my-auto">
          <input
            type="text"
            id="courseId"
            name="courseId"
            className='form-control w-75 border-dark'
            value={courseId}
            onChange={(e) => setCourseId(e.target.value)}
          />
          </div>
        </div>
        <div className="form-group row border rounded mt-3">
          <label htmlFor="fees" className="col-sm-6 col-form-label text-center">Fees</label>
          <div className="col-sm-6 my-auto">
          <input
            type="text"
            id="fees"
            name="fees"
            className='form-control w-75 border-dark'
            value={fees}
            onChange={(e) => setFees(e.target.value)}
          />
          </div>
        </div>
        <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Update Course</button>
         <div id="message"></div>
    </form>
  );
}	 	  	  		    	   	 	   	 	
  export default UpdateCourse;
  