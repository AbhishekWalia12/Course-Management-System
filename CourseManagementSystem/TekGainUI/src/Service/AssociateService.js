import axios from 'axios';

export  async function addAssociate(associate) {
    const headers = {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': localStorage.getItem('token')
      };

   
    const response = await axios.post('http://localhost:9098/api/admin/associate/addAssociate', associate, {
      headers: headers
    });
    return response;
   
}

export async function updateAssociate(associateId,address) {
  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': localStorage.getItem('token')
  };

 
    const response = await axios.put(
      `http://localhost:9098/api/user/associate/updateAssociate/${associateId}/${address}`,
      null,
      { headers }
    );

    return response;
  
 
}
 
export async function viewAssociateById(associateId) {
  const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'Authorization': localStorage.getItem('token')
  };

  const response = await axios.get(
    `http://localhost:9098/api/user/associate/viewByAssociateId/${associateId}`,
    { headers }
  );

  return response;

  
}	 	  	  		    	   	 	   	 	
  