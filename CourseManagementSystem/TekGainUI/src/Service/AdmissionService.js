import axios from 'axios';

export  async function register(associateId,courseId) {

    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
    
     
        const response = await axios.post(
          `http://localhost:9098/api/user/admission/register/${associateId}/${courseId}`,
          null, 
          {
            headers: headers
          }
        );
        return response;
    
    
}

export async function calculateFees(associateId) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.put(`http://localhost:9098/api/admin/admission/calculateFees/${associateId}`,
        null,
        { headers }
      );
  
      return response;
   
}

export  async function addfeedback(regNo,feedback,feedbackRating) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
    
     
        const response = await axios.post(
          `http://localhost:9098/api/user/admission/feedback/${regNo}/${feedback}/${feedbackRating}`,
          null, 
          {
            headers: headers
          }
        );
        return response;
   
}	 	  	  		    	   	 	   	 	


  
export async function highestFee(associateId) {	 	
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.get(
        `http://localhost:9098/api/admin/admission/highestFee/${associateId}`,
        { headers }
      );
  
      return response;  	  		    	   	 	   	 	

}

export async function viewFeedbackByCourseId(courseId) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.get(
        `http://localhost:9098/api/user/admission/viewFeedbackByCourseId/${courseId}`,
        { headers }
      );
  
      return response; 
 
}
  
export async function makePayment(registartionId) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
       
      };
    
     
        const response = await axios.post(
          `http://localhost:9093/admission/makePayment/${registartionId}`,
          null, 
          {
            headers: headers
          }
        );
        return response;

   
  
}

	 	  	  		    	   	 	   	 	
