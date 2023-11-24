import React,{useState,useEffect} from 'react';
import { highestFee } from '../Service/AdmissionService';

function HighestFeeCalculation(){
  const [associateId, setAssociateId] = useState("");
  const [message, setMessage] = useState(null);
  const [data, setData] = useState(null);
   
  const fetchData = async () => {
    try {
      const response = await highestFee(associateId);
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
    if (associateId) {
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
<div className="text-center">
   <form className="my-auto text-center" >
   <h3>Associate Highest Fee</h3>
   <div className="form-group row border rounded mt-3">
             <label htmlFor="associateId" className="col-sm-6 col-form-label text-center">Associate Id</label>
             <div className="col-sm-6 my-auto">
             <input
               type="text"
               id="associateId"
               name="associateId"
               className='form-control w-75 border-dark'
               value={associateId}
               onChange={(e) => setAssociateId(e.target.value)}
              
             />
             </div>
           </div>
       <button type="button" id='submit' onClick={handleSubmit} className="ml-3">Submit</button>
      
   </form>
   {message && <div id="message">{message}</div>}
        {data && (
        <table className="table table-border w-50 m-auto">
          <tbody>
            <tr>
              <th>Course Id</th>
            </tr>
            {data.map((item, index) => (
          <tr key={index}>
            <td>{item}</td>
          </tr>
        ))}
          </tbody>
        </table>
      )}  
   </div>
 );
}	 	  	  
  export default HighestFeeCalculation;
  