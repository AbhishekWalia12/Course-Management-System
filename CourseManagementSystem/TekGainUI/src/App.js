import React, { useEffect } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  NavLink,
} from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Button from "react-bootstrap/Button";
import "./App.css";
import { useState } from "react";
import validateUser from "./Service/LoginService";
import "bootstrap/dist/css/bootstrap.min.css";
import { logStatus } from "./Service/LoginService";
import "./App.css";
import Routing from "./routing";

function App() {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [isLoggedIn, setIsLoggedIn] = useState(logStatus);
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    let user = await validateUser(userName, password);
    if (user) {
      setIsLoggedIn(true);
      setErrorMessage("");
    } else {
      setIsLoggedIn(false);
      setUserName("");
      setPassword("");
      setErrorMessage("Invalid username/Password");
    }
  };
  useEffect(() => {
    setIsLoggedIn(logStatus);
  }, []);

  return (
    <div className="app-container">
      {!isLoggedIn ? (
        <div
          className="login-container"
        >
          <h1 className="text-center mb-4">TechGain</h1>

          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="userName">Username</label>

              <input
                type="text"
                className="form-control"
                id="userName"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Password</label>

              <input
                type="password"
                className="form-control"
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <div className="text-center">
              <button type="submit" className="btn btn-primary mt-3">
                Log In
              </button>
            </div>

            {errorMessage && <p className="error-msg">{errorMessage}</p>}
          </form>
        </div>
      ) : (
        <Routing />
      )}
    </div>
  );
}

export default App;