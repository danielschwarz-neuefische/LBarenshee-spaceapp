import PictureOfTheDay from "../components/PictureOfTheDay";
import React from "react";
import {useNavigate} from "react-router-dom";

function LandingPage() {
    const navigate = useNavigate()
    return (
        <div className="landingpage">
            <button onClick={() => navigate(`/picoftheday`)}>Picture Of The Day</button>
        </div>
    );
}

export default LandingPage;
