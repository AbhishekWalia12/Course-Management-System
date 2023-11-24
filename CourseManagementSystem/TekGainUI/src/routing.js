import React from "react";

import {
  BrowserRouter as Router,
  Route,
  Routes,
  NavLink,
} from "react-router-dom";

import Nav from "react-bootstrap/Nav";

import Navbar from "react-bootstrap/Navbar";

import Button from "react-bootstrap/Button";

import Course from "./Course/Course.js";

import Admission from "./Admission/Admission.js";

import Associate from "./Associate/Associate.js";

import AddCourse from "./Course/AddCourse.js";

import UpdateCourse from "./Course/UpdateCourse.js";

import ViewCourse from "./Course/ViewCourse.js";

import CourseDeactivate from "./Course/CourseDeactivate.js";

import CourseRating from "./Course/CourseRating.js";

import AssociateRegistration from "./Admission/AssociateRegistration.js";

import HighestFeeCalculation from "./Admission/HighestFeeCalculation";

import AddFeedback from "./Admission/AddFeedback";

import ViewFeedback from "./Admission/ViewFeedback";

import FeeDetails from "./Admission/FeeDetails.js";

import MakePayment from "./Admission/MakePayment";

import AddAssociate from "./Associate/AddAssociate";

import UpdateAssociate from "./Associate/UpdateAssociate";

import ViewAssociate from "./Associate/ViewAssocite";

import "bootstrap/dist/css/bootstrap.min.css";

function Routing() {
  // Get the user's role from localStorage

  const userRole = localStorage.getItem("username");

  const handleLogout = () => {
    logout();
  };

  const logout = () => {
    localStorage.clear();

    window.location.href = "/";
  };

  return (
    <Router>
      <Navbar className="form-control justify-content-between bg-dark">
        <Navbar.Brand href="/" className="navbar-brand">
          <div>
            <h1 style={{ color: "white" }}>TekGain</h1>
          </div>
        </Navbar.Brand>

        <Nav>
          <h5 className="m-auto text-white">
            Hello, {localStorage.getItem("username")}
          </h5>

          <NavLink to="/course" className="nav-link">
            <Button className="btn btn-primary">Course</Button>
          </NavLink>

          <NavLink to="/admission" className="nav-link">
            <Button className="btn btn-primary">Admission</Button>
          </NavLink>

          <NavLink to="/associate" className="nav-link">
            <Button className="btn btn-primary">Associate</Button>
          </NavLink>

          <NavLink to="/" className="nav-link">
            <Button className="btn btn-danger" onClick={handleLogout}>
              Log Out
            </Button>
          </NavLink>
        </Nav>
      </Navbar>

      <Routes>
        <Route path="/course" element={<Course />}>
          {userRole === "admin" && (
            <Route path="addcourse" element={<AddCourse />} />
          )}

          {userRole === "admin" && (
            <Route path="updatecourse" element={<UpdateCourse />} />
          )}

          {userRole !== "admin" && (
            <Route path="viewcourse" element={<ViewCourse />} />
          )}

          {userRole === "admin" && (
            <Route path="deactivate" element={<CourseDeactivate />} />
          )}

          {userRole === "admin" && (
            <Route path="viewfeedbackrating" element={<CourseRating />} />
          )}
        </Route>

        <Route path="/admission" element={<Admission />}>
          <Route path="registration" element={<AssociateRegistration />} />

          {userRole === "admin" && (
            <Route path="totalFee" element={<FeeDetails />} />
          )}

          {userRole !== "admin" && (
            <Route path="addfeedback" element={<AddFeedback />} />
          )}

          {userRole === "admin" && (
            <Route path="highestFee" element={<HighestFeeCalculation />} />
          )}

          {/* {userRole !== 'admin' && <Route path="makepayment" element={<MakePayment />} />} */}

          <Route path="makepayment" element={<MakePayment />} />

          {userRole !== "admin" && (
            <Route path="viewfeedback" element={<ViewFeedback />} />
          )}
        </Route>

        <Route path="/associate" element={<Associate />}>
          {userRole === "admin" && (
            <Route path="addassociate" element={<AddAssociate />} />
          )}

          {userRole !== "admin" && (
            <Route path="updateassociate" element={<UpdateAssociate />} />
          )}

          {userRole !== "admin" && (
            <Route path="viewassociate" element={<ViewAssociate />} />
          )}
        </Route>
      </Routes>
    </Router>
  );
}

export default Routing;
