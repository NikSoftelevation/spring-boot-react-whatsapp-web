import React from "react";

const ChatCard = (props) => {
  const containerStyle = {
    display: "flex",
  };

  const imageContainerStyle = {
    width: "20%",
  };

  const imageStyle = {
    height: "3rem",
    width: "3rem",
    borderRadius: "30%",
    cursor: "pointer",
  };

  const contentContainerStyle = {
    paddingLeft: "0.1rem",
    width: "50%",
  };

  const headerStyle = {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  };

  const usernameStyle = {
    fontSize: "1.125rem",
  };

  const timestampStyle = {
    fontSize: "1.125rem",
  };
  return (
    <div style={containerStyle}>
      <div style={imageContainerStyle}>
        <img
          style={imageStyle}
          src="https://cdn.pixabay.com/photo/2023/06/13/15/05/astronaut-8061095_640.png"
          alt=""
        />
      </div>
      <div style={contentContainerStyle}>
        <div style={headerStyle}>
          <p style={usernameStyle}>Username</p>
          <p style={timestampStyle}>Timestamp</p>
        </div>
      </div>
    </div>
  );
};

export default ChatCard;
