import React, { useState } from "react";

import {deactivate} from "../Service/CourseService"
function CourseDeactivate(){
  //  Create state variableas and event handing functions
  const [courseId, setCourseId] = useState("");
  const handleSubmit = async () => {
    try {
      const response = await deactivate(courseId);
      if (response.status === 200) {
        const data = response.data;
        document.getElementById("message").innerText = `Course ${data.courseId} is deactivated successfully`;
      } else {
        const error = await response.text();
        throw new Error(error);
      }
    } catch (error) {
      document.getElementById("message").innerText = error.message;
    }
  };

  return (
    <div className="text-center">
    <form className="my-auto text-center">
    <h3>Course Deactivation</h3>
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
    <button type="button" id="submit" onClick={handleSubmit} className="ml-3">
      Submit
    </button>
     
  </form>
  <div id="message" className="my-auto"></div>
  </div>

  );
}	 	  	  
   	  	  		    	   	 	   	 	
  export default CourseDeactivate;
  