import {useEffect, useState} from "react";
import {getPictureOfTheDay} from "../service/api-service";


import {NasaPicture} from "../model/NasaPicture";


export default function useNasaPicture(){
    const [pictureOfTheDay, setPictureOfTheDay] = useState<NasaPicture>();

    useEffect( () =>{
        getPictureOfTheDay()
            .then(pictureOfTheDay => setPictureOfTheDay(pictureOfTheDay))
            .catch(()=> console.error("Can't find Picture Of The Day"))
    },[])
    return pictureOfTheDay
}