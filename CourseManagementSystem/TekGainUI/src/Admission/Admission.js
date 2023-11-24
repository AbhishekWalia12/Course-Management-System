import React from "react";

import { Link, Outlet } from "react-router-dom";

import {
  BrowserRouter as Router,
  Route,
  Routes,
  NavLink,
} from "react-router-dom";

import Routing from "../routing";

function Admission() {
  const userRole = localStorage.getItem("username"); // Get the user's role

  return (
    <div>
      <h2 className="text-dark text-center">ADMISSION DETAILS</h2>

      <div className="">
        <div className="d-flex justify-content-center">
          {userRole !== "admin" && (
            <Link to="/admission/registration">
              <button type="button" className="btn btn-warning m-2">
                Associate Registration
              </button>
            </Link>
          )}

          {userRole === "admin" && (
            <Link to="/admission/totalFee">
              <button type="button" className="btn btn-warning m-2">
                Total Fee
              </button>
            </Link>
          )}

          {userRole !== "admin" && (
            <Link to="/admission/addfeedback">
              <button type="button" className="btn btn-warning m-2">
                Add Feedback
              </button>
            </Link>
          )}

          {userRole === "admin" && (
            <Link to="/admission/highestFee">
              <button type="button" className="btn btn-warning m-2">
                Highest Fee Details
              </button>
            </Link>
          )}

          {userRole !== "admin" && (
            <Link to="/admission/viewfeedback">
              <button type="button" className="btn btn-warning m-2">
                View Feedback
              </button>
            </Link>
          )}

          {userRole !== "admin" && (
            <Link to="/admission/makepayment">
              <button type="button" className="btn btn-warning m-2">
                Make Payment
              </button>
            </Link>
          )}
        </div>

        <Outlet />
      </div>
    </div>
  );
}

export default Admission;
