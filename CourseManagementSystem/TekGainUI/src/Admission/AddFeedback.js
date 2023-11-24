import React,{useState} from 'react';
import { addfeedback } from '../Service/AdmissionService';

function AddFeedback(){
  //  Create state variableas and event handing functions
  const [registrationId, setRegistrationId] = useState("");
  const [feedback, setFeedback] = useState("");
  const [feedbackRating, setFeedbackRating] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
  
    try {
      const response = await addfeedback(registrationId,feedback,feedbackRating);
      if (response.status === 200) {
        const data = response.data;
        document.getElementById("message").innerText = `Feedback added successfully`
       
        
      } else {
        throw new Error(response.data.message);
      }
    } catch (error) {
      document.getElementById("message").innerText =  error.response.data;
    }
  };

  return (
    <div className="text-center">
    <form className="my-auto text-center">
    <h3>Add Feedback</h3>
    <div className="form-group row border rounded mt-3">
      <label htmlFor="registrationId" className="col-sm-6 col-form-label text-center">
      Register Number
      </label>
      <div className="col-sm-6 my-auto">
        <input
          type="text"
          id="registrationId"
          name="registrationId"
          className="form-control w-75 border-dark"
          value={registrationId}
          onChange={(e) => setRegistrationId(e.target.value)}
        />
      </div>
    </div>
    <div className="form-group row border rounded mt-3">
              <label htmlFor="feedback" className="col-sm-6 col-form-label text-center">Feedback Comments</label>
              <div className="col-sm-6 my-auto">
              <input
                type="text"
                id="feedback"
                name="feedback"
                className='form-control w-75 border-dark'
                value={feedback}
                onChange={(e) => setFeedback(e.target.value)}
               
              />
              </div>
            </div>
            <div className="form-group row border rounded mt-3">
              <label htmlFor="feedbackRating" className="col-sm-6 col-form-label text-center">Feedback Rating (Rate from 1 to 5)</label>
              <div className="col-sm-6 my-auto">
              <input
                type="text"
                id="feedbackRating"
                name="feedbackRating"
                className='form-control w-75 border-dark'
                value={feedbackRating}
                onChange={(e) => setFeedbackRating(e.target.value)}
               
              />
              </div>
            </div>
    <button type="button" id="submit" onClick={handleSubmit} className="ml-3">
     Submit
    </button>
  </form>
   <div id="message"></div>
   </div>

  );
}	 	  	   		    	   	 	   	 	
  export default AddFeedback;
  