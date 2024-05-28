import React, { useState } from 'react';
import CustomerList from './components/customertable';
import CustomerDetails from './components/customerdetails';
import customers from './components/customer.json'; // Importing the initial customer data
import AddCustomer from './components/addcustomer';
import "./components/app.css";

 
const App = () => {
  const [customerList, setCustomerList] = useState(customers); // Using the imported customer data as initial state
  const [selectedCustomer, setSelectedCustomer] = useState(null);
 
  const handleCustomerSelect = (customer) => {
    setSelectedCustomer(customer);
  };
 
  const handleCustomerAdd = (newCustomer) => {
    const maxId = customerList.length > 0 ? Math.max(...customerList.map((c) => c.id)) : 0;
    const updatedCustomer = { ...newCustomer, id: maxId + 1 };
    setCustomerList([...customerList, updatedCustomer]); // Adding the new customer to the list
    console.log('Updated Customer List:', customerList);
  };
 
  return (
    <div className="containermy-5">
       <CustomerList customers={customerList} onCustomerSelect={handleCustomerSelect} /> {/* Passing the updated customer list */}
          <br/><br/><br/>
      <div className="row">
        <div className="col-md-6">
          <AddCustomer onCustomerAdd={handleCustomerAdd} />
        </div>
        <div className="col-md-6">
          <div className="details">
          <CustomerDetails customer={selectedCustomer} />
          </div>
        </div>
      </div>
    </div>
  );
};
 
export default App;