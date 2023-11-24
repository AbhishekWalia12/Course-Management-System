import React, { useState,useEffect } from "react";
import '../App.css';
import { viewFeedbackByCourseId } from "../Service/AdmissionService";

function ViewFeedback(){

 //  Create state variableas and event handing functions

  const [courseId, setCourseId] = useState("");
  const [message, setMessage] = useState(null);
  const [data, setData] = useState(null);

  const fetchData = async () => {
    try {
      const response = await viewFeedbackByCourseId(courseId);
      if (response.status === 200) {
        const data = response.data;
        setData(data);
        setMessage("");
      } else {
        const error = response.data.message;
        throw new Error(error);
      }
    } catch (error) {
      setMessage( error.response.data);
    }
  };
  
 

  const handleSubmit = () => {
    if (courseId) {
      fetchData();
    }
  };

  useEffect(() => {
    if (message) {
      setData(null);
    }
  }, [message]);
  
  useEffect(() => {
    if (data) {
      setMessage(null);
    }
  }, [data]);

  return (
    <div className="text-center my-auto">
    <form className="my-auto text-center">
      <h3>View Course By Id</h3>
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
      View Course
      </button>
    </form>
    {message && <div id="message">{message}</div>}
        {data && (
        <table className="table table-border w-50 m-auto">
          <tbody>
          <tr>
      <th>Course Id</th>
      <th>Feedback</th>
    </tr>
    <tr>
      <td>{courseId}</td>
      <td>
        {data.map((item, index) => (
          <div key={index}>{item}</div>
        ))}
      </td>
    </tr>
          </tbody>
        </table>
      )}  
    </div>

  );
}	 	  	  
  
  
  export default ViewFeedback;
  