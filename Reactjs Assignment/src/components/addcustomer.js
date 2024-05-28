import "../components/addcustomer.css";
import React, { useState } from 'react';
const AddCustomer=({ onCustomerAdd })=> {
    const [UserData, setUserData] = useState({
      firstName: "",
      lastName: "",
      email: ""
    });
  const {firstName, lastName, email} = UserData;
 
  const changeHandler = e => {
    setUserData({
      ...UserData,
      [e.target.name]: e.target.value
    });
  }
  const addedData= e=>{
    e.preventDefault();
    onCustomerAdd(UserData);
  }
  return (
    <>
    <h2>AddCustomer</h2>
    <hr/>
    <form onSubmit={addedData}>
      <label>First Name</label><br/>
      <input className='mystyle' type="text" placeholder="Enter First Name" name="firstName" value={firstName} onChange={changeHandler} required/><br/>
      <label>Last Name</label><br/>
      <input  className='mystyle' type="text" placeholder="Enter Last Name" name="lastName" value={lastName} onChange={changeHandler}required/><br/>
      <label>Email</label><br/>
      <input  className='mystyle' type="email" placeholder="Enter Email" name="email" value={email} onChange={changeHandler}required/><br/>
      <button type="submit">Submit</button>
    </form>
    </>
  );
}
 
export default AddCustomer;
