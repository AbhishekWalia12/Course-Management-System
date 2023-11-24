import React, { useState } from "react";
import {addCourseDetails} from "../Service/CourseService"

function  AddCourse() {
//  Create state variableas and event handing functions
const [courseId, setCourseId] = useState("");
  const [courseName, setCourseName] = useState("");
  const [fees, setFees] = useState("");
  const [duration, setDuration] = useState("");
  const [courseType, setCourseType] = useState("");

  
  const handleSubmit = async (event) => {
    event.preventDefault();
    const course = {
      courseId,
      courseName,
      fees,
      duration,
      courseType,
    };
  
    try {
      const response = await addCourseDetails(course);
      if (response.status === 200) {
        const data = response.data;
        document.getElementById("message").innerText = "Course added successfully";
        
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
<h3>Add Course</h3>
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
          <label htmlFor="courseName" className="col-sm-6 col-form-label text-center">Course Name</label>
          <div class="col-sm-6 my-auto">
          <input
            type="text"
            id="courseName"
            name="courseName"
            className='form-control w-75 border-dark'
            value={courseName}
            onChange={(e) => setCourseName(e.target.value)}
          />
          </div>
        </div>

        <div className="form-group row border rounded mt-3">
          <label htmlFor="fees" className="col-sm-6 col-form-label text-center">Fees</label>
          <div class="col-sm-6 my-auto">
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

        <div className="form-group row border rounded mt-3">
          <label htmlFor="duration" className="col-sm-6 col-form-label text-center">Duration in Months</label>
          <div class="col-sm-6 my-auto">
          <input
            type="text"
            id="duration"
            name="duration"
            className='form-control w-75 border-dark'
            value={duration}
            onChange={(e) => setDuration(e.target.value)}
          />
           </div>
        </div>

        <div className="form-group row border rounded mt-3">
          <label htmlFor="courseType" className="col-sm-6 col-form-label text-center">Course Type</label>
          <div class="col-sm-6 my-auto">
          <input
            type="text"
            id="courseType"
            name="courseType"
            className='form-control w-75 border-dark'
            value={courseType}
            onChange={(e) => setCourseType(e.target.value)}
          />
        </div>
        </div>
    <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Add Course</button>
   
</form>
<div id="message"></div>
    </div>
   
    
 
  
  );
}	 	  	  		    	   	 	   	 	
export default AddCourse;

