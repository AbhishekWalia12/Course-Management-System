import axios from 'axios';

export  async function addCourseDetails(course) {

   
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };

   
    const response = await axios.post('http://localhost:9098/api/admin/course/addCourse', course, {
      headers: headers
    });
    return response;
    
}

export async function updateCourse(courseId, fees) {
 
    const headers = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      'Authorization': localStorage.getItem('token')
    };

    const response = await axios.put(
      `http://localhost:9098/api/admin/course/update/${courseId}/${fees}`,
      null,
      { headers }
    );

    return response;
  
    
  }
  
  export async function viewCourseById(courseId) {
   
      const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.get(
        `http://localhost:9098/api/user/course/viewByCourseId/${courseId}`,
        { headers }
      );
  
      return response; // Return the response data instead of the entire response object
    
    
  }
  
  export async function viewFeedback(courseId) {
  
      const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.get(
        `http://localhost:9098/api/admin/course/viewFeedbackRating/${courseId}`,
        { headers }
      );
  
      return response; // Return the response data instead of the entire response object
    
    
  }
  
  export async function deactivate(courseId, fees) {
 
      const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };
  
      const response = await axios.delete(
        `http://localhost:9098/api/admin/course/deactivate/${courseId}`,
        { headers }
      );
  
      return response.data; // Return the response data instead of the entire response object
    
    
    
  }