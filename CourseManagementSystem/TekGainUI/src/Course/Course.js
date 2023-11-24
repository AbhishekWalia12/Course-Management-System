import React from "react";

import { Link, Outlet } from "react-router-dom";

import {
  BrowserRouter as Router,
  Route,
  Routes,
  NavLink,
} from "react-router-dom";

import Routing from "../routing";

import { Button } from "react-bootstrap";

import "./AddCourse.css";

function Course() {
  const userRole = localStorage.getItem("username"); // Get the user's role

  return (
    <div>
      <h2 className="text-dark text-center">COURSE INFORMATION</h2>

      <div className="">
        <div className="d-flex justify-content-center">
          {userRole === "admin" && (
            <Link to="/course/addcourse">
              <button type="button" className="btn btn-warning m-2">
                Add Course
              </button>
            </Link>
          )}

          {userRole === "admin" && (
            <Link to="/course/updatecourse">
              <button type="button" className="btn btn-warning m-2">
                Update Course
              </button>
            </Link>
          )}

          {userRole !== "admin" && (
            <Link to="/course/viewcourse">
              <button type="button" className="btn btn-warning m-2">
                View Course
              </button>
            </Link>
          )}

          {userRole === "admin" && (
            <Link to="/course/viewfeedbackrating">
              <button type="button" className="btn btn-warning m-2">
                View Feedback Rating
              </button>
            </Link>
          )}

          {userRole === "admin" && (
            <Link to="/course/deactivate">
              <button type="button" className="btn btn-warning m-2">
                Course Deactivate
              </button>
            </Link>
          )}

        </div>

        <Outlet />
      </div>
    </div>
  );
}

export default Course;
