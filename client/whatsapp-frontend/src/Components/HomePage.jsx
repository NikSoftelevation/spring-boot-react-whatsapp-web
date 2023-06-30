import React from "react";
import { TbCircleDashed } from "react-icons/tb";
import { BiCommentDetail } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import { BsFilter } from "react-icons/bs";
import ChatCard from "./ChatCard";

const HomePage = () => {
  const containerStyle = {
    position: "relative",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
    padding: "0.75rem",
  };

  const headerStyle = {
    width: "100%",
    paddingTop: "3.5rem",
    backgroundColor: "#00a884",
  };

  const contentWrapperStyle = {
    display: "flex",
    backgroundColor: "#f0f2f5",
    height: "90vh",
    position: "absolute",
    top: "6rem",
    left: "6rem",
    width: "calc(100% - 12rem)",
  };

  const leftPanelStyle = {
    width: "30%",
    backgroundColor: "#e8e9ec",
    height: "100%",
  };

  const userProfileStyle = {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "0.75rem",
  };

  const imageStyle = {
    borderRadius: "50%",
    width: "5rem",
    height: "5rem",
    cursor: "pointer",
  };
  const userStyle = {
    marginLeft: "0.75rem",
  };

  const iconWrapperStyle = {
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
    gap: "0.9rem",
    fontSize: "2rem",
  };
  const inputStyle = {
    border: "none",
    outline: "none",
    backgroundColor: "slategray",
    borderRadius: "0.375rem",
    width: "93%",
    paddingLeft: "2.25rem",
    paddingTop: "0.5rem",
    paddingBottom: "0.5rem",
    color: "white",
  };

  const searchIconStyle = {
    position: "absolute",
    left: "0.75rem",
    top: "50%",
    transform: "translateY(-50%)",
  };

  const filterIconStyle = {
    marginLeft: "0.75rem",
    fontSize: "1.875rem",
  };

  const chatCardContainerStyle = {
    display: "flex",
    flexDirection: "column",
    gap: "0.1rem",
    marginTop: "1.5rem",
  };

  return (
    <div style={containerStyle}>
      <div style={headerStyle}></div>
      <div style={contentWrapperStyle}>
        <div style={leftPanelStyle}>
          <div style={userProfileStyle}>
            <div style={{ display: "flex", alignItems: "center" }}>
              <img
                src="https://cdn.pixabay.com/photo/2023/06/12/00/11/smartphone-8057248_640.jpg"
                alt=""
                style={imageStyle}
              />

              <p style={userStyle}>username</p>
            </div>

            <div style={iconWrapperStyle}>
              <TbCircleDashed />
              <BiCommentDetail />
            </div>
          </div>
          <div style={containerStyle}>
            <input
              style={inputStyle}
              type="text"
              placeholder="Search or start a new chat"
            />
            <AiOutlineSearch style={searchIconStyle} />
            <BsFilter style={filterIconStyle} />
          </div>

          <div style={chatCardContainerStyle}>
            <ChatCard />
          </div>
        </div>
      </div>
      <div className="right"></div>
    </div>
  );
};

export default HomePage;
