import React, { useState } from "react";
import { viewFeedback } from "../Service/CourseService";

function CourseRating() {
  const [courseId, setCourseId] = useState("");
  const [rating, setRating] = useState("");
  const [message, setMessage] = useState(null);

  const fetchData = async () => {
    try {
      const response = await viewFeedback(courseId);
      if (response.status === 200) {
        const data = response.data;
        setRating(data);
        console.log(data[0])
        setMessage(<><div>Course Id: {data[1]}</div><div>Feedback Rating: {data[0]}</div></>);
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

  return (
    <div className="text-center">
    <form className="my-auto text-center">
      <h3>Course Feedback Rating</h3>
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
    {message && <div id="message">{message}</div>}
    </div>
  );
}

export default CourseRating;
