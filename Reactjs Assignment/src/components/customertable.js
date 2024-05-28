import React from 'react';
import "./customertable.css";
const CustomerList = ({ customers, onCustomerSelect }) => {
  return (
    <div className="card mb-4"> {/* Added bottom margin for spacing */}
      <div className="card-header bg-primary text-white">
        <h2>Customer List</h2>
      </div>
      <div className="card-body">
        <table className="table-striped">
          <thead>
            <tr>
              <th>Id</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
            </tr>
          </thead>
          <tbody>
            {customers.map((customer) => (
              <tr
                key={customer.id}
                onClick={() => onCustomerSelect(customer)}
                className="cursor-pointer"
              >
                <td>{customer.id}</td>
                <td>{customer.firstName}</td>
                <td>{customer.lastName}</td>
                <td>{customer.email}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};
 
export default CustomerList;