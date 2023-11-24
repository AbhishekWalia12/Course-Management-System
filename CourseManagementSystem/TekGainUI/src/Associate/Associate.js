import React from "react";

import { Link, Outlet } from "react-router-dom";

import Routing from "../routing";

function Associate() {
  const userRole = localStorage.getItem("username"); // Get the user's role

  return (
    <div>
      <h2 className="text-dark text-center">ASSOCIATE INFO</h2>

      <div className="">
        <div className="d-flex justify-content-center">
          {userRole === "admin" && (
            <Link to="/associate/addassociate">
              <button type="button" className="btn btn-warning m-2">
                Add Associate
              </button>
            </Link>
          )}

          {userRole !== "admin" && (
            <Link to="/associate/updateassociate">
              <button type="button" className="btn btn-warning m-2">
                Update Associate
              </button>
            </Link>
          )}

         

          {userRole !== "admin" && (
            <Link to="/associate/viewassociate">
              <button type="button" className="btn btn-warning m-2">
                View Associate
              </button>
            </Link>
          )}
        </div>

        <Outlet />
      </div>
    </div>
  );
}

export default Associate;
