import React from 'react';
 
const CustomerDetails = ({ customer }) => {
  if (!customer) {
    return <div className="card">
      <div className="card-body">
        <p>Select a customer to view details</p>
      </div>
    </div>;
  }
 
  return (
    <div className="card">
      <div className="card-header bg-primary text-white">
        <h2>Customer Details</h2>
      </div>
      <div className="card-body">
        <p>ID: {customer.id}</p>
        <p>First Name: {customer.firstName}</p>
        <p>Last Name: {customer.lastName}</p>
        <p>Email: {customer.email}</p>
      </div>
    </div>
  );
};
 
export default CustomerDetails;