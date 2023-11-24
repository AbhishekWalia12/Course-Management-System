import React, { useState, useEffect } from "react";
import { viewCourseById } from "../Service/CourseService";

function ViewCourse() {
  const [courseId, setCourseId] = useState("");
  const [message, setMessage] = useState(null);
  const [data, setData] = useState(null);

  const fetchData = async () => {
    try {
      const response = await viewCourseById(courseId);
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
              <th>Course Name</th>
              <th>Duration</th>
              <th>Course Type</th>
              <th>Fees</th>
              <th>Rating</th>
            </tr>
            <tr>
              <td>{data.courseId}</td>
              <td>{data.courseName}</td>
              <td>{data.duration}</td>
              <td>{data.courseType}</td>
              <td>{data.fees}</td>
              <td>{data.rating}</td>
            </tr>
          </tbody>
        </table>
      )}    
    </div>
  );
}

export default ViewCourse;
